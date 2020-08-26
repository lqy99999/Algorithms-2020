/* *****************************************************************************
 *  Name:    LEE CHINGYEN
 *  NetID:   B06602035
 *  Precept: P00
 *
 *
 *  Description:  BinarySearchDeluxe
 *
 **************************************************************************** */

import java.util.Comparator;

public class BinarySearchDeluxe {

    // Returns the index of the first key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
        if (a == null || key == null || comparator == null) throw new IllegalArgumentException();

        int lo = 0;
        int hi = a.length - 1;

        while (hi >= lo) {
            int mid = lo + (hi - lo) / 2;
            if (comparator.compare(key, a[mid]) > 0) lo = mid + 1;
            else if (comparator.compare(key, a[mid]) < 0) hi = mid - 1;
            else {
                while (mid >= 0 && comparator.compare(a[mid], key) == 0)
                    mid--;
                return mid + 1;
            }
        }

        return -1;
    }

    // Returns the index of the last key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
        if (a == null || key == null || comparator == null) throw new IllegalArgumentException();

        int lo = 0;
        int hi = a.length - 1;

        while (hi >= lo) {
            int mid = lo + (hi - lo) / 2;
            if (comparator.compare(a[mid], key) > 0) hi = mid - 1;
            else if (comparator.compare(a[mid], key) < 0) lo = mid + 1;
            else {
                while (mid < a.length && comparator.compare(a[mid], key) == 0)
                    mid++;
                return mid - 1;
            }
        }

        return -1;
    }

    // unit testing (required)
    public static void main(String[] args) {
        //     Integer[] numbers = {
        //             10, 10, 10, 9, 9, 9, 8, 8, 8, 7, 7, 7, 6, 6, 6, 5, 5, 5, 4, 4, 4, 3, 3, 3, 2, 2, 2,
        //             1, 1, 1
        //     };
        //     System.out.println("-----Array-----");
        //     for (Integer a : numbers) {
        //         System.out.print(a + " ");
        //     }
        //     System.out.println("\n-----Test 10: first / last-----");
        //     System.out.print(BinarySearchDeluxe.firstIndexOf(numbers, 10, Collections.reverseOrder())
        //                              + "\t");
        //     System.out.println(BinarySearchDeluxe.lastIndexOf(numbers, 10, Collections.reverseOrder()));
        //
        //     System.out.println("-----Test 9: first / last-----");
        //     System.out.print(BinarySearchDeluxe.firstIndexOf(numbers, 9, Collections.reverseOrder())
        //                              + "\t");
        //     System.out.println(BinarySearchDeluxe.lastIndexOf(numbers, 9, Collections.reverseOrder()));
        //
        //     System.out.println("-----Test 4: first / last-----");
        //     System.out.print(BinarySearchDeluxe.firstIndexOf(numbers, 4, Collections.reverseOrder())
        //                              + "\t");
        //     System.out.println(BinarySearchDeluxe.lastIndexOf(numbers, 4, Collections.reverseOrder()));
        //
        //     System.out.println("-----Test 0: first / last-----");
        //     System.out.print(BinarySearchDeluxe.firstIndexOf(numbers, 0, Collections.reverseOrder())
        //                              + "\t");
        //     System.out.println(BinarySearchDeluxe.lastIndexOf(numbers, 0, Collections.reverseOrder()));
        //
        //     System.out.println("-----Test 11: first / last-----");
        //     System.out.print(BinarySearchDeluxe.firstIndexOf(numbers, 11, Collections.reverseOrder())
        //                              + "\t");
        //     System.out.println(BinarySearchDeluxe.lastIndexOf(numbers, 11, Collections.reverseOrder()));
    }
}
