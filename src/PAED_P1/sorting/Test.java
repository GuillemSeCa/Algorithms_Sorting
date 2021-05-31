package com.rogermiranda1000.PAED_P1.sorting;

import com.rogermiranda1000.PAED_P1.exceptions.NotSortedException;

public class Test<T extends Comparable<T>> {
    private final AbstractSort<T> sortType;
    private final int numberOfTests;

    public Test(AbstractSort<T> sortType, int numberOfTests) {
        this.sortType = sortType;
        this.numberOfTests = numberOfTests;
    }

    public void run() {
        if (this.numberOfTests <= 0) return;

        System.out.print("Sorting by " + this.sortType.getClass().getSimpleName());
        try {
            long media = 0, min = Long.MAX_VALUE, max = 0, current;
            int dot = this.numberOfTests / 3;
            if (dot == 0) dot = 1;
            for (int n = 0; n < this.numberOfTests; n++) {
                current = this.sortType.getSortingTime();
                media += current;
                if (current < min) min = current;
                if (current > max) max = current;
                if (n % dot == 0) System.out.print('.');
            }
            System.out.println();
            System.out.println("\tMin time: " + Test.parseNano(min));
            System.out.println("\tMax time: " + Test.parseNano(max));
            System.out.println("\tAvg time: " + Test.parseNano(Math.round((double)media/this.numberOfTests)));
        } catch (NotSortedException e) {
            e.printStackTrace();
        }
    }

    private static String parseNano(long time) {
        return String.format("%,d", time).replace('.', ',') + " [ns]";
    }
}
