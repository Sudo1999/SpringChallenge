package com.coding.games;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedSet;

public class FightingTheVoid {

    /**
     * @param codes La liste des codes binaires dans la table.
     * @return La plus courte séquence ambiguë possible. Si aucune séquence ambiguë n'existe, renvoyez "X".
     */
    public static String crashDecode(List<String> codes) {

        String sequence = "X";
        StringBuilder builder = new StringBuilder();
        for (String code : codes) {
            builder.append(code);
        }
        String oneline = builder.toString();

        for (int i = 0; i < codes.size(); i++) {
            String codeI = codes.get(i);
            for (int j = i + 1; j < codes.size(); j++) {
                String codeJ = codes.get(j);
                if (codeI.equals(codeJ)) {
                    System.out.println("Code en double");
                }
            }
        }


        return sequence;
    }

    /* ==== Solution controle below ==== */
    private static final Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    /**
     * Try a solution
     * @param ambiguousSequence La plus courte séquence ambiguë possible. Si aucune séquence ambiguë n'existe, renvoyez "X".
     */
    public static void trySolution(String ambiguousSequence, String rightOutput) {
        System.out.println("" + gson.toJson(ambiguousSequence));
        System.out.println((gson.toJson(ambiguousSequence)).equals(rightOutput));
    }

    public static void main(String args[]) {
        //try (Scanner in = new Scanner(System.in)) {

        String rightOutput = "01101";
        String inputFile = "src/main/resources/9-FightingTheVoid.txt";      // ["01","101","011"]
        try (Scanner in = new Scanner(new File(inputFile))) {

            trySolution(crashDecode(
                gson.fromJson(in.nextLine(), new TypeToken<List<String>>(){}.getType())
            ), rightOutput);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    /* ==== Solution controle above ==== */
}
