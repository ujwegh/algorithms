/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    // find a solution to the initial board (using the A* algorithm)

    private Node winner;
    private boolean isUnsolvable = true;

    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("Board must not be null");
        }

        MinPQ<Node> mainMinPQ = new MinPQ<>();
        MinPQ<Node> twinMinPQ = new MinPQ<>();

        Node mainNode = new Node(initial, 0, null);
        Node twinNode = new Node(initial.twin(), 0, null);
        mainMinPQ.insert(mainNode);
        twinMinPQ.insert(twinNode);

        while (true) {
            mainNode = extracted(mainMinPQ);
            if (mainNode.board.isGoal()) {
                winner = mainNode;
                isUnsolvable = false;
                break;
            }
            twinNode = extracted(twinMinPQ);
            if (twinNode.board.isGoal()) {
                winner = twinNode;
                break;
            }
        }
    }

    private Node extracted(MinPQ<Node> minPQ) {
        Node searchNode = minPQ.delMin();
        Iterable<Board> neighbors = searchNode.board.neighbors();
        for (Board neighbor : neighbors) {
            Node prevNode = searchNode.prev;
            if (prevNode == null || !neighbor.equals(prevNode.board)) {
                minPQ.insert(new Node(neighbor, searchNode.moves + 1, searchNode));
            }
        }
        return searchNode;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return !isUnsolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (isUnsolvable) {
            return -1;
        }
        return winner.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (isUnsolvable) {
            return null;
        }
        Stack<Board> result = new Stack<>();
        Node currentNode = winner;
        while (currentNode != null) {
            result.push(currentNode.board);
            currentNode = currentNode.prev;
        }
        return result;
    }

    private class Node implements Comparable<Node> {
        private Board board;
        private int moves;
        private Node prev;
        private int priority;

        public Node(Board board, int moves, Node prev) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
            this.priority = this.moves + this.board.manhattan();
        }

        public int compareTo(Node node) {
            return Integer.compare(this.priority, node.priority);
        }
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In("puzzle04.txt");
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

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