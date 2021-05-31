package com.rogermiranda1000.PAED_P1.sorting;

import com.rogermiranda1000.PAED_P1.dades.Club;
import org.jetbrains.annotations.NotNull;

import java.util.function.IntFunction;

public class QuickSort<T extends Comparable<T>> extends AbstractSort<T> {

    /**
     * Quicksort<T> var = new Quicksort<>( T[]::new );
     * Quicksort<T> var = new Quicksort<>( (n) -> new T[n] );
     * @param arrayBuilder Given an integer return T-type array
     */
    public QuickSort(@NotNull IntFunction<T[]> arrayBuilder) {
        super(arrayBuilder);
    }

    private void printRegion(T[] array, int init, int end, int pivot) {
        StringBuilder sb = new StringBuilder();
        for (int n = init; n < end; n++) {
            if (n == pivot) sb.append("--");
            sb.append(((Club[])array)[n].getMitjanaEdat());
            if (n == pivot) sb.append("--");
            sb.append(" > ");
        }
        System.out.println(sb.toString());
    }

    private void quickSort(final T[] array, int init, int end) {
        if (init >= end) return; // trivial case (only 1 element)

        int pivot = (end+init)/2; // pivot in the middle element; init + (end-init/2)

        /*System.out.println("IN:");
        printRegion(array, init, end, pivot);*/

        // Sort by pivot
        int auxInit = init, auxEnd = end;
        while (auxInit < auxEnd) {
            // next?
            if (array[auxInit].compareTo(array[pivot]) < 0) auxInit++; // current element smaller than pivot
            else if (array[auxEnd].compareTo(array[pivot]) > 0) auxEnd--; // current element larger than pivot
            // same elements
            else if (array[auxInit].compareTo(array[auxEnd]) == 0) {
                if (pivot == auxInit) auxEnd--;
                else auxInit++;
            }
            // swap & next
            else {
                // swap
                T tmp = array[auxInit];
                array[auxInit] = array[auxEnd];
                array[auxEnd] = tmp;
                // pivot changed?
                if (pivot == auxInit) pivot = auxEnd;
                else if (pivot == auxEnd) pivot = auxInit;
            }
        }

        /*System.out.println("OUT:");
        printRegion(array, init, end, pivot);*/

        quickSort(array, init, pivot-1);
        quickSort(array, pivot+1, end);
    }

    /* OVERRIDE METHODS */
    /**
     * Get a sorted array (ascendant; using Quicksort)
     */
    @Override
    protected T[] sort(final T[] array) {
        quickSort(array, 0, array.length-1);
        return array;
    }
}
