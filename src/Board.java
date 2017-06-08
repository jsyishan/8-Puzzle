import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

/**
 * Created by eugen on 6/6/17.
 */


public class Board {

    private int[][] tiles;
    private int n;
    private int hamming;
    private int manhattan;

    public Board (int[][] blocks) {

        n = blocks.length;
        tiles = new int[n][n];
        hamming = 0;
        manhattan = 0;

        int value = 1;  //  correct value on each tiles
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = blocks[i][j];

                if (tiles[i][j] != 0 && tiles[i][j] != value) {
                    hamming++;

                    int row = (tiles[i][j] - 1) / n;
                    int column = (tiles[i][j] - 1) % n;

                    manhattan += (Math.abs (i - row) + Math.abs (j - column));
                }

                value++;
            }
        }

    }

    private Board (int[][] blocks, int x1, int y1, int x2, int y2) {

        n = blocks.length;
        tiles = new int[n][n];
        hamming = 0;
        manhattan = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = blocks[i][j];
            }
        }

        //exchange the value of tiles[x1][y1] and tiles[x2][y2]
        int tmp = tiles[x1][y1];
        tiles[x1][y1] = tiles[x2][y2];
        tiles[x2][y2] = tmp;

        int value = 1;  //  correct value on each tiles
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                if (tiles[i][j] != 0 && tiles[i][j] != value) {
                    hamming++;

                    int row = (tiles[i][j] - 1) / n;
                    int column = (tiles[i][j] - 1) % n;

                    manhattan += (Math.abs (i - row) + Math.abs (j - column));
                }

                value++;
            }
        }

    }

    public int dimension () { return n; }

    public int hamming () { return hamming; }

    public int manhattan () { return manhattan; }

    public boolean isGoal () {

        int value = 1;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                //if (tiles[i][j] == 0 && i < n) { return false; }
                if (tiles[i][j] != value && tiles[i][j] != 0) { return false; }
                value++;
            }
        }

        return true;
    }

    public Board twin () {

        if (tiles[0][0] != 0 && tiles[0][1] != 0) { return new Board (tiles, 0, 0, 0, 1); }

        else if (tiles[0][0] != 0 && tiles[1][0] != 0) { return new Board (tiles, 0, 0, 1, 0); }

        else { return new Board (tiles, 0, n - 1, n - 1, 0); }

    }


    public boolean equals (Object y) {

        if (y == this) { return true; }
        if (y == null) { return false; }
        if (y.getClass() != this.getClass()) { return false; }

        Board that = (Board) y;
        if (that.n != this.n) { return false; }
        for (int i = 0; i < n; i++) {
            for (int j = 0;j < n; j++) {
                if (that.tiles[i][j] != this.tiles[i][j]) { return false; }
            }
        }

        return true;
    }

    public Iterable<Board> neighbors () {

        Stack<Board> iterNeighbors = new Stack<>();

        int row = 0, col = 0;   //  location(row, col) of 0 in tiles

        outerloop:
        for (row = 0; row < n; row++) {
            for (col = 0; col < n; col++) {
                if (tiles[row][col] == 0) {
                    break outerloop;
                }
            }
        }

        if (row > 0) { iterNeighbors.push (new Board(tiles, row, col, row - 1, col)); }

        if (row < n - 1) { iterNeighbors.push (new Board(tiles, row, col, row + 1, col)); }

        if (col > 0) { iterNeighbors.push (new Board(tiles, row, col, row, col - 1)); }

        if (col < n - 1) { iterNeighbors.push (new Board(tiles, row, col, row ,col + 1)); }

        return iterNeighbors;
    }

    public String toString() {

        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }


    public static void main (String[] args) {

        In in = new In (args[0]);
        int n = in.readInt ();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocks[i][j] = in.readInt ();
            }
        }
        Board initial = new Board(blocks);
        StdOut.println (initial);

//        StdOut.println(initial.manhattan());
        StdOut.println(initial.hamming());

//        StdOut.println("neighbors:");
//        for (Board neighbor : initial.neighbors()) {
//            StdOut.println(neighbor);
//            StdOut.println(neighbor.manhattan() + "\n");
//        }


//        Board b1 = new Board(blocks);
//        Board b2 = new Board(blocks, 0, 0, 1, 1);
//        Board b3 = initial;
//
//        StdOut.println (b1.equals (b2));
//        StdOut.println (initial.equals (b1));
//        StdOut.println (initial.equals (b2));
//        StdOut.println (initial.equals (b3));
//        StdOut.println (b1.equals (b3));
//        StdOut.println (b2.equals (b3));

//        StdOut.println (initial.twin());
    }
}
