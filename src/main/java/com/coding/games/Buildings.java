package com.coding.games;

import java.io.File;
import java.io.FileNotFoundException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Buildings {

    /**
     * @param n Le nombre de bâtiments
     * @param buildingMap La représentation des n bâtiments
     * @return La hauteur de chaque bâtiment
     */
    public static List<Integer> buildingHeights(int n, List<String> buildingMap) {

        List<Integer> heights = new ArrayList<>();
        for (String string : buildingMap) {
            int nbOfFloors = 0;
            for (int i = 0; i < string.length(); i++) {
                if (string.charAt(i) == '*') {
                    nbOfFloors++;
                } else {
                    break;
                }
            }
            heights.add(nbOfFloors);
        }
        return heights;
    }

    /* ==== Solution controle below ==== */

    private static final Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    /**
     * Try a solution
     * @param output La hauteur de chaque bâtiment
     */
    public static void trySolution(List<Integer> output, List<Integer> rightSolution) {
        System.out.println(gson.toJson(output));
        System.out.println(gson.toJson(output).equals(gson.toJson(rightSolution)));
    }

    public static void main(String args[]) {

        //try (Scanner in = new Scanner(System.in)) {
            // public static final java.io.InputStream « System.in » => This stream is already open and ready to supply input data.
            // Typically this stream corresponds to keyboard input or another input source specified by the host environment or user.
            // A propos du Scanner => https://www.data-transitionnumerique.com/scanner-java/
            // Le flux d’entrée standard, connu également sous le nom de System.in de Java, permet à un utilisateur d’écrire du texte,
            // et au programme de lire ce texte via la classe Scanner. L'outil Scanner peut également lui servir à lire un fichier.

        String inputFile = "src/main/resources/buildings.txt";
        try (Scanner in = new Scanner(new File(inputFile))) {

            ArrayList inputs = new ArrayList() {{
                add(gson.fromJson(in.nextLine(), new TypeToken<Integer>(){}.getType()));
                add(gson.fromJson(in.nextLine(), new TypeToken<List<String>>(){}.getType()));
                add(gson.fromJson(in.nextLine(), new TypeToken<List<Integer>>(){}.getType()));
            }};

            int n = (int) inputs.get(0);
            List<String> buildingMap = (List<String>) inputs.get(1);
            List<Integer> rightSolution = (List<Integer>) inputs.get(2);

            trySolution(buildingHeights(n, buildingMap), rightSolution);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /* ==== Solution controle above ==== */
}
