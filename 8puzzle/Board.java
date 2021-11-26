/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class Board {

    private final int[][] tiles;
    private final int[][] goal;
    private final int length;
    private int x;
    private int y;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.length = tiles.length;
        this.tiles = new int[length][length];
        this.goal = new int[length][length];
        int k = 1;

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                this.tiles[i][j] = tiles[i][j];
                this.goal[i][j] = k++;
                if (tiles[i][j] == 0) {
                    this.x = i;
                    this.y = j;
                }
            }
        }
        goal[length - 1][length - 1] = 0;
    }

    // string representation of this board
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(length).append("\n");
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                builder.append(tiles[i][j]).append(" ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    // board dimension n
    public int dimension() {
        return length;
    }

    // number of tiles out of place
    public int hamming() {
        int sum = 0;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (tiles[i][j] != goal[i][j] && tiles[i][j] != 0) {
                    sum++;
                }
            }
        }
        return sum;
    }

    public int manhattan() {
        int sum = 0;
        int iDestination;
        int jDestination;
        for (int i = 0; i < length; i++)
            for (int j = 0; j < length; j++) {
                if (tiles[i][j] != 0 && tiles[i][j] != i * length + (j + 1)) {
                    iDestination = (tiles[i][j] - 1) / length;
                    if (tiles[i][j] % length == 0) {
                        jDestination = length - 1;
                    }
                    else {
                        jDestination = tiles[i][j] % length - 1;
                    }
                    sum += getManhattanDistance(iDestination, i, jDestination, j);
                }
            }
        return sum;
    }

    private int getManhattanDistance(int x0, int x1, int y0, int y1) {
        return Math.abs(x1 - x0) + Math.abs(y1 - y0);
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object obj) {
        if (obj == null || (this.getClass() != obj.getClass())) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Board board = (Board) obj;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (this.tiles[i][j] != board.tiles[i][j])
                    return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> queue = new Queue<>();
        if (x - 1 >= 0) {
            queue.enqueue(getNeighbourBoard(x, y, x - 1, y));
        }
        if (x + 1 < length) {
            queue.enqueue(getNeighbourBoard(x, y, x + 1, y));
        }
        if (y - 1 >= 0) {
            queue.enqueue(getNeighbourBoard(x, y, x, y - 1));
        }
        if (y + 1 < length) {
            queue.enqueue(getNeighbourBoard(x, y, x, y + 1));
        }
        return queue;
    }

    private Board getNeighbourBoard(int x0, int y0, int i, int j) {
        swapTiles(x0, y0, i, j);
        Board board = new Board(tiles);
        swapTiles(x0, y0, i, j);
        return board;
    }

    private void swapTiles(int x0, int y0, int i, int j) {
        int temp = tiles[x0][y0];
        tiles[x0][y0] = tiles[i][j];
        tiles[i][j] = temp;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length - 1; j++) {
                if (tiles[i][j] != 0 && tiles[i][j + 1] != 0) {
                    return getNeighbourBoard(i, j, i, j + 1);
                }
            }
        }
        return this;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // read in the board specified in the filename
        In in = new In("puzzle3x3-12.txt");
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }

        // solve the slider puzzle
        Board initial = new Board(tiles);

        StdOut.println(initial.hamming());
        StdOut.println(initial.manhattan());
        StdOut.println(initial.neighbors());
    }
}
