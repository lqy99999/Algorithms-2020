/* *****************************************************************************
 *  Name:    LEE CHINGYEN
 *  NetID:   B06602035
 *
 *  Description:  WordNet is a semantic lexicon for the English language
 *                that computational linguists and cognitive scientists
 *                use extensively.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;
import java.util.HashSet;

public class ShortestCommonAncestor {

    private Digraph digraph;
    private HashMap<HashSet<Integer>, int[]> cache;

    // constructor takes a rooted DAG as argument
    public ShortestCommonAncestor(Digraph G) {
        // corner case
        if (G == null) throw new IllegalArgumentException();

        digraph = new Digraph(G);
        cache = new HashMap<>();
    }

    // length of shortest ancestral path of vertex subsets A and B
    public int lengthSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        return sap(subsetA, subsetB)[0];
    }

    // a shortest common ancestor of vertex subsets A and B
    public int ancestorSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        return sap(subsetA, subsetB)[1];
    }

    // length of shortest ancestral path between v and w
    public int length(int v, int w) {
        sap(v, w);
        HashSet<Integer> key = new HashSet<>();
        key.add(v);
        key.add(w);
        int[] value = cache.get(key);
        return value[0];
    }

    // a shortest common ancestor of vertices v and w
    public int ancestor(int v, int w) {
        sap(v, w);
        HashSet<Integer> key = new HashSet<>();
        key.add(v);
        key.add(w);
        int[] value = cache.get(key);
        return value[1];
    }


    // helper methods
    private void sap(int v, int w) {
        validateVertex(v);
        validateVertex(w);

        HashSet<Integer> key = new HashSet<>();
        key.add(v);
        key.add(w);

        // check cache
        if (cache.containsKey(key))
            return;

        // bfs from v
        BreadthFirstDirectedPaths vPath = new BreadthFirstDirectedPaths(digraph, v);

        // bfs from w
        BreadthFirstDirectedPaths wPath = new BreadthFirstDirectedPaths(digraph, w);

        int distance = Integer.MAX_VALUE;
        int ancestor = -2;
        // loop through each vertex and find the minimal ancestor length one
        for (int vertex = 0; vertex < digraph.V(); vertex++) {
            if (vPath.hasPathTo(vertex) && vPath.distTo(vertex) < distance && wPath
                    .hasPathTo(vertex)
                    && wPath.distTo(vertex) < distance) {
                int sum = vPath.distTo(vertex) + wPath.distTo(vertex);
                if (distance > sum) {
                    distance = sum;
                    ancestor = vertex;
                }
            }
        }

        if (distance == Integer.MAX_VALUE) {
            // which means no such path
            distance = -1;
            ancestor = -1;
        }

        int[] value = new int[] { distance, ancestor };
        cache.put(key, value);
    }

    private int[] sap(Iterable<Integer> v, Iterable<Integer> w) {
        validateVertices(v);
        validateVertices(w);

        // bfs from v
        BreadthFirstDirectedPaths vPath = new BreadthFirstDirectedPaths(digraph, v);

        // bfs from w
        BreadthFirstDirectedPaths wPath = new BreadthFirstDirectedPaths(digraph, w);

        int distance = Integer.MAX_VALUE;
        int ancestor = -2;
        // loop through each vertex and find the minimal ancestor length one
        for (int vertex = 0; vertex < digraph.V(); vertex++) {
            if (vPath.hasPathTo(vertex) && vPath.distTo(vertex) < distance && wPath
                    .hasPathTo(vertex)
                    && wPath.distTo(vertex) < distance) {
                int sum = vPath.distTo(vertex) + wPath.distTo(vertex);
                if (distance > sum) {
                    distance = sum;
                    ancestor = vertex;
                }
            }
        }

        if (distance != Integer.MAX_VALUE) {
            return new int[] { distance, ancestor };
        }
        else {
            return new int[] { -1, -1 };
        }
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int n = digraph.V();
        if (v < 0 || v >= n) throw new IllegalArgumentException();
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertices(Iterable<Integer> vertices) {
        if (vertices == null) throw new IllegalArgumentException();

        int n = digraph.V();
        for (int v : vertices) {
            if (v < 0 || v >= n) throw new IllegalArgumentException();
        }
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        ShortestCommonAncestor sca = new ShortestCommonAncestor(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sca.length(v, w);
            int ancestor = sca.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }

}
