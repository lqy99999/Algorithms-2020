/* *****************************************************************************
 *  Name:    LEE CHINGYEN
 *  NetID:   B06602035
 *  Precept: P00
 *
 *  Description:  We model a percolation system using an n-by-n grid of sites.
 *                Each site is either open or blocked. A full site is an open
 *                site that can be connected to an open site in the top row via
 *                a chain of neighboring (left, right, up, down) open sites.
 *                We say the system percolates if there is a full site in the
 *                bottom row. In other words, a system percolates if we fill all
 *                open sites connected to the top row and that process fills
 *                some open site on the bottom row.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF cute; // record the index of open sites

    /**
     * Extra bonus: deal with backwash
     */
    private WeightedQuickUnionUF back;

    private boolean[] grid; // build a percolation system using an n-by-n grid
    private int num; // record n
    private int end; // record the index of bottom
    private int opensites; // the number of open sites

    /**
     * creates n-by-n grid, with all sites initially blocked(false)
     * 0 (top)
     * ------------
     * x x x ... x
     * x x x ... x
     * .         .    (n*n)
     * .         .
     * x x x ... x
     * ------------
     * n*n+1 (bottom)
     * <p>
     * grid: boolean array. It's easy to deal with by tranfering
     * 2-dimention arrary to 1-dimention array.
     */
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("Illegal Argument!!");

        cute = new WeightedQuickUnionUF(n * n + 2);
        back = new WeightedQuickUnionUF(n * n + 1); // without bottom

        grid = new boolean[n * n + 2];

        // record n & bottom
        num = n;
        end = n * n + 1;

        // top & bottom : open(true)
        grid[0] = true;
        grid[end] = true;
    }

    /**
     * To open the site (row, col) if it is not open already.
     * Meanwhile, check if it can be connected to another open site via a chain
     * of neighboring (up, down, left, right) open sites.
     */
    public void open(int row, int col) {
        if (!checkIndex(row, col)) return;

        int idx = calIndex(row, col);
        if (isOpen(row, col)) return;

        grid[idx] = true;
        ++opensites;

        // up
        if (row == 0) {
            cute.union(0, idx);
            back.union(0, idx);
        }
        if (row - 1 >= 0) {
            int up = calIndex(row - 1, col);
            if (grid[up]) {
                cute.union(up, idx);
                back.union(up, idx);
            }
        }

        // down
        if (row == num - 1) cute.union(end, idx);
        if (row + 1 <= num - 1) {
            int down = calIndex(row + 1, col);
            if (grid[down]) {
                cute.union(down, idx);
                back.union(down, idx);
            }
        }

        // left
        if (col - 1 >= 0) {
            int left = calIndex(row, col - 1);
            if (grid[left]) {
                cute.union(left, idx);
                back.union(left, idx);
            }
        }
        // right
        if (col + 1 <= num - 1) {
            int right = calIndex(row, col + 1);
            if (grid[right]) {
                cute.union(right, idx);
                back.union(right, idx);
            }
        }
    }

    /**
     * Check if the site (row, col) is open.
     */
    public boolean isOpen(int row, int col) {
        if (checkIndex(row, col))
            return grid[calIndex(row, col)];
        else
            return false;
    }

    /**
     * Check if the site (row, col) is full by checking if the site and
     * top are in the same set.
     */
    public boolean isFull(int row, int col) {
        if (checkIndex(row, col))
            return (back.find(calIndex(row, col)) == cute.find(0));
        else
            return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return (opensites);
    }

    /**
     * Check if the system percolates or not by comparing
     * the set of top and bottom.
     */
    public boolean percolates() {
        return (cute.find(0) == cute.find(end));
    }

    // unit testing (required)
    public static void main(String[] args) {

    }


    /**
     * Check if (row, col) is in grid.
     */
    private boolean checkIndex(int row, int col) {
        if (row < 0 || row > num - 1 || col < 0 || col > num - 1)
            throw new IllegalArgumentException("Illegal (row,col)!!");
        else return true;
    }

    /**
     * Calculate index from (row, col).
     */
    private int calIndex(int row, int col) {
        return row * num + (col + 1);
    }
}
