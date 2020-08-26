/* *****************************************************************************
 *  Name:    LEE CHINGYEN
 *  NetID:   B06602035
 *  Precept: P00
 *
 *
 *  Description:  Write a program to solve the 8-puzzle problem
 *                (and its natural generalizations) using
 *                the A* search algorithm.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.Queue;

import java.util.Arrays;

/*
 * default construtor
 * When it constructs input board, recording the number of tiles out of place,
 * sum of Manhattan distances between tiles and goal and the index of 0 in board.
 */
public class Board {
    private int n; // n-by-n board
    private int[] board; // n-by-n board
    private int ham; // number of tiles out of place
    private int man; // sum of Manhattan distances between tiles and goal
    private int zero; // the index of 0 in board

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles.length;
        board = new int[n * n];

        /*
         * idx : transfer from 2-dimension index to 1-dimension index
         * row : the row distance of board[idx] and idx
         * col : the column distance of board[idx] and idx
         */
        int idx, row, col;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                idx = i * n + j;
                board[idx] = tiles[i][j];

                if (board[idx] == 0) zero = idx;

                if (board[idx] != idx + 1 && board[idx] != 0) {
                    ham++;
                    row = Math.abs((board[idx] - (idx + 1)) / n);
                    col = Math.abs((board[idx] - (idx + 1)) % n);
                    man = man + row + col;
                }
            }
        }
    }

    // create a [n*n] board from an n-by-n array of tiles,
    public Board(int[] tiles) {
        n = (int) Math.sqrt(tiles.length);
        board = new int[tiles.length];

        /**
         * row and col are the position at 2-dimension board.
         * Calculate the hamming and manhattan meanwhile.
         */
        int row, col;

        for (int i = 0; i < tiles.length; i++) {
            board[i] = tiles[i];
            if (board[i] == 0) zero = i;
            if (board[i] != i + 1 && board[i] != 0) {
                ham++;
                row = Math.abs((board[i] - (i + 1)) / n);
                col = Math.abs((board[i] - (i + 1)) % n);
                man = man + row + col;
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(n);
        for (int i = 0; i < n * n; i++) {
            if (i % n == 0) str.append("\n\t" + board[i] + " ");
            else str.append(board[i] + " ");
        }
        return str.toString();
    }

    // tile at (row, col) or 0 if blank
    public int tileAt(int row, int col) {
        return board[row * n + col];
    }

    // board size n
    public int size() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        return ham;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return man;
    }

    // is this board the goal board?
    public boolean isGoal() {
        /**
         * check n != 0 and there is no tile out of place first.
         */
        if (n != 0 && hamming() == 0) return true;
        else return false;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        Board that = (Board) y;
        return Arrays.equals(this.board, that.board);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Board neighbor = new Board(board);
        /**
         * put all the neighbors beside 0 into the queue
         */
        Queue<Board> queue = new Queue<Board>();
        if (zero < 0) return null;

        /**
         * check if the zero is in the boundary of board.
         */
        if (zero / n > 0) { // up
            queue.enqueue(exchange(neighbor, zero, zero - n));
        }
        if (zero / n < (n - 1)) { // down
            queue.enqueue(exchange(neighbor, zero, zero + n));
        }
        if (zero % n > 0) { // left
            queue.enqueue(exchange(neighbor, zero, zero - 1));
        }
        if (zero % n < (n - 1)) { // right
            queue.enqueue(exchange(neighbor, zero, zero + 1));
        }

        return queue;
    }

    /**
     * Exchange the value of index i and j in board a.
     *
     * @param a: the board needed to exchange the value of index i and j
     * @param i: index
     * @param j: index
     * @return board that has been exchanged the value
     */
    private Board exchange(Board a, int i, int j) {
        int[] newb = Arrays.copyOf(a.board, a.board.length);
        int tmp = newb[i];
        newb[i] = newb[j];
        newb[j] = tmp;
        return new Board(newb);
    }

    /**
     * Is this board solvable?
     * when n is odd, an n-by-n board is solvable if and only if its number of inversions is even.
     * when n is even, an n-by-n board is solvable if and only if the number of inversions
     * plus the row of the blank square is odd.
     */
    public boolean isSolvable() {
        if (n == 0) return false;
        /**
         * an inversion is any pair of tiles i and j where i < j but i
         * appears after j when considering the board in row-major order
         */
        int inver = 0;
        for (int i = 0; i < n * n; i++) {
            for (int j = i + 1; j < n * n; j++) {
                if (board[i] == 0 || board[j] == 0) continue;
                if (board[i] > board[j]) {
                    // System.out.println("i:" + board[i] + " \tj:" + board[j]);
                    inver++;
                }
            }
        }
        // System.out.print("TEST:" + inver);
        if (n % 2 == 0) {
            if ((inver + zero / n) % 2 == 1) return true;
            else return false;
        }
        else {
            if (inver % 2 == 0) return true;
            else return false;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        // Board board = new Board(new int[][] { { 0, 1, 3, }, { 4, 2, 5 }, { 7, 8, 6 } });
        // System.out.println(board.toString());
        // for (Board board1 :
        //         board.neighbors()) {
        //     System.out.println(board1);
        // }
        // System.out.println(board.hamming());
        // System.out.println(board.manhattan());
        // System.out.println(board.isGoal());
        // System.out.println(board.isSolvable());
    }
}
