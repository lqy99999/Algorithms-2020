/* *****************************************************************************
 *  Name:    LEE CHINGYEN
 *  NetID:   B06602035
 *  Precept: P00
 *
 *  Description: Randomized Queue
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int n; // the number of items
    private Item[] rq;

    // construct an empty randomized queue
    // default array length = 1;
    public RandomizedQueue() {
        rq = (Item[]) new Object[1];
        n = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // resize randomize queue when it is full
    private void resize(int newl) {
        Item[] tmp = (Item[]) new Object[newl];
        for (int i = 0; i < n; ++i) {
            tmp[i] = rq[i];
        }
        rq = tmp;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (n == rq.length) resize(rq.length * 2);

        rq[n] = item;
        n++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        int rand = StdRandom.uniform(0, n);
        Item t = rq[rand];


        //move the last item to rq[rand]
        rq[rand] = rq[n - 1];

        rq[n - 1] = null;

        //check the size of randomized queue
        if (n > 0 && n < rq.length / 4) resize(rq.length / 2);

        n--;
        return t;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        int rand = StdRandom.uniform(0, n);

        return rq[rand];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new myIterator();
    }

    private class myIterator implements Iterator<Item> {
        private int idx;
        private Item[] now = (Item[]) new Object[n];

        // default constructor
        // shuflle queue here
        public myIterator() {
            for (int i = 0; i < n; i++) {
                now[i] = rq[i];
            }
            StdRandom.shuffle(now);
            idx = n - 1;
        }

        public boolean hasNext() {
            return idx >= 0;
        }

        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            return now[idx--];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        // print all items
        public void showItem() {
            while (hasNext()) StdOut.print(next());
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        System.out.println("=============Test==============");
        System.out.println("---Build a empty Randomized Queue---");
        RandomizedQueue<String> deque = new RandomizedQueue<String>();
        System.out.print("Empty?  ");
        System.out.println(deque.isEmpty());
        System.out.print("Size?  ");
        System.out.println(deque.size());

        System.out.println("---ADD: add aabbccdd---");
        deque.enqueue("dd");
        deque.enqueue("cc");
        deque.enqueue("bb");
        deque.enqueue("aa");

        System.out.print("Empty?  ");
        System.out.println(deque.isEmpty());
        System.out.print("Size?  ");
        System.out.println(deque.size());

        System.out.println("---Iterator: aabbccdd---");
        ((RandomizedQueue.myIterator) deque.iterator()).showItem();

        System.out.println("\n---Sample---");
        System.out.println("Sample1: " + deque.sample());
        System.out.println("Sample2: " + deque.sample());
        System.out.println("Sample3: " + deque.sample());
        System.out.println("Sample4: " + deque.sample());
        System.out.println("Sample5: " + deque.sample());

        System.out.println("---REMOVE---");
        System.out.println("delete: " + deque.dequeue());
        System.out.println("delete: " + deque.dequeue());
        System.out.print("Data: ");
        ((RandomizedQueue.myIterator) deque.iterator()).showItem();
        System.out.print("\n" + "Size?  ");
        System.out.println(deque.size());

        System.out.println("---REMOVE: null---");
        System.out.println("delete: " + deque.dequeue());
        System.out.println("delete: " + deque.dequeue());
        System.out.print("Data: ");
        ((RandomizedQueue.myIterator) deque.iterator()).showItem();
        System.out.print("\nSize?  ");
        System.out.println(deque.size());
    }

}
