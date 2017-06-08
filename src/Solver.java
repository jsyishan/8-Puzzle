/**
 * Created by eugen on 6/6/17.
 */

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;

public class Solver {

    private boolean solvable;
    private int moves;
    private Stack<Board> solution;


    public Solver (Board initial) {

        if (initial == null) { throw new NullPointerException(); }

        moves = 0;
        solvable = false;
        MinPQ<SearchNode> minPQ = new MinPQ<>();

//        SearchNode initNode = new SearchNode(initial, false);
//        SearchNode initTwinNode = new SearchNode(initial.twin(), true);
        minPQ.insert (new SearchNode(initial, false));
        minPQ.insert (new SearchNode(initial.twin(), true));

        SearchNode min;

        do {
            min = minPQ.delMin();
            for (Board nextBoard : min.board.neighbors()) {
                if (min.prewNode == null || !nextBoard.equals (min.prewNode.board)) {
                    SearchNode nextNode = new SearchNode(nextBoard, min.twin);
                    nextNode.moves = min.moves + 1;
                    nextNode.prewNode = min;
                    minPQ.insert (nextNode);
                }
//                if (!nextBoard.equals(min.board)) { minPQ.insert (nextNode); }
            }
//            moves++;
        } while (!min.board.isGoal());
//        moves--;

        if (min.twin) {
            min.moves = -1;
            moves = min.moves;
            solvable = false;
        } else {
            solvable = true;
            moves = min.moves;

            solution = new Stack<>();
            solution.push (min.board);
            while (min.prewNode != null) {
                solution.push (min.prewNode.board);
                min = min.prewNode;
            }
        }

    }

    private static class SearchNode implements Comparable <SearchNode>{

        private int moves;
        private int priority;
        private boolean twin;
        private SearchNode prewNode;
        private Board board;

        public SearchNode (Board board, boolean twin) {

            this.board = board;
            this.twin = twin;
            moves = 0;
            priority();
            prewNode = null;

//            priority = moves + board.manhattan();
        }

        private int priority () { return priority = moves + board.manhattan(); }

        @Override
        public int compareTo (SearchNode that) { return priority() - that.priority(); }

    }

    public boolean isSolvable () { return solvable; }

    public int moves () { return moves; }

    public Iterable<Board> solution () { return solution; }


    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
