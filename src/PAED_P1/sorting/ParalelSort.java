package com.rogermiranda1000.PAED_P1.sorting;

import org.jetbrains.annotations.NotNull;

import java.util.function.IntFunction;

public abstract class ParalelSort<T extends Comparable<T>> extends AbstractSort<T> {
    protected int threadExponentNumber;

    public ParalelSort(@NotNull IntFunction<T[]> arrayBuilder, int threads) {
        super(arrayBuilder);
        this.threadExponentNumber = threads;
    }

    /* SETTERS/GETTERS */

    /**
     * Set the number of threads to create
     * @param threadExponentNumber Number of threads (2^threadExponentNumber; with n=5, 32 threads)
     * @return Current object
     */
    public ParalelSort setThreadExponentNumber(int threadExponentNumber) {
        this.threadExponentNumber = threadExponentNumber;
        return this;
    }

    /* ABSTRACT METHODS */

    /**
     * Get a sorted array (sorted by the indexFunction, ascendant)
     */
    protected abstract T[] sort(final T[] array);
}
