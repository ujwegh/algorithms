import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {

    private Node root = null;
    private int count;


    public boolean isEmpty() { // is the set empty?
        return count == 0;
    }

    public int size() { // number of points in the set
        return count;
    }


    public void insert(Point2D p) {
        chackNotNull(p);
        put(root, p);
    }

    private void put(Node node, Point2D p) {
        if (node == null && root == null) {
            count++;
            root = new Node(p, true);
            root.rectHV = new RectHV(0, 0, 1, 1);
        }
        if (node == null) {
            return;
        }
        RectHV rectHV = node.rectHV;


        if (node.isSplitVertical) {
            Node newNode = new Node(p, false);
            if (p.x() < node.point2D.x()) {
                if (node.left != null) {
                    put(node.left, p);
                }
                else {
                    count++;
                    newNode.rectHV = new RectHV(rectHV.xmin(), rectHV.ymin(), node.point2D.x(),
                                                rectHV.ymax());
                    node.left = newNode;
                }
            }
            else {
                if (node.right != null) {
                    put(node.right, p);
                }
                else {
                    count++;
                    newNode.rectHV = new RectHV(node.point2D.x(), rectHV.ymin(), rectHV.xmax(),
                                                rectHV.ymax());
                    node.right = newNode;
                }
            }
        }
        else {
            Node newNode = new Node(p, true);
            if (p.y() < node.point2D.y()) {
                if (node.left != null) {
                    put(node.left, p);
                }
                else {
                    count++;
                    newNode.rectHV = new RectHV(rectHV.xmin(), rectHV.ymin(), rectHV.xmax(),
                                                node.point2D.y());
                    node.left = newNode;
                }
            }
            else {
                if (node.right != null) {
                    put(node.right, p);
                }
                else {
                    count++;
                    newNode.rectHV = new RectHV(rectHV.xmin(), node.point2D.y(), rectHV.xmax(),
                                                rectHV.ymax());
                    node.right = newNode;
                }
            }
        }
    }

    public boolean contains(Point2D point2D) {
        chackNotNull(point2D);
        return contains(root, point2D);
    }

    private boolean contains(Node node, Point2D point2D) {
        if (node == null) {
            return false;
        }
        if (point2D.equals(node.point2D)) {
            return true;
        }
        if (node.isSplitVertical) {
            if (point2D.x() < node.point2D.x()) {
                return contains(node.left, point2D);
            }
            else {
                return contains(node.right, point2D);
            }
        }
        else {
            if (point2D.y() < node.point2D.y()) {
                return contains(node.left, point2D);
            }
            else {
                return contains(node.right, point2D);
            }
        }
    }

    public void draw() {
        if (root == null) {
            return;
        }
        drawPoint(root);
    }

    private void drawPoint(Node node) {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.005);

        node.point2D.draw();
        RectHV rectHV = node.rectHV;

        if (node.isSplitVertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.point2D.x(), rectHV.ymin(), node.point2D.x(), rectHV.ymax());
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(rectHV.xmin(), node.point2D.y(), rectHV.xmax(), node.point2D.y());
        }
        if (node.left != null) {
            drawPoint(node.left);
        }
        if (node.right != null) {
            drawPoint(node.right);
        }
    }

    public Point2D nearest(Point2D query) {
        chackNotNull(query);
        Point2D result = null;
        double minDistance = Double.POSITIVE_INFINITY;
        Stack<Node> stack = new Stack<Node>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            if (node == null) {
                continue;
            }
            double distance = query.distanceTo(node.point2D);
            if (distance < minDistance) {
                result = node.point2D;
                minDistance = distance;
            }
            stack.push(node.left);
            stack.push(node.right);
        }

        return result;
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("RectHV must not be null");
        }
        Stack<Point2D> stack = new Stack<>();
        findAll(root, rect, stack);
        return stack;
    }

    private void findAll(Node node, RectHV rect, Stack<Point2D> stack) {
        if (node == null) {
            return;
        }
        RectHV rectHV = node.rectHV;
        if (rect.intersects(rectHV)) {
            if (rect.contains(node.point2D)) {
                stack.push(node.point2D);
            }
            findAll(node.left, rect, stack);
            findAll(node.right, rect, stack);
        }
    }

    private void chackNotNull(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Point must not be null");
        }
    }

    private class Node {
        private Point2D point2D;
        private Node left;
        private Node right;
        private boolean isSplitVertical;
        private RectHV rectHV;

        public Node(Point2D point2D, boolean isSplitVertical) {
            this.point2D = point2D;
            this.isSplitVertical = isSplitVertical;
        }
    }

}
