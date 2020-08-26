/* *****************************************************************************
 *  Name:    LEE CHINGYEN
 *  NetID:   B06602035
 *  Precept: P00
 *
 *  Description: head.pre = null;
 *               tail.next = tail;
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    // first and last queue in deque
    private Node head;
    private Node tail;
    // size of deque
    private int n;

    // Node class
    private class Node {
        private Node pre = null;
        private Node next = null;
        private Item item;
    }

    // construct an empty deque
    public Deque() {
        head = null;
        tail = null;
        n = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node newnode = head;
        head = new Node();
        head.next = newnode;
        head.item = item;

        // check if the deque is empty
        if (n == 0) tail = head;
        else newnode.pre = head;

        n++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node newnode = tail;
        tail = new Node();
        tail.pre = newnode;
        tail.item = item;

        // check if the deque is empty
        if (n == 0) head = tail;
        else newnode.next = tail;

        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (n == 0) throw new java.util.NoSuchElementException();
        Node newnode = head;
        head = head.next;
        newnode.pre = null;
        newnode.next = null;
        n--;
        if (n == 0) tail = null;
        else head.pre = null;

        return newnode.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (n == 0) throw new java.util.NoSuchElementException();
        Node newnode = tail;
        tail = tail.pre;
        newnode.pre = null;
        newnode.next = null;
        n--;
        if (n == 0) head = null;
        else tail.next = null;

        return newnode.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new myIterator();
    }

    private class myIterator implements Iterator<Item> {
        private Node now = head;

        // check if the deque has the next queue
        public boolean hasNext() {
            return now != null;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }

        // move iterator to next queue after returning item
        public Item next() {
            if (now == null) throw new java.util.NoSuchElementException();
            Item t = now.item;
            now = now.next;
            return t;
        }

        // print all the items in deque
        public void allItem() {
            while (hasNext()) {
                StdOut.print(next());
            }
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        System.out.println("=============Test==============");
        System.out.println("---Build a empty Deque---");
        Deque<String> deque = new Deque<String>();
        System.out.print("Empty?  ");
        System.out.println(deque.isEmpty());
        System.out.print("Size?  ");
        System.out.println(deque.size());

        System.out.println("---ADD First: add aabb---");
        deque.addFirst("bb");
        deque.addFirst("aa");

        System.out.println("---ADD Last: add ccdd---");
        deque.addLast("cc");
        deque.addLast("dd");
        System.out.print("Empty?  ");
        System.out.println(deque.isEmpty());
        System.out.print("Size?  ");
        System.out.println(deque.size());

        System.out.println("---Iterator: aabbccdd---");
        ((Deque.myIterator) deque.iterator()).allItem();

        System.out.println("\n" + "---REMOVE: ccdd---");
        deque.removeFirst();
        deque.removeFirst();
        System.out.print("Data: ");
        ((Deque.myIterator) deque.iterator()).allItem();
        System.out.print("\n" + "Size?  ");
        System.out.println(deque.size());

        System.out.println("---REMOVE: null---");
        deque.removeLast();
        deque.removeLast();
        System.out.println("Data: ");
        ((Deque.myIterator) deque.iterator()).allItem();
        System.out.print("Size?  ");
        System.out.println(deque.size());
    }

}
