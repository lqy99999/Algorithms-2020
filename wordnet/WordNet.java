/* *****************************************************************************
 *  Name:    LEE CHINGYEN
 *  NetID:   B06602035
 *
 *  Description:  WordNet is a semantic lexicon for the English language
 *                that computational linguists and cognitive scientists
 *                use extensively.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;

public class WordNet {
    private Digraph wordnet;
    private HashMap<Integer, String> synSets;
    private HashMap<String, Bag<Integer>> synMap;
    private int size;
    private ShortestCommonAncestor sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        synSets = new HashMap<Integer, String>();
        synMap = new HashMap<String, Bag<Integer>>();

        readSyn(synsets);
        wordnet = new Digraph(size);
        readHyp(hypernyms);

        // check that the input does correspond to a rooted DAG
        DirectedCycle dc = new DirectedCycle(wordnet);
        if (dc.hasCycle()) throw new IllegalArgumentException();
        sap = new ShortestCommonAncestor(wordnet);
    }

    private void readSyn(String synsets) {
        if (synsets == null) throw new IllegalArgumentException();

        In in = new In(synsets);
        while (in.hasNextLine()) {
            size++;
            String line = in.readLine();
            String[] splits = line.split(",");
            int id = Integer.parseInt(splits[0]);
            synSets.put(id, splits[1]);
            String[] nouns = splits[1].split(" ");
            for (String n : nouns) {
                if (synMap.get(n) != null) {
                    Bag<Integer> bag = synMap.get(n);
                    bag.add(id);
                }
                else {
                    Bag<Integer> bag = new Bag<Integer>();
                    bag.add(id);
                    synMap.put(n, bag);
                }
            }
        }
    }

    private void readHyp(String hypernyms) {
        if (hypernyms == null) throw new IllegalArgumentException();

        In in = new In(hypernyms);
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] splits = line.split(",");
            int v = Integer.parseInt(splits[0]);
            for (int i = 1; i < splits.length; i++) {
                int w = Integer.parseInt(splits[i]);
                wordnet.addEdge(v, w);
            }
        }
    }

    // all WordNet nouns
    public Iterable<String> nouns() {
        return synMap.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) throw new IllegalArgumentException();
        return synMap.containsKey(word);
    }

    // a synset (second field of synsets.txt) that is a shortest common ancestor
    // of noun1 and noun2 (defined below)
    public String sca(String noun1, String noun2) {
        if (noun1 == null || noun2 == null) throw new IllegalArgumentException();

        Bag<Integer> a = synMap.get(noun1);
        Bag<Integer> b = synMap.get(noun2);

        int id = sap.ancestorSubset(a, b);
        return synSets.get(id);

    }

    // distance between noun1 and noun2 (defined below)
    public int distance(String noun1, String noun2) {
        if (noun1 == null || noun2 == null) throw new IllegalArgumentException();

        Bag<Integer> a = synMap.get(noun1);
        Bag<Integer> b = synMap.get(noun2);

        return sap.lengthSubset(a, b);
    }


    // unit testing (required)
    public static void main(String[] args) {

    }
}
