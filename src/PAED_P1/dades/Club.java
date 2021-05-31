package com.rogermiranda1000.PAED_P1.dades;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class Club implements Comparable<Club> {
    // JSON variables
    public String name;
    public String nation;
    public int date;
    public Atletes []at;

    private float mitjanaEdat;

    public Club() {
        this.name = "";
        this.nation = "";
        this.date = 0;
        this.at = null;
    }

    /**
     * Calculate the atletes' average age
     */
    public void calculateAverageAge() {
        if (this.at == null || this.at.length == 0) return; // nothing to calculate here

        float sumaEdat = 0.f;
        for (Atletes atlet : this.at) sumaEdat += atlet.age;
        this.mitjanaEdat = sumaEdat/this.at.length;
    }

    /* GETTERS/SETTERS */
    /**
     * Get the average age
     * @return Club's average age
     */
    public float getMitjanaEdat() {
        return this.mitjanaEdat;
    }

    /**
     * Set the average age
     * @param mitjanaEdat Club's average age
     * @return Current club
     */
    public Club setMitjanaEdat(float mitjanaEdat) {
        this.mitjanaEdat = mitjanaEdat;
        return this;
    }

    /* OVERRIDED FUNCTIONS */
    @Override
    public String toString() {
        return "Club{" +
                "name='" + name + '\'' +
                ", mitjanaEdat=" + this.mitjanaEdat +
                ", nation='" + nation + '\'' +
                ", date=" + date +
                ", at[" + Arrays.toString(at) +
                '}';
    }

    @Override
    public int compareTo(@NotNull Club o) {
        /* compareTo should return < 0 if this(keyword) is supposed to be less than au,
         *                         > 0 if this is supposed to be greater than object au,
         *                         = 0 if they are supposed to be equal. */
        float v = this.mitjanaEdat - o.mitjanaEdat;
        if (v >= 0) return (int)Math.ceil(v);
        else return (int)Math.floor(v);
    }
}