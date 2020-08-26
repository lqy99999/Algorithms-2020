/* *****************************************************************************
 *  Name:    LEE CHINGYEN
 *  NetID:   B06602035
 *  Precept: P00
 *
 *
 *  Description:  In this part, you will implement A* search
 *                to solve n-by-n slider puzzles. Create an immutable
 *                data type Solver with the following API
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    /**
     * sols: the queue that contains the solution board of input board
     * isSol: check if input is solvable
     */
    private Queue<Board> sols = new Queue<Board>();
    private boolean isSol = false;

    /**
     * Node that contains previous Node, board and the number of moving
     */
    private class SearchNode implements Comparable<SearchNode> {
        private SearchNode pre;
        private Board board;
        private int moves;

        public SearchNode(Board initial, SearchNode previous) {
            board = initial;
            pre = previous;
            if (pre == null) moves = 0;
            else moves = pre.moves + 1;
        }

        // override function to compare 2 SearchNode by the sum
        // of manhattan and moves
        @Override
        public int compareTo(SearchNode a) {
            int first = this.board.manhattan() + moves;
            int second = a.board.manhattan() + a.moves;

            if (first > second) return 1;
            else if (first < second) return -1;
            else return 0;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new NullPointerException();
        // if (!initial.isSolvable()) throw new IllegalArgumentException();

        MinPQ<SearchNode> pq = new MinPQ<>(); // queue containing all neightbors
        pq.insert(new SearchNode(initial, null));

        SearchNode min = pq.delMin();
        sols.enqueue(min.board);
        // System.out.println("TEST: " + initial);

        if (initial.isSolvable()) { // if initial board isn't sovlable, just skip calculating
            while (!min.board.isGoal()) {
                for (Board i : min.board.neighbors()) {
                    if (min.pre == null || !(min.pre.board.equals(i)))
                        pq.insert(new SearchNode(i, min));
                }
                min = pq.delMin();
                sols.enqueue(min.board); // put the min in pq into solutions queue
                // System.out.println("TEST: " + min.board);
            }
        }

        if (min.board.isGoal()) isSol = true;
    }

    // min number of moves to solve initial board
    public int moves() {
        if (isSol) return sols.size() - 1;
        else return -1;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        return sols;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        String filename = args[0];
        In in = new In(filename);
        // In in = new In("./puzzle2x2-00.txt");

        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // float time1, time2, num1;
        // System.out.print("Hamming: ");
        // time1 = System.currentTimeMillis();
        // num1 = initial.hamming();
        // time2 = System.currentTimeMillis();
        // System.out.printf("%f time: %f\n", num1, (time2 - time1));
        //
        // System.out.print("Manhattan: ");
        // time1 = System.currentTimeMillis();
        // num1 = initial.manhattan();
        // time2 = System.currentTimeMillis();
        // System.out.printf("%f time: %f\n", num1, (time2 - time1));

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSol)
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);

        }
    }
}
