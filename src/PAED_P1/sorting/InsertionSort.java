package com.rogermiranda1000.PAED_P1.sorting;

import com.rogermiranda1000.PAED_P1.dades.Club;
import org.jetbrains.annotations.NotNull;

import java.util.function.IntFunction;

public class InsertionSort<T extends Comparable<T>> extends AbstractSort<T>  {
    /**
     * InsertionSort<T> var = new InsertionSort<>( T[]::new );
     * InsertionSort<T> var = new InsertionSort<>( (n) -> new T[n] );
     * @param arrayBuilder Given an integer return T-type array
     */
    public InsertionSort(@NotNull IntFunction<T[]> arrayBuilder) {
        super(arrayBuilder);
    }

    /* OVERRIDE METHODS */
    /**
     * Get a sorted array (ascendant; using InsertionSort)
     */
    @Override
    protected T[] sort(final T[] array) {
        int i, j;
        T tmp;
        for (i = 1; i < array.length; i++) {
            tmp = array[i];
            j = i-1;
            while (j >= 0 && array[j].compareTo(tmp) > 0) {
                array[j+1] = array[j];
                j--;
            }
            array[j+1] = tmp;
        }
        return array;
    }
}
