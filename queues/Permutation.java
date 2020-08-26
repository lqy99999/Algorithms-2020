/* *****************************************************************************
 *  Name:    LEE CHINGYEN
 *  NetID:   B06602035
 *  Precept: P00
 *
 *  Description: Permutation
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        if (args.length != 1) {
            System.out.println("Invalid argument!");
            return;
        }

        while (!StdIn.isEmpty())
            rq.enqueue(StdIn.readString());

        int num = Integer.parseInt(args[0]);
        for (int i = 0; i < num; i++)
            StdOut.println(rq.dequeue());

        return;
    }
}
