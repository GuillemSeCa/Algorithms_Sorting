package com.rogermiranda1000.PAED_P1.sorting;

import java.util.ArrayList;

import com.rogermiranda1000.PAED_P1.exceptions.BucketNotSortedException;
import com.rogermiranda1000.PAED_P1.exceptions.NotSortedException;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.function.IntFunction;

public class BucketSort<T extends Comparable<T>> extends AbstractSort<T> {
    private final T []buckets;
    private final int bucket_number;
    private final byte selected_sort;

    private static final byte INSERTION_SORT = 0;
    private static final byte QUICK_SORT = 1;
    private static final byte MERGE_SORT = 2;

    /**
     * Quicksort<T> var = new Quicksort<>( T[]::new, buckets );
     * Quicksort<T> var = new Quicksort<>( (n) -> new T[n], buckets );
     *
     * Buckets:
     * If you want: -inf-10, 11-20, 21-inf
     * You must send a 2-length array with values 11 and 21 (first values)
     *
     * @param arrayBuilder Given an integer return T-type array
     * @param buckets bucket first limit to compare
     */
    public BucketSort(@NotNull IntFunction<T[]> arrayBuilder, T[] buckets, byte selected_sort) throws IllegalArgumentException, BucketNotSortedException {
        super(arrayBuilder);
        this.buckets = buckets;
        this.bucket_number = buckets.length + 1;
        this.selected_sort = selected_sort;
        for (int x = 1; x < buckets.length; x++) {
            if (buckets[x].compareTo(buckets[x-1]) < 0) throw new BucketNotSortedException();
        }
        if (selected_sort < 0 || selected_sort > 2) throw new IllegalArgumentException("Selected sort must be between 0 and 2");
    }

    private T[] bucketSort(T[] array) {
        Object []b = new Object[this.bucket_number];
        int i, j;

        for (i = 0; i < b.length; i++) {
            switch (this.selected_sort) {
                case BucketSort.INSERTION_SORT:
                    b[i] = new InsertionSort<T>(this.arrayBuilder);
                    break;
                case BucketSort.QUICK_SORT:
                    b[i] = new QuickSort<T>(this.arrayBuilder);
                    break;
                case BucketSort.MERGE_SORT:
                    b[i] = new MergeSort<T>(this.arrayBuilder);
                    break;
            }
        }

        for (i = 0; i < array.length; i++) {
            j = 1;
            while (j < b.length-1 && array[i].compareTo(this.buckets[j-1]) > 0) j++;
            ((AbstractSort<T>)b[j]).addElement(array[i]);
        }

        ArrayList<T> result = new ArrayList<>();
        for (i = 0; i < b.length; i++) {
            try {
                result.addAll(Arrays.asList(((AbstractSort<T>)b[i]).getSorted()));
            } catch (NotSortedException e) { }
        }
        return result.toArray(this.getEmptyArray());
    }

    /* OVERRIDE METHODS */
    /**
     * Get a sorted array (ascendant; using BucketSort)
     */
    @Override
    protected T[] sort(final T[] array) {
        return bucketSort(array);
    }
}

