package com.coding.games;

import java.util.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class PixelCircles {

    /**
     * @param nRows Le nombre de lignes de l'image
     * @param nCols Le nombre de colonnes de l'image
     * @param image Les pixels de l'image, donnés ligne par ligne de haut en bas.
     * @return Les caractéristiques du plus grand cercle [ligneCentre, colonneCentre, rayon].
     */
    public static List<Integer> findLargestCircle(int nRows, int nCols, List<String> image) {
        // Write your code here

        return Arrays.asList(20, 153, 116);
    }

    /* ==== Ignore and do not change the code below ==== */
    private static final Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    /**
     * Try a solution
     * @param largestCircle Les caractéristiques du plus grand cercle [ligneCentre, colonneCentre, rayon].
     */
    public static void trySolution(List<Integer> largestCircle) {
        System.out.println("" + gson.toJson(largestCircle));
    }

    public static void main(String args[]) {
        try (Scanner in = new Scanner(System.in)) {
            trySolution(findLargestCircle(
                gson.fromJson(in.nextLine(), new TypeToken<Integer>(){}.getType()),
                gson.fromJson(in.nextLine(), new TypeToken<Integer>(){}.getType()),
                gson.fromJson(in.nextLine(), new TypeToken<List<String>>(){}.getType())
            ));
        }
    }
    /* ==== Ignore and do not change the code above ==== */
}
