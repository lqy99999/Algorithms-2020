/* *****************************************************************************
 *  Name:    LEE CHINGYEN
 *  NetID:   B06602035
 *
 *  Description:  a mutable data type PointST.java that uses a red–black BST
 *                to represent a symbol table whose keys are two-dimensional
 *                points
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.RedBlackBST;

public class PointST<Value> {
    RedBlackBST<Point2D, Value> rbt;

    // construct an empty symbol table of points
    public PointST() {
        rbt = new RedBlackBST<Point2D, Value>();
    }

    // is the symbol table empty?
    public boolean isEmpty() {
        return rbt.isEmpty();
    }

    // number of points
    public int size() {
        return rbt.size();
    }

    // associate the value val with point p
    public void put(Point2D p, Value val) {
        isNull(p);
        isNull(val);
        rbt.put(p, val);
    }

    // value associated with point p
    public Value get(Point2D p) {
        isNull(p);
        return rbt.get(p);
    }

    // does the symbol table contain point p?
    public boolean contains(Point2D p) {
        isNull(p);
        return rbt.contains(p);
    }

    // all points in the symbol table
    public Iterable<Point2D> points() {
        Iterable<Point2D> p = rbt.keys();
        Queue<Point2D> elements = new Queue<Point2D>();

        for (Point2D i : p) elements.enqueue(i);
        return elements;
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        isNull(rect);
        Point2D min = new Point2D(rect.xmin(), rect.ymin());
        Point2D max = new Point2D(rect.xmax(), rect.ymax());

        Iterable<Point2D> p = rbt.keys(min, max);
        Queue<Point2D> elements = new Queue<Point2D>();

        for (Point2D i : p) {
            if (rect.contains(i)) {
                elements.enqueue(i);
            }
        }
        return elements;
    }

    // a nearest neighbor of point p; null if the symbol table is empty
    public Point2D nearest(Point2D p) {
        isNull(p);
        if (isEmpty()) return null;

        Iterable<Point2D> points = rbt.keys();

        Point2D curr = null;
        for (Point2D i : points) {
            if (curr == null) curr = i;
            if (p.distanceSquaredTo(curr) > p.distanceSquaredTo(i) && p.compareTo(i) != 0) {
                curr = i;
            }
        }
        return curr;
    }

    // check if the object a is null
    private void isNull(Object a) {
        if (a == null) throw new IllegalArgumentException();
    }


    // unit testing (required)
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);

        PointST<Integer> mySet = new PointST<Integer>();
        for (int i = 0; !in.isEmpty(); i++) {
            double x = in.readDouble();
            double y = in.readDouble();

            Point2D p = new Point2D(x, y);
            mySet.put(p, i);
        }
        long time1, time2, num1;
        Point2D point;
        time1 = System.nanoTime();
        for (int i = 0; i < 100; i++) {
            point = mySet.nearest(new Point2D(0.148684, 0.884150));
        }
        time2 = System.nanoTime();

        System.out.println("程式執行時間： " + (time2 - time1) + "ns");

        // int N = Integer.parseInt(args[0]);
        //
        // PointST<Double> mySet = new PointST<Double>();
        // System.out.println("Myset is empty. -> " + mySet.isEmpty());
        // System.out.println("===Start put points===");
        // Point2D a = new Point2D(0.99, 0.99);
        // mySet.put(a, 0.99);
        // System.out.print(a.toString() + " ");
        // for (int i = 1; i < N; i++) {
        //     double x = ((double) StdRandom.uniform(100)) / 100.0;
        //     double y = ((double) StdRandom.uniform(100)) / 100.0;
        //
        //     Point2D point = new Point2D(x, y);
        //     mySet.put(point, (x + y) / 2);
        //     System.out.print(point.toString() + " ");
        // }
        // System.out.println("\nMyset is empty. -> " + mySet.isEmpty());
        // System.out.println("Myset size: " + N + " -> " + mySet.size());
        // System.out.println(
        //         "Myset contains (0.99,0.99): -> " + mySet.contains(new Point2D(0.99, 0.99)));
        // System.out.println(
        //         "Myset gets (0.99,0.99) value = 0.99: -> " + mySet.get(new Point2D(0.99, 0.99)));
        //
        // //draw the nearest neighbor
        // Point2D p = new Point2D(0.5, 0.5);
        // System.out.println("The closest point to (0.5,0.5) is " + mySet.nearest(p).toString());
        //
        // //draw point within a range
        // RectHV myRect = new RectHV(0.5, 0.5, 0.99, 0.99);
        // Iterable<Point2D> rangeSet = mySet.range(myRect);
        //
        // for (Point2D point : rangeSet) {
        //     System.out.println("Point inside range(0.5, 0.5, 0.99, 0.99) is " + point.toString());
        // }
    }

}
