/**
 * OrderedArray
 *
 * Implements a resizable array of Integer objects where non-null
 * elements are always kept in ascending sorted order. Keeping the
 * array sorted allows searching with binary search (O(log n)), but
 * insertion and deletion are slower than in an unordered array because
 * elements must be shifted to maintain order.
 *
 * Null entries in arr[] represent unused positions and always appear
 * after all non-null (sorted) elements.
 */
public class OrderedArray {

    private Integer[] arr;   // backing storage; null = empty slot
    private int count;       // number of non-null elements currently stored

    /**
     * Constructor.
     * Time Complexity: O(n) - allocating and initializing an array of size n.
     *
     * @param size initial capacity of the array
     */
    public OrderedArray(int size) {
        arr = new Integer[size];
        count = 0;
    }

    /**
     * Inserts x into the array while keeping arr[] sorted in ascending
     * order. The correct insertion position is located with binary
     * search, and elements to the right of that position are shifted
     * one slot to the right to make room. If the array is full, it is
     * resized (doubled) before inserting.
     *
     * Time Complexity: O(n)
     *   - O(log n) to locate the insertion point via binary search.
     *   - O(n) worst case to shift existing elements to make room.
     *   Overall this is O(n), dominated by the shifting step.
     *
     * @param x the integer to insert
     */
    public void insert(int x) {
        if (count == arr.length) {
            // Array is full - double the capacity before inserting.
            resize(arr.length == 0 ? 1 : arr.length * 2);
        }

        int insertPos = findInsertionIndex(x); // O(log n)

        // Shift everything at/after insertPos one slot to the right.
        for (int i = count; i > insertPos; i--) {
            arr[i] = arr[i - 1];
        }
        arr[insertPos] = x;
        count++;
    }

    /**
     * Removes the first occurrence of x from the array if present.
     * After removal, all elements to the right shift left by one so
     * that non-null elements remain contiguous and sorted.
     *
     * Time Complexity: O(n)
     *   - O(log n) to locate x via binary search
     *   - O(n) worst case to shift the remaining elements left
     *   Overall this is O(n), which dominated by the shifting step.
     *
     * @param x the integer to delete
     * @return true if x was found and removed, false otherwise
     */
    public boolean delete(int x) {
        int index = find(x); // O(log n)
        if (index == -1) {
            return false;
        }
        // Shift all elements after 'index' one position to the left.
        for (int i = index; i < count - 1; i++) {
            arr[i] = arr[i + 1];
        }
        arr[count - 1] = null; // clear the now-duplicate last slot
        count--;
        return true;
    }

    /**
     * Searches for x using binary search, which is possible because
     * arr[] is always kept sorted in ascending order.
     *
     * Time Complexity: O(log n) - binary search halves the search
     * range on each iteration.
     *
     * @param x the integer to search for
     * @return the index of x if found, otherwise -1
     */
    public int find(int x) {
        int low = 0;
        int high = count - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] == x) {
                return mid;
            } else if (arr[mid] < x) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    /**
     * Returns the integer stored at the given index.
     *
     * Time Complexity: O(1) - direct array access.
     *
     * @param index position to read from
     * @return the Integer at arr[index], or null if no element is stored there
     * @throws IndexOutOfBoundsException if index is out of bounds of arr[]
     */
    public Integer get(int index) {
        if (index < 0 || index >= arr.length) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for array of length " + arr.length);
        }
        return arr[index];
    }

    /**
     * Returns the total capacity of the underlying array (not the number
     * of elements currently stored).
     *
     * Time Complexity: O(1) - simply returns arr.length.
     *
     * @return the capacity of arr[]
     */
    public int size() {
        return arr.length;
    }

    /**
     * Returns the number of non-null elements currently stored in the array.
     *
     * Time Complexity: O(1) - count is maintained incrementally by
     * insert() and delete(), so no scan of the array is needed.
     *
     * @return the number of elements currently stored
     */
    public int count() {
        return count;
    }

    /**
     * Resizes the underlying array to newSize, preserving existing
     * elements (and their sorted order) as much as possible. Any
     * elements beyond newSize are discarded.
     *
     * Time Complexity: O(n) - every existing element (up to the smaller
     * of the old size and newSize) must be copied into the new array.
     *
     * @param newSize the new capacity for arr[]
     */
    public void resize(int newSize) {
        Integer[] newArr = new Integer[newSize];
        int elementsToCopy = Math.min(count, newSize);
        for (int i = 0; i < elementsToCopy; i++) {
            newArr[i] = arr[i];
        }
        arr = newArr;
        // If the new size is smaller than count, discarded elements
        // are no longer tracked, so update count accordingly.
        if (newSize < count) {
            count = newSize;
        }
    }

    /**
     * Helper method: finds the index at which x should be inserted to
     * keep arr[] sorted in ascending order, using binary search.
     *
     * Time Complexity: O(log n) - standard binary search.
     *
     * @param x the integer to find an insertion point for
     * @return the index where x should be placed
     */
    private int findInsertionIndex(int x) {
        int low = 0;
        int high = count - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] < x) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return low;
    }
}