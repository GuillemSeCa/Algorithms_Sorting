package com.rogermiranda1000.PAED_P1.sorting;

import org.jetbrains.annotations.NotNull;

import java.util.function.IntFunction;

public class MergeSort<T extends Comparable<T>> extends AbstractSort<T>  {

    public MergeSort(@NotNull IntFunction<T[]> arrayBuilder) {
        super(arrayBuilder);
    }

    private void mergeSort(T[] array) {
        if (array.length <= 1) return;

        int mid = array.length/2 - 1;

        T[] arrayLeft = this.getTArray(mid + 1);
        T[] arrayRight = this.getTArray(array.length - arrayLeft.length);
        System.arraycopy(array, 0, arrayLeft, 0, arrayLeft.length);
        System.arraycopy(array, mid + 1, arrayRight, 0, arrayRight.length);

        mergeSort(arrayLeft);
        mergeSort(arrayRight);

        mergeParts(array, arrayLeft, arrayRight);
    }

    private void mergeParts(T[] array, T[] arrayLeft, T[] arrayRight) {
        int i=0, j=0, k=0;

        while (i<arrayLeft.length && j<arrayRight.length) {
            if(arrayLeft[i].compareTo(arrayRight[j]) <= 0) array[k++] = arrayLeft[i++];
            else array[k++] = arrayRight[j++];
        }

        while (i<arrayLeft.length) array[k++] = arrayLeft[i++];

        while (j<arrayRight.length) array[k++] = arrayRight[j++];
    }

    /* OVERRIDE METHODS */
    /**
     * Get a sorted array (ascendant; using Mergesort)
     **/

    @Override
    protected T[] sort(final T[] array) {
        mergeSort(array);
        return array;
    }
}
