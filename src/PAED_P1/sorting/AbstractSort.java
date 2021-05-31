package com.rogermiranda1000.PAED_P1.sorting;

import com.rogermiranda1000.PAED_P1.exceptions.NotSortedException;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.function.IntFunction;

public abstract class AbstractSort<T extends Comparable<T>> {
    private final ArrayList<T> list;
    protected final IntFunction<T[]> arrayBuilder;

    public AbstractSort(@NotNull IntFunction<T[]> arrayBuilder) {
        this.arrayBuilder = arrayBuilder;
        this.list = new ArrayList<>();
    }

    /**
     * Add one element to the list
     * @param element Element to add (added by reference; DO NOT MODIFY)
     */
    public void addElement(T element) {
        this.list.add(element);
    }

    /**
     * Add all the array's elements to the list
     * @param elements Array to add
     */
    public void addElements(T []elements) {
        for (T element: elements) this.addElement(element);
    }

    /**
     * Get the list with the current elements
     * @return Array with all the elements
     */
    public T[] getList() {
        return this.list.toArray(this.getEmptyArray());
    }

    /**
     * Use this with 'this.list.toArray'
     * @return new T[0]
     */
    protected T[] getEmptyArray() {
        return this.getTArray(0);
    }

    /**
     * Generates an array of T with given size
     * @param size array size
     * @return the array created
     */
    protected T[] getTArray(int size) {
        return this.arrayBuilder.apply(size);
    }

    /**
     * Check if the array is sorted
     * @return true -> Sorted Array
     */
    private boolean checkSorted(T[] array) {
        // empty list? (by default: ordered)
        if (array.length == 0) return true;

        T current = array[0], next;
        for (int n = 1; n < array.length; n++) {
            next = array[n];
            if (current.compareTo(next) > 0) return false; // not ordered!
            current = next;
        }

        return true;
    }

    /**
     * Get sorted array sorted by the class' implemented algorithm
     * @return Sorted array
     * @throws NotSortedException Array not sorted after calling sort()
     */
    public T[] getSorted() throws NotSortedException {
        T []returnArray = this.sort(this.getList());
        if (!this.checkSorted(returnArray)) throw new NotSortedException();
        return returnArray;
    }

    /**
     * Get sorting time sorted by the class' implemented algorithm
     * @param verbose Should print information?
     * @return Sorting time in nanoseconds
     * @throws NotSortedException Array not sorted after calling sort()
     */
    public long getSortingTime(boolean verbose) throws NotSortedException {
        T []returnArray = this.getList();
        if (verbose) System.out.println("Getting " + this.getClass().getSimpleName() + " array...");
        long init = System.nanoTime();
        returnArray = this.sort(returnArray);
        long last = System.nanoTime();
        if (verbose) System.out.println("Verifying array...");
        if (!this.checkSorted(returnArray)) throw new NotSortedException();
        return last-init;
    }

    /**
     * Get sorting time sorted by the class' implemented algorithm
     * @return Sorting time
     * @throws NotSortedException Array not sorted after calling sort()
     */
    public long getSortingTime() throws NotSortedException {
        return getSortingTime(false);
    }

    /* ABSTRACT METHODS */

    /**
     * Get a sorted array (sorted by the indexFunction, ascendant)
     */
    protected abstract T[] sort(final T[] array);

    /* OVERRIDE METHODS */
    /**
     * Returns a list with all the current elements
     * {e1, e2, e3...}
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append('{');
        try {
            for (T element : this.getSorted()) {
                sb.append(element.toString());
                sb.append(", ");
            }

            // replace last ", " (if exists)
            int lenght = sb.length();
            if (lenght >= 3) sb.setLength(lenght - 2);
        } catch (NotSortedException e) {
            sb.append("SORTING EXCEPTION");
        }

        sb.append('}');

        return sb.toString();
    }
}
