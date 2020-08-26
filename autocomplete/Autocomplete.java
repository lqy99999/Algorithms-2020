/* *****************************************************************************
 *  Name:    LEE CHINGYEN
 *  NetID:   B06602035
 *  Precept: P00
 *
 *
 *  Description:  Autocomplete
 *
 **************************************************************************** */

import java.util.Arrays;

public class Autocomplete {
    private Term[] terms;

    // Initializes the data structure from the given array of terms.
    public Autocomplete(Term[] terms) {
        if (terms == null) throw new java.lang.IllegalArgumentException();
        for (Term i : terms) {
            if (i == null) throw new java.lang.IllegalArgumentException();
        }

        this.terms = terms;
        Arrays.sort(this.terms);
    }

    // Returns all terms that start with the given prefix, in descending order of weight.
    public Term[] allMatches(String prefix) {
        if (prefix == null) throw new NullPointerException();
        Term a = new Term(prefix, 0);

        int start = BinarySearchDeluxe.firstIndexOf(terms, a, Term.byPrefixOrder(prefix.length()));
        if (start < 0) return new Term[] { };
        int end = BinarySearchDeluxe.lastIndexOf(terms, a, Term.byPrefixOrder(prefix.length()));
        if (end < 0) return new Term[] { };

        Term[] b = new Term[end - start + 1];
        for (int i = start; i <= end; i++) {
            b[i - start] = terms[i];
        }

        Arrays.sort(b, Term.byReverseWeightOrder());
        return b;
    }

    // Returns the number of terms that start with the given prefix.
    public int numberOfMatches(String prefix) {
        if (prefix == null) throw new NullPointerException();
        Term a = new Term(prefix, 0);

        int start = BinarySearchDeluxe.firstIndexOf(terms, a, Term.byPrefixOrder(prefix.length()));
        if (start < 0) return 0;
        int end = BinarySearchDeluxe.lastIndexOf(terms, a, Term.byPrefixOrder(prefix.length()));
        if (end < 0) return 0;

        return end - start + 1;
    }

    // unit testing (required)
    public static void main(String[] args) {
        // // read in the terms from a file
        // String filename = args[0];
        // In in = new In(filename);
        // int n = in.readInt();
        // Term[] terms = new Term[n];
        // for (int i = 0; i < n; i++) {
        //     long weight = in.readLong();           // read the next weight
        //     in.readChar();                         // scan past the tab
        //     String query = in.readLine();          // read the next query
        //     terms[i] = new Term(query, weight);    // construct the term
        // }
        //
        // // read in queries from standard input and print the top k matching terms
        // int k = Integer.parseInt(args[1]);
        // Autocomplete autocomplete = new Autocomplete(terms);
        // while (StdIn.hasNextLine()) {
        //     String prefix = StdIn.readLine();
        //     Term[] results = autocomplete.allMatches(prefix);
        //     StdOut.printf("%d matches\n", autocomplete.numberOfMatches(prefix));
        //     for (int i = 0; i < Math.min(k, results.length); i++)
        //         StdOut.println(results[i]);
        // }
    }

}
