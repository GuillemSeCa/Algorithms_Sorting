package com.rogermiranda1000.PAED_P1.dades;

import org.jetbrains.annotations.NotNull;

public class Atletes implements Comparable<Atletes> {
    private static boolean compareByNation = true;

    public String name;
    public String lastname;
    public int age;
    public String nation;
    public float distance;
    public float time;
    public String type;

    public Atletes() {
        this("", "", 0, "", 0, 0);
        this.type = "";
    }

    public Atletes(String name, String lastname, int age, String nation, float distance, float time) {
        this.nation = nation;
        this.name = name;
        this.lastname = lastname;
        this.age = age;
        this.distance = distance;
        this.time = time;
        this.type = "";
    }

    public Atletes(String nation, float aptitude) {
        this();
        this.nation = nation;
        // aptitude = distance*distance/time => aptitude = arg aptitude
        this.time = aptitude;
        this.distance = aptitude;
    }

    /**
     * Compare 2 atletes
     * @param a2 Atlete to compare
     * @return If the nation/name of the first atlete is before the other's (<); after (>); or same (=)
     */
    public int compareNation(Atletes a2) {
        int r = this.nation.toLowerCase().compareTo(a2.nation.toLowerCase());
        String nom1 = this.name + " " + this.lastname, nom2 = a2.name + " " + a2.lastname;
        if (r == 0) r = nom1.toLowerCase().compareTo(nom2.toLowerCase());
        return r;
    }

    /**
     * Compare 2 atletes
     * @param a2 Atlete to compare
     * @return If the atlete is better than the other (<), worst (>), or equal (=)
     */
    public int compareAptitude(Atletes a2) {
        double v = a2.getAptitude() - this.getAptitude();
        if (v >= 0) return (int)Math.ceil(v);
        else return (int)Math.floor(v);
    }

    /* GETTERS/SETTERS */

    /**
     * Get the atlete's aptitude
     * @return V^2 * d
     */
    public double getAptitude() {
        return Math.pow(this.distance/(this.time == 0 ? 0.0001f : this.time), 2) * this.distance;
    }

    public void setParameters(String nat, float dis, float t){
        this.nation = nat;
        this.distance = dis;
        this.time = t;
    }

    /**
     * Set the comparison criteria
     * @param b If you want to compare the nation (true) or the aptitude (false)
     */
    public static void setCompareByNation(boolean b) {
        Atletes.compareByNation = b;
    }

    /* OVERRIDED FUNCTIONS */
    @Override
    public String toString() {
        return "Atletes{" +
                "name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", age=" + age +
                ", nation='" + nation + '\'' +
                ", distance=" + distance +
                ", time=" + time +
                ", type='" + type + '\'' +
                '}';
    }

    @Override
    public int compareTo(@NotNull Atletes o) {
        return Atletes.compareByNation ? this.compareNation(o) : this.compareAptitude(o);
    }
}