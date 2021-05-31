package com.rogermiranda1000.PAED_P1.sorting;

import org.jetbrains.annotations.NotNull;

import java.util.function.IntFunction;

public class ParalelQuickSort<T extends Comparable<T>> extends ParalelSort<T> {
    /**
     * ParalelQuickSort<T> var = new ParalelQuickSort<>( T[]::new, T::compare );
     * ParalelQuickSort<T> var = new ParalelQuickSort<>( (n) -> new T[n], (e1, e2) -> e1.compare(e2) );
     * @param arrayBuilder Given an integer return T-type array
     * @param threads Number of threads (2^threadExponentNumber; with n=5, 32 threads)
     */
    public ParalelQuickSort(@NotNull IntFunction<T[]> arrayBuilder, int threads) {
        super(arrayBuilder, threads);
    }

    private void quickSort(final T[] array, int init, int end, int level) {
        if (init >= end) return; // trivial case (only 1 element)

        int pivot = (end+init)/2; // pivot in the middle element; init + (end-init/2)

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

        if (level < this.threadExponentNumber) {
            final int pivotAux = pivot;
            Runnable runLeft =  () -> quickSort(array, init, pivotAux - 1, level + 1);
            Runnable runRight = () -> quickSort(array, pivotAux + 1, end, level + 1);

            Thread t1 = new Thread(runLeft);
            t1.start();
            Thread t2 = new Thread(runRight);
            t2.start();

            // wait till t1 & t2
            try {
                t1.join();
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else {
            // run in this thread
            quickSort(array, init, pivot - 1, level + 1);
            quickSort(array, pivot + 1, end, level + 1);
        }
    }

    /* OVERRIDE METHODS */
    /**
     * Get a sorted array (sorted by the indexFunction, ascendant; using Quicksort)
     */
    @Override
    protected T[] sort(final T[] array) {
        this.quickSort(array, 0, array.length-1, 0);
        return array;
    }
}
