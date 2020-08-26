/* *****************************************************************************
 *  Name:    LEE CHINGYEN
 *  NetID:   B06602035
 *  Precept: P00
 *
 *
 *  Description:  Term
 *
 **************************************************************************** */

import java.util.Comparator;

public class Term implements Comparable<Term> {
    private String query;
    private long weight;

    // Initializes a term with the given query string and weight.
    public Term(String query, long weight) {
        if (query.equals("") || weight < 0) throw new IllegalArgumentException();
        this.query = query;
        this.weight = weight;
    }

    private static class ReverseWeightOrder implements Comparator<Term> {
        public int compare(Term a, Term b) {
            return Long.compare(b.weight, a.weight);

            // if (a.weight > b.weight) return -1;
            // else if (a.weight < b.weight) return 1;
            // else return 0;
        }
    }

    // Compares the two terms in descending order by weight.
    public static Comparator<Term> byReverseWeightOrder() {
        return new ReverseWeightOrder();
    }

    private static class PrefixOrder implements Comparator<Term> {
        private int r;

        public PrefixOrder(int r) {
            this.r = r;
        }

        public int compare(Term a, Term b) {
            String a_name;
            String b_name;
            if (a.query.length() < r) a_name = a.query;
            else a_name = a.query.substring(0, r);

            if (b.query.length() < r) b_name = b.query;
            else b_name = b.query.substring(0, r);

            return a_name.compareTo(b_name);
        }
    }

    // Compares the two terms in lexicographic order,
    // but using only the first r characters of each query.
    public static Comparator<Term> byPrefixOrder(int r) {
        return new PrefixOrder(r);
    }

    // Compares the two terms in lexicographic order by query.
    public int compareTo(Term that) {
        return this.query.compareTo(that.query);
    }

    // Returns a string representation of this term in the following format:
    // the weight, followed by a tab, followed by the query.
    public String toString() {
        return Long.toString(weight) + "\t" + query;
    }

    // unit testing (required)
    public static void main(String[] args) {
        //     System.out.println("-----Create Terms-----");
        //     Term[] terms = {
        //             new Term("Andrews", 3), new Term("Battle", 4),
        //             new Term("Batel", 3), new Term("Fox", 1)
        //     };
        //
        //     for (Term a : terms) {
        //         System.out.println(a.query + " " + a.weight);
        //     }
        //
        //     System.out.println("-----ReverseWeightOrder-----");
        //     Arrays.sort(terms, byReverseWeightOrder());
        //     for (Term a : terms) {
        //         System.out.println(a.query + " " + a.weight);
        //     }
        //
        //     System.out.println("-----PrefixOrder 3-----");
        //     Arrays.sort(terms, byPrefixOrder(3));
        //     for (Term a : terms) {
        //         System.out.println(a.query + " " + a.weight);
        //     }
        //
        //     System.out.println("-----PrefixOrder 4-----");
        //     Arrays.sort(terms, byPrefixOrder(4));
        //     for (Term a : terms) {
        //         System.out.println(a.query + " " + a.weight);
        //     }
        //
        //
        //     System.out.println("-----Completed.-----");
    }

}

