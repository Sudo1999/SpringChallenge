package com.coding.games;

import java.util.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class FightingTheVoid {

    /**
     * @param codes La liste des codes binaires dans la table.
     * @return La plus courte séquence ambiguë possible. Si aucune séquence ambiguë n'existe, renvoyez "X".
     */
    public static String crashDecode(List<String> codes) {
        // Write your code here

        return "string";
    }

    /* ==== Ignore and do not change the code below ==== */
    private static final Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    /**
     * Try a solution
     * @param ambiguousSequence La plus courte séquence ambiguë possible. Si aucune séquence ambiguë n'existe, renvoyez "X".
     */
    public static void trySolution(String ambiguousSequence) {
        System.out.println("" + gson.toJson(ambiguousSequence));
    }

    public static void main(String args[]) {
        try (Scanner in = new Scanner(System.in)) {
            trySolution(crashDecode(
                gson.fromJson(in.nextLine(), new TypeToken<List<String>>(){}.getType())
            ));
        }
    }
    /* ==== Ignore and do not change the code above ==== */
}
