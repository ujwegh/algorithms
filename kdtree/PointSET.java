import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;

import java.util.TreeSet;

public class PointSET {

    private TreeSet<Point2D> set;

    public PointSET() { // construct an empty set of points
        set = new TreeSet<>();
    }

    public boolean isEmpty() { // is the set empty?
        return set.isEmpty();
    }

    public int size() { // number of points in the set
        return set.size();
    }

    public void insert(Point2D p) { // add the point to the set (if it is not already in the set)
        chackNotNull(p);
        set.add(p);
    }

    public boolean contains(Point2D p) { // does the set contain point p?
        chackNotNull(p);
        return set.contains(p);
    }

    public void draw() { // draw all points to standard draw
        set.forEach(Point2D::draw);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("RectHV must not be null");
        }
        Queue<Point2D> queue = new Queue<>();
        set.forEach(point2D -> {
            if (rect.contains(point2D)) {
                queue.enqueue(point2D);
            }
        });
        return queue;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        chackNotNull(p);
        if (set.isEmpty()) {
            return null;
        }
        Point2D result = null;
        double minDistance = Double.POSITIVE_INFINITY;
        for (Point2D point2D : set) {
            double distance = p.distanceTo(point2D);
            if (distance <= minDistance) {
                minDistance = distance;
                result = point2D;
            }
        }
        return result;
    }

    public static void main(String[] args) { // unit testing of the methods (optional)

    }

    private void chackNotNull(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Point must not be null");
        }
    }
}
