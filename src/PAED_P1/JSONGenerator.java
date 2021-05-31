package com.rogermiranda1000.PAED_P1;

import com.rogermiranda1000.PAED_P1.dades.Atletes;
import com.rogermiranda1000.PAED_P1.dades.Club;

import java.io.*;
import java.util.Random;

//JSONGeneratorClass
public class JSONGenerator {
    public static void main(String[] args) {
        System.out.println("Generating...");
        Club[] clubs = generate(3490000);

        System.out.println("Saving...");
        try {
            File f = new File("files/custom" + clubs.length + ".json");
            FileOutputStream fos = new FileOutputStream(f);
            fos.write("[\n".getBytes());
            for (int n = 0; n < clubs.length; n++) {
                fos.write(getJSON(clubs[n]).getBytes());
                if (n != clubs.length - 1) fos.write(",".getBytes());
                fos.write("\n".getBytes());
            }
            fos.write("]\n".getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generate JSON from Club[]
     *
     * @param club Club to get the information
     * @return JSON string
     */
    private static String getJSON(Club club) {
        StringBuilder sb = new StringBuilder();

        sb.append("  {\n");

        sb.append("    \"name\": \"");
        sb.append(club.name);
        sb.append("\",\n");

        sb.append("    \"nation\": \"");
        sb.append(club.nation);
        sb.append("\",\n");

        sb.append("    \"date\": ");
        sb.append(club.date);
        sb.append(",\n");

        sb.append("    \"at\": [\n");
        for (int w = 0; w < club.at.length; w++) {
            Atletes at = club.at[w];
            sb.append("      {\n");

            sb.append("        \"name\": \"");
            sb.append(at.name);
            sb.append("\",\n");

            sb.append("        \"lastname\": \"");
            sb.append(at.lastname);
            sb.append("\",\n");

            sb.append("        \"age\": ");
            sb.append(at.age);
            sb.append(",\n");

            sb.append("        \"nation\": \"");
            sb.append(at.nation);
            sb.append("\",\n");

            sb.append("        \"distance\": ");
            sb.append(at.distance);
            sb.append(",\n");

            sb.append("        \"time\": ");
            sb.append(at.time);
            sb.append(",\n");

            sb.append("        \"type\": \"");
            sb.append(at.type);
            sb.append("\"\n");

            sb.append("      },\n");
        }
        sb.setLength(sb.length() - 2); // remove last coma
        sb.append("\n    ]\n");

        sb.append("  }");

        return sb.toString();
    }

    /**
     * Generate random clubs (and atletes)
     *
     * @param size Number of clubs
     * @return Array
     */
    private static Club[] generate(int size) {
        Club[] r = new Club[size];
        Random random = new Random();

        for (int x = 0; x < size; x++) {
            Club c = new Club();
            c.at = new Atletes[3];
            for (int y = 0; y < c.at.length; y++) {
                c.at[y] = new Atletes(getString(random), getString(random), random.nextInt(120),
                        getString(random), random.nextInt(120), random.nextInt(120));
            }
            r[x] = c;
        }

        return r;
    }

    /**
     * Get a random name
     *
     * @param r Random
     * @return Random name (between 2 and 6 characters)
     */
    private static String getString(Random r) {
        StringBuilder sb = new StringBuilder();
        for (int x = 0; x < r.nextInt(5) + 2; x++) sb.append((char) (r.nextInt('z' - 'a') + 'a'));
        return sb.toString();
    }
}
