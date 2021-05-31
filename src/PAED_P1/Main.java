package com.rogermiranda1000.PAED_P1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rogermiranda1000.PAED_P1.dades.Atletes;
import com.rogermiranda1000.PAED_P1.dades.Club;
import com.rogermiranda1000.PAED_P1.exceptions.NotSortedException;
import com.rogermiranda1000.PAED_P1.sorting.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String []args) {
        HashMap<String, Club[]> clubs = new HashMap<>();
        HashMap<String, Atletes[]> atletes = new HashMap<>();
        ArrayList<String> blacklist = new ArrayList<>();
        blacklist.add("custom3490000.json");

        // for each json file...
        System.out.println("Loading files...");
        for (File f : new File("files").listFiles(f -> f.getName().endsWith(".json") && !blacklist.contains(f.getName()))) {
            try {
                String name = f.getName().substring(0, f.getName().length()-5); // get name without '.json'
                Club[] c = loadFile(f);
                ArrayList<Atletes> at = new ArrayList<>();
                for (Club club : c) at.addAll(Arrays.asList(club.at));
                clubs.put(name, c);
                atletes.put(name, at.toArray(new Atletes[0]));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Club[] clubBuckets = new Club[9];
        // -inf-10, 11-20, 21-30, 31-40, 41-50, 51-60, 61-70, 71-80, 81-90, 91-inf
        for (int i = 0; i < 9; i++) clubBuckets[i] = new Club().setMitjanaEdat((i+1)*10 + 1);

        System.out.println("[*] CLUBS [*]");
        for (Map.Entry<String, Club[]> e: clubs.entrySet()) {
            System.out.println("-- " + e.getKey() + " --");
            QuickSort<Club> quickSortClub = new QuickSort<>(Club[]::new);
            quickSortClub.addElements(e.getValue());
            new Test<Club>(quickSortClub, 80).run();

            ParalelQuickSort<Club> paralelQuickSortClub = new ParalelQuickSort<>(Club[]::new, 5);
            paralelQuickSortClub.addElements(e.getValue());
            new Test<Club>(paralelQuickSortClub, 10).run();


            MergeSort<Club> mergeSortClub = new MergeSort<>(Club[]::new);
            mergeSortClub.addElements(e.getValue());
            new Test<Club>(mergeSortClub, 80).run();

            InsertionSort<Club> insertionSortClub = new InsertionSort<>(Club[]::new);
            insertionSortClub.addElements(e.getValue());
            new Test<Club>(insertionSortClub, 2).run();

            for(int x = 0; x < 3; x++) {
                BucketSort<Club> bucketSortClub = new BucketSort<>(Club[]::new, clubBuckets, (byte) x);
                bucketSortClub.addElements(e.getValue());
                new Test<Club>(bucketSortClub, 2).run();
            }
        }

        Atletes[] atletesBucket = new Atletes[9];
        String[] nat = {"C", "F", "H", "K", "M", "P", "R", "T", "X"};
        float[] aptitude = {1000, 800, 600, 500, 400, 300, 200, 100, 50};

        for (int i = 0; i < 9; i++) atletesBucket[i] = new Atletes(nat[i], aptitude[i]);

        for (int x = 0; x < 2; x++) {
            if (x==1) Atletes.setCompareByNation(false);

            System.out.println("[*] ATLETES (" + (x==1 ? "compare by aptitude" : "compare by nation") + ") [*]");
            for (Map.Entry<String, Atletes[]> e : atletes.entrySet()) {
                System.out.println("-- " + e.getKey() + " --");
                QuickSort<Atletes> quickSortAtletes = new QuickSort<>(Atletes[]::new);
                quickSortAtletes.addElements(e.getValue());
                new Test<Atletes>(quickSortAtletes, 20).run();

                ParalelQuickSort<Atletes> paralelQuickSortAtletes = new ParalelQuickSort<>(Atletes[]::new, 5);
                paralelQuickSortAtletes.addElements(e.getValue());
                new Test<Atletes>(paralelQuickSortAtletes, 5).run();


                MergeSort<Atletes> mergeSortAtletes = new MergeSort<>(Atletes[]::new);
                mergeSortAtletes.addElements(e.getValue());
                new Test<Atletes>(mergeSortAtletes, 20).run();

                InsertionSort<Atletes> insertionSortAtletes = new InsertionSort<>(Atletes[]::new);
                insertionSortAtletes.addElements(e.getValue());
                new Test<Atletes>(insertionSortAtletes, 2).run();

                for(int n = 0; n < 3; n++) {
                    BucketSort<Atletes> bucketSortAtletes = new BucketSort<>(Atletes[]::new, atletesBucket, (byte) n);
                    bucketSortAtletes.addElements(e.getValue());
                    new Test<Atletes>(bucketSortAtletes, 2).run();
                }
            }
        }
    }

    /**
     * Load JSON and return the loaded variable
     * @param f File
     * @return Loaded value
     * @throws IOException File doesn't exists
     */
    private static Club []loadFile(File f) throws IOException {
        Club []r;
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line;
        //System.out.println("Loading file...");
        while((line=br.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
        }
        br.close();
        //System.out.println("Creating clubs...");
        ObjectMapper mapper=new ObjectMapper();
        r = mapper.readValue(sb.toString(), Club[].class);
        //System.out.println(Arrays.toString(clubs));
        // calculate clubs' average age
        for (Club club : r) {
            club.calculateAverageAge();
        }
        //System.out.println("Clubs created!");
        return r;
    }

    /**
     * Load JSON and return the loaded variable
     * @param s File name
     * @return Loaded value
     * @throws IOException File doesn't exists
     */
    private static Club []loadFile(String s) throws IOException {
        return loadFile(new File("files/" + s + ".json"));
    }
}
