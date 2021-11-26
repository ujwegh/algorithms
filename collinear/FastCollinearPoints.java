import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.Arrays;

public class FastCollinearPoints {

    private final Stack<LineSegment> lineSegments = new Stack<>();

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Points array must not be null");
        }
        int length = points.length;

        for (int i = 0; i < points.length; i++) {
            Point a = points[i];
            checkPointNotNull(a);
            Point[] restPoints = new Point[length - i - 1];
            int g = 0;
            for (int j = i + 1; j < points.length; j++) {
                Point b = points[j];
                checkPointNotNull(b);
                if (a.compareTo(b) == 0) {
                    throw new IllegalArgumentException(
                            String.format("Points %s and %s must not be same", a.toString(),
                                          b.toString()));
                }
                restPoints[g] = b;
                g++;
            }
            Arrays.sort(restPoints, a.slopeOrder());
            Stack<Point> sames = new Stack<>();
            double slopeValue = 0;
            int k = 1;

            for (int jj = 0; jj < restPoints.length; jj++) {
                Point point = restPoints[jj];
                double slope = a.slopeTo(point);
                if (jj > 0 && slopeValue == slope) {
                    k++;
                }
                else {
                    k = 1;
                }
                slopeValue = slope;

                if (k >= 3) {
                    sames.push(point);
                }
            }
            if (sames.size() > 0) {
                lineSegments.push(new LineSegment(a, sames.peek()));
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
        Stopwatch stopwatch = new Stopwatch();
        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        System.out.println("Elapsed time: " + stopwatch.elapsedTime());
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
