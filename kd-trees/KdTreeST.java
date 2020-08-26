/* *****************************************************************************
 *  Name:    LEE CHINGYEN
 *  NetID:   B06602035
 *
 *  Description:  Prints 'Hello, World' to the terminal window.
 *                By tradition, this is everyone's first program.
 *                Prof. Brian Kernighan initiated this tradition in 1974.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;

/**
 * https://github.com/mincong-h/algorithm-princeton/blob/master/kdtree/KdTree.java
 */
public class KdTreeST<Value> {
    private Node root;
    private int size;

    private class Node {
        private Node lb; // the left/bottom subtree
        private Node rt; // the right/top subtree
        private Point2D p; // the point
        private Value value; // the symbol table maps the point to this value
        private RectHV rect; // the axis-aligned rectangle corresponding to this node
        private boolean ver; // vertical=1, horizontal=0;

        /**
         * @param p:     point(x,y)
         * @param value: the value of point p
         * @param ver:   vertical or horizontal
         * @param rect:  The rect of root is boundary.
         */
        public Node(Point2D p, Value value, boolean ver, RectHV rect) {
            this.p = p;
            this.value = value;
            this.ver = ver;
            this.rect = rect;
        }

        /**
         * The rectangle on the left/bottom of node
         */
        public RectHV rect_lb() {
            if (ver) return new RectHV(rect.xmin(), rect.ymin(), p.x(), rect.ymax());
            else return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), p.y());
        }

        /**
         * The rectangle on the right/top of node
         */
        public RectHV rect_rt() {
            if (ver) return new RectHV(p.x(), rect.ymin(), rect.xmax(), rect.ymax());
            else return new RectHV(rect.xmin(), p.y(), rect.xmax(), rect.ymax());
        }

        /**
         * Check if the point p is on the right of node or not
         */
        public boolean isRT(Point2D p) {
            isNull(p);
            if (ver) {
                return (p.x() >= this.p.x());
            }
            else {
                return (p.y() >= this.p.y());
            }
        }
    }

    // construct an empty root
    public KdTreeST() {
        root = null;
        size = 0;
    }

    // is the symbol table empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points
    public int size() {
        return size;
    }

    // associate the value val with point p
    public void put(Point2D p, Value val) {
        isNull(p);
        if (isEmpty()) {
            root = new Node(p, val, true, new RectHV(0, 0, 1, 1));
            size++;
            return;
        }

        Node pre = null; // to remember the curr
        Node curr = root;
        while (curr != null) {
            pre = curr;
            if (curr.p.equals(p)) return;

            if (curr.isRT(p)) curr = curr.rt;
            else curr = curr.lb;
        }

        if (pre.isRT(p)) pre.rt = new Node(p, val, !(pre.ver), pre.rect_rt());
        else pre.lb = new Node(p, val, !(pre.ver), pre.rect_lb());

        size++;
    }

    // value associated with point p
    public Value get(Point2D p) {
        return get(root, p);
    }

    // value associated with point p
    private Value get(Node x, Point2D p) {
        if (x == null) return null;

        if (x.p.equals(p)) return x.value;

        if (x.isRT(p)) return get(x.rt, p);
        else return get(x.lb, p);
    }

    // does the symbol table contain point p?
    public boolean contains(Point2D p) {
        isNull(p);
        return get(p) != null;
    }

    // all points in the symbol table
    // level order traversal
    public Iterable<Point2D> points() {
        if (root == null) return null;
        Queue<Point2D> queue = new Queue<Point2D>(); // record all points
        Queue<Node> detec = new Queue<Node>(); // to do level order traversal

        detec.enqueue(root);
        while (!detec.isEmpty()) {
            Node n = detec.dequeue();
            queue.enqueue(n.p);

            if (n.lb != null) detec.enqueue(n.lb);
            if (n.rt != null) detec.enqueue(n.rt);
        }
        return queue;
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        isNull(rect);
        if (isEmpty()) return null;
        Queue<Point2D> queue = new Queue<Point2D>();

        addpoints(root, rect, queue);
        return queue;
    }

    /**
     * https://github.com/mincong-h/algorithm-princeton/blob/master/kdtree/KdTree.java
     */
    private void addpoints(Node node, RectHV rect, Queue<Point2D> queue) {
        if (node == null) return;

        if (rect.contains(node.p)) {
            queue.enqueue(node.p);
            addpoints(node.lb, rect, queue);
            addpoints(node.rt, rect, queue);
            return;
        }
        if (!node.isRT(new Point2D(rect.xmin(), rect.ymin()))) {
            addpoints(node.lb, rect, queue);
        }
        if (node.isRT(new Point2D(rect.xmax(), rect.ymax()))) {
            addpoints(node.rt, rect, queue);
        }
    }

    // a nearest neighbor of point p; null if the symbol table is empty
    public Point2D nearest(Point2D p) {
        isNull(p);
        if (isEmpty()) return null;

        return nearpoint(p, root, root.p);
    }

    /**
     * https://github.com/mincong-h/algorithm-princeton/blob/master/kdtree/KdTree.java
     */
    private Point2D nearpoint(Point2D point, Node node, Point2D closest) {
        if (node == null) return closest;

        double distance = closest.distanceSquaredTo(point);

        if (node.rect.distanceTo(point) < distance) {
            double nodeDist = node.p.distanceSquaredTo(point);
            if (nodeDist < distance) closest = node.p;

            if (node.isRT(point)) {
                closest = nearpoint(point, node.rt, closest);
                closest = nearpoint(point, node.lb, closest);
            }
            else {
                closest = nearpoint(point, node.lb, closest);
                closest = nearpoint(point, node.rt, closest);
            }
        }
        return closest;
    }

    // check if the object a is null
    private void isNull(Object a) {
        if (a == null) throw new IllegalArgumentException();
    }

    // unit testing (required)
    public static void main(String[] args) {
        // String filename = args[0];
        // In in = new In(filename);
        //
        // KdTreeST<Integer> mySet = new KdTreeST<Integer>();
        // for (int i = 0; !in.isEmpty(); i++) {
        //     double x = in.readDouble();
        //     double y = in.readDouble();
        //
        //     Point2D p = new Point2D(x, y);
        //     mySet.put(p, i);
        // }
        // long time1, time2, num1;
        // Point2D point;
        // time1 = System.nanoTime();
        // for (int i = 0; i < 100; i++) {
        //     point = mySet.nearest(new Point2D(0.148684, 0.884150));
        // }
        // time2 = System.nanoTime();
        //
        // System.out.println("程式執行時間： " + (time2 - time1) + "ns");

        String filename = args[0];
        In in = new In(filename);
        KdTreeST<Integer> kdtree = new KdTreeST<Integer>();

        // create kd-tree
        for (int i = 0; !in.isEmpty(); i++) {
            double x = in.readDouble();
            double y = in.readDouble();

            Point2D p = new Point2D(x, y);
            kdtree.put(p, i);
        }

        Iterable<Point2D> q = kdtree.points();
        for (Point2D i : q) {
            System.out.println(i.toString());
        }
    }
}
