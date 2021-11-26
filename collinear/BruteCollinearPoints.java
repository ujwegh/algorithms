import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {

    private final Stack<LineSegment> lineSegments = new Stack<>();

    public BruteCollinearPoints(Point[] points) { // finds all line segments containing 4 points
        if (points == null) {
            throw new IllegalArgumentException("Points array must not be null");
        }
        int length = points.length;
        Point[] innerPoints = new Point[length];

        for (int i = 0; i < length; i++) {
            Point a = points[i];
            checkPointNotNull(a);
            for (int j = i + 1; j < length; j++) {
                Point b = points[j];
                checkPointNotNull(b);
                if (a.compareTo(b) == 0) {
                    throw new IllegalArgumentException(
                            String.format("Points %s and %s must not be same", a.toString(),
                                          b.toString()));
                }
            }
            innerPoints[i] = a;
        }
        Arrays.sort(innerPoints);
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                for (int k = j + 1; k < length; k++) {
                    for (int m = k + 1; m < length; m++) {
                        Point pI = innerPoints[i];
                        Point jP = innerPoints[j];
                        Point kP = innerPoints[k];
                        Point mP = innerPoints[m];
                        if (pI.slopeTo(jP) == pI.slopeTo(kP) && pI.slopeTo(kP) == pI.slopeTo(mP)) {
                            lineSegments.push(new LineSegment(innerPoints[i], innerPoints[m]));
                        }
                    }
                }
            }
        }
    }

    private void checkPointNotNull(Point point) {
        if (point == null) {
            throw new IllegalArgumentException("Point must not be null");
        }
    }

    public int numberOfSegments() { // the number of line segments
        return lineSegments.size();
    }

    public LineSegment[] segments() { // the line segments
        LineSegment[] result = new LineSegment[lineSegments.size()];
        int k = 0;
        for (LineSegment lineSegment : lineSegments) {
            result[k] = lineSegment;
            k++;
        }
        return result;
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {

            int x = in.readInt();
            int y = in.readInt();
            System.out.println(x + " - " + y);
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
