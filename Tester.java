import java.util.Arrays;

/**
 * Tester
 *
 * FOR LOCAL TESTING ONLY - do NOT submit this file with your assignment.
 * The assignment explicitly says not to submit a class with a main method,
 * so keep this file out of your submission (only UnorderedArray.java and
 * OrderedArray.java should be turned in).
 *
 * Run with:
 *   javac UnorderedArray.java OrderedArray.java Tester.java
 *   java Tester
 */
public class Tester {

    public static void main(String[] args) {
        testUnorderedArray();
        System.out.println();
        testOrderedArray();
    }

    private static void testUnorderedArray() {
        System.out.println("===== Testing UnorderedArray =====");
        UnorderedArray ua = new UnorderedArray(3);

        // Insert within capacity
        ua.insert(10);
        ua.insert(30);
        ua.insert(20);
        print("After inserting 10, 30, 20", debugString(ua));
        check("size() == 3", ua.size() == 3);
        check("count() == 3", ua.count() == 3);

        // Trigger auto-resize
        ua.insert(40);
        print("After inserting 40 (triggers resize)", debugString(ua));
        check("size() doubled to 6", ua.size() == 6);
        check("count() == 4", ua.count() == 4);

        // find()
        check("find(20) == 2", ua.find(20) == 2);
        check("find(99) == -1", ua.find(99) == -1);

        // get() and bounds checking
        check("get(0) == 10", ua.get(0) == 10);
        try {
            ua.get(100);
            check("get(100) should throw", false);
        } catch (IndexOutOfBoundsException e) {
            check("get(100) throws IndexOutOfBoundsException", true);
        }

        // delete()
        boolean removed = ua.delete(30);
        print("After delete(30)", debugString(ua));
        check("delete(30) returned true", removed);
        check("count() == 3 after delete", ua.count() == 3);
        check("elements shifted left (no gap)", ua.get(0) == 10 && ua.get(1) == 20 && ua.get(2) == 40);

        boolean removedMissing = ua.delete(999);
        check("delete(999) returns false", !removedMissing);

        // resize() manually
        ua.resize(2);
        print("After resize(2)", debugString(ua));
        check("size() == 2 after shrink", ua.size() == 2);
        check("count() == 2 after shrink", ua.count() == 2);
    }

    private static void testOrderedArray() {
        System.out.println("===== Testing OrderedArray =====");
        OrderedArray oa = new OrderedArray(3);

        // Insert out of order; array should stay sorted
        oa.insert(30);
        oa.insert(10);
        oa.insert(20);
        print("After inserting 30, 10, 20", debugString(oa));
        check("stays sorted [10,20,30]", oa.get(0) == 10 && oa.get(1) == 20 && oa.get(2) == 30);

        // Trigger auto-resize
        oa.insert(5);
        print("After inserting 5 (triggers resize)", debugString(oa));
        check("size() doubled to 6", oa.size() == 6);
        check("stays sorted [5,10,20,30]", oa.get(0) == 5 && oa.get(1) == 10 && oa.get(2) == 20 && oa.get(3) == 30);

        // find() via binary search
        check("find(20) == 2", oa.find(20) == 2);
        check("find(99) == -1", oa.find(99) == -1);

        // get() and bounds checking
        try {
            oa.get(-1);
            check("get(-1) should throw", false);
        } catch (IndexOutOfBoundsException e) {
            check("get(-1) throws IndexOutOfBoundsException", true);
        }

        // delete()
        boolean removed = oa.delete(10);
        print("After delete(10)", debugString(oa));
        check("delete(10) returned true", removed);
        check("still sorted after delete [5,20,30]", oa.get(0) == 5 && oa.get(1) == 20 && oa.get(2) == 30);

        boolean removedMissing = oa.delete(999);
        check("delete(999) returns false", !removedMissing);

        // resize() manually
        oa.resize(2);
        print("After resize(2)", debugString(oa));
        check("size() == 2 after shrink", oa.size() == 2);
        check("count() == 2 after shrink", oa.count() == 2);
    }

    // ---- tiny helper utilities for readable test output ----

    // Builds a printable snapshot of an UnorderedArray using only its public API.
    private static String debugString(UnorderedArray a) {
        Integer[] snapshot = new Integer[a.size()];
        for (int i = 0; i < a.size(); i++) {
            snapshot[i] = a.get(i);
        }
        return Arrays.toString(snapshot) + " (count=" + a.count() + ", size=" + a.size() + ")";
    }

    // Builds a printable snapshot of an OrderedArray using only its public API.
    private static String debugString(OrderedArray a) {
        Integer[] snapshot = new Integer[a.size()];
        for (int i = 0; i < a.size(); i++) {
            snapshot[i] = a.get(i);
        }
        return Arrays.toString(snapshot) + " (count=" + a.count() + ", size=" + a.size() + ")";
    }

    private static void print(String label, String state) {
        System.out.println(label + ": " + state);
    }

    private static void check(String description, boolean passed) {
        System.out.println((passed ? "  [PASS] " : "  [FAIL] ") + description);
    }
}