# UnorderedArray & OrderedArray

Java implementations of two array-based data structures — an **UnorderedArray** and an **OrderedArray** — supporting insertion, deletion, search, retrieval, size, and resizing.

## Files

| File | Description |
|---|---|
| `UnorderedArray.java` | Array class where elements are stored in no particular order. Fast insertion, linear-time search. |
| `OrderedArray.java` | Array class where elements are always kept in ascending sorted order. Slower insertion, binary-search lookups. |
| `Tester.java` | Local test driver with a `main` method. **Not part of the assignment submission** and used only to verify both classes work correctly. |

## API

Both classes implement the same set of methods:

| Method | Description | Time Complexity |
|---|---|---|
| `UnorderedArray(int size)` / `OrderedArray(int size)` | Constructor; initializes `arr[]` to the given capacity. | O(n) |
| `insert(int x)` | Inserts `x` into the array, auto-resizing (doubling capacity) if full. | O(1) amortized (Unordered) / O(n) (Ordered) |
| `delete(int x)` | Removes the first occurrence of `x`, shifting remaining elements left to stay contiguous. Returns `true`/`false`. | O(n) |
| `find(int x)` | Returns the index of `x`, or `-1` if not found. | O(n) (Unordered) / O(log n) (Ordered) |
| `get(int index)` | Returns the element at `index`. Throws `IndexOutOfBoundsException` if out of range. | O(1) |
| `size()` | Returns the total capacity of `arr[]`. | O(1) |
| `count()` | Returns the number of non-null elements currently stored. | O(1) |
| `resize(int newSize)` | Resizes `arr[]` to `newSize`, preserving existing elements as much as possible. | O(n) |

See the comments above each method in the source files for a full explanation of its time complexity.

## Key Differences

- **UnorderedArray**: new elements are appended after the last element; no ordering is maintained.
- **OrderedArray**: new elements are inserted via binary search to keep `arr[]` sorted in ascending order at all times.

## Running the Tests Locally

```bash
javac UnorderedArray.java OrderedArray.java Tester.java
java Tester
```

`Tester.java` exercises insertion (including auto-resize), deletion, search, bounds-checked retrieval, and manual resizing for both classes, printing `[PASS]`/`[FAIL]` for each check.

> **Note:** `Tester.java` contains a `main` method and must be excluded from the final assignment submission — only `UnorderedArray.java` and `OrderedArray.java` will be submitted.

