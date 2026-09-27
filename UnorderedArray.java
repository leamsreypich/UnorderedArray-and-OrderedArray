/**
 * UnorderedArray
 *
 * Implements a resizable array of Integer objects where elements are
 * NOT kept in any particular order. New elements are always appended
 * after the last non-null element, which makes insertion very fast
 * but makes searching slow (must check every element).
 *
 * Null entries in arr[] represent unused positions.
 */
public class UnorderedArray {

    private Integer[] arr;   // backing storage; null = empty slot
    private int count;       // number of non-null elements currently stored

    /**
     * Constructor.
     * Time Complexity: O(n) - allocating and initializing an array of size n.
     *
     * @param size initial capacity of the array
     */
    public UnorderedArray(int size) {
        arr = new Integer[size];
        count = 0;
    }

    /**
     * Inserts x into the array. Since the array is unordered, the new
     * element is simply placed in the next free slot (arr[count]).
     * If the array is full, it is resized (doubled) before inserting.
     *
     * Time Complexity: O(1) amortized.
     *   - Normal case: O(1), we just place the element at arr[count].
     *   - Occasionally O(n) when a resize is triggered, but since resizing
     *     happens only after the array doubles in size, the amortized
     *     cost per insertion remains O(1).
     *
     * @param x the integer to insert
     */
    public void insert(int x) {
        if (count == arr.length) {
            // Array is full - double the capacity before inserting.
            resize(arr.length == 0 ? 1 : arr.length * 2);
        }
        arr[count] = x;
        count++;
    }

    /**
     * Removes the first occurrence of x from the array, if present.
     * After removal, all elements to the right shift left by one so
     * that non-null elements remain contiguous starting at index 0.
     *
     * Time Complexity: O(n)
     *   - O(n) in the worst case to find x (linear search).
     *   - O(n) in the worst case to shift the remaining elements left.
     *   Overall this is still O(n).
     *
     * @param x the integer to delete
     * @return true if x was found and removed, false otherwise
     */
    public boolean delete(int x) {
        int index = find(x); // O(n)
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
     * Searches for x in the array using a simple linear scan, since the
     * array is not sorted and no faster search strategy is possible.
     *
     * Time Complexity: O(n) - in the worst case every element must be
     * checked before finding x or concluding it isn't present.
     *
     * @param x the integer to search for
     * @return the index of x if found, otherwise -1
     */
    public int find(int x) {
        for (int i = 0; i < count; i++) {
            if (arr[i] != null && arr[i] == x) {
                return i;
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
     * elements (and their relative order) as much as possible. Any
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
}