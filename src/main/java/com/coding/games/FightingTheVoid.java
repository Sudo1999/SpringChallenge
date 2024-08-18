package com.coding.games;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class FightingTheVoid {

    //// Mise en oeuvre de l'ordre lexicographique (https://h-deb.clg.qc.ca/Sujets/TrucsScouts/ordre_lexicographique.html) :
    //// La fonction ne sert pas, elle est remplacée par l'usage de Collections.sort(sequences);
    public boolean precedeLexicographiquement(String c0, String c1) {
        int c0Length = c0.length();
        int c1Length = c1.length();
        int ppetit = Math.min(c0Length, c1Length);

        for(int i = 0; i < ppetit; ++i) {       // 011 précède 10
            if (c0.charAt(i) < c1.charAt(i))
                return true;
            if (c0.charAt(i) > c1.charAt(i))
                return false;
        }
        return c0Length < c1Length;     // 1 précède 10
    }   // Fin de precedeLexicographiquement()

    //// Sélection de la séquence la plus courte entre deux, ou sinon de la première dans l'ordre lexicographique
    public static String selectSequence(String sequence, String newSequence) {
        if (sequence.equals("X")) {
            //System.out.println("Choix unique");
            return newSequence;
        }
        if (newSequence.equals("X")) {
            //System.out.println("Choix unique inversé");
            return sequence;
        }

        // Retourner la plus courte, et s'il y en a plusieurs, la première dans l'ordre lexicographique.
        List<String> sequences = new ArrayList<>();
        sequences.add(sequence);
        sequences.add(newSequence);

        Comparator<String> compareByLength = new Comparator<String>() {
            @Override
            public int compare(String string1, String string2) {
                //return (string1.length() < string2.length()) ? -1 :
                //((string1.length() > string2.length()) ? 1 : 0);
                return Integer.compare(string1.length(), string2.length());
            }
        };

        Collections.sort(sequences);
        sequences.sort(compareByLength);
        //System.out.print("Sequences => " + sequences);
        //System.out.println(" => Choix Sequence get(0) => " + sequences.get(0));

        return sequences.get(0);
    }   // Fin de selectSequence()

    //// Fonctions récursives

    //// La fonction ne sert pas, elle servait à l'ancienne solution
    public static String checkCurrentShorter(List<String> codes, int currentIndex, String currentInput, String alternInput, String sequence) {
        //System.out.println("currentShorter");
        //System.out.println("Sequence in checkCurrentShorter = " + sequence);
        int currentOldLength = currentInput.length();
        int alternLength = alternInput.length();

        if (currentIndex < codes.size()) {
            String nextCode = codes.get(currentIndex++);
            String currentNewInput = currentInput + nextCode;
            int currentNewLength = currentNewInput.length();

            int gap = (currentNewLength > alternLength ? alternLength - currentOldLength : nextCode.length());
            for (int stringindex = 0; stringindex < gap; stringindex++) {
                if (alternInput.charAt(stringindex + currentOldLength) != nextCode.charAt(stringindex)) {
                    //System.out.println("Le code est différent");
                    return sequence;
                }
            }

            if (currentNewLength == alternLength) {
                sequence = selectSequence(sequence, currentNewInput);
            }
            if (currentNewLength < alternLength) {
                sequence = checkCurrentShorter(codes, currentIndex, currentNewInput, alternInput, sequence);
            }
            if (currentNewLength > alternLength) {
                sequence = checkAlternShorter(codes, currentIndex, currentNewInput, alternInput, sequence);
            }
        }
        //System.out.println("Sequence = " + sequence);
        return sequence;
    }

    //// La fonction ne sert pas, elle servait à l'ancienne solution
    public static String checkAlternShorter(List<String> codes, int currentIndex, String currentInput, String alternInput, String sequence) {
        //System.out.println("currentLonger");
        //System.out.println("Sequence in checkAlternShorter = " + sequence);
        int currentLength = currentInput.length();
        int alternOldLength = alternInput.length();

        String currentEnd = currentInput.substring(alternOldLength, currentLength);

        List<String> compliantCodes = new ArrayList<>();
        for (String code : codes) {
            if (code.charAt(0) == currentEnd.charAt(0)) {
                if (code.length() == 1 || currentEnd.length() == 1) {
                    compliantCodes.add(code);
                } else {
                    int smaller = Math.min(code.length(), currentEnd.length());
                    for (int stringindex = 0; stringindex < smaller; stringindex++) {
                        if (code.charAt(stringindex) != currentEnd.charAt(stringindex)) {
                            //System.out.println("Le code " + code + " diffère de currentEnd = " + currentEnd);
                            smaller = 0;
                            break;
                        }
                    }
                    if (smaller > 0) {
                        //System.out.println("Le code " + code + " est compatible avec " + currentEnd);
                        compliantCodes.add(code);
                    }
                }
            }
        }

        for (String compliantCode : compliantCodes) {
            String alternNewInput = alternInput + compliantCode;
            int alternNewLength = alternNewInput.length();

            if (alternNewLength == currentLength) {
                sequence = selectSequence(sequence, alternNewInput);
            }
            if (alternNewLength < currentLength) {
                sequence = checkAlternShorter(codes, currentIndex, currentInput, alternNewInput, sequence);
            }
            if (alternNewLength > currentLength) {
                sequence = checkCurrentShorter(codes, currentIndex, currentInput, alternNewInput, sequence);
            }
        }
        //System.out.println("Sequence = " + sequence);
        return sequence;
    }

    //// Fonction récursive de la réinterprétation
    public static String checkAlternative(String pair, String sequence, List<String> codes, int turn) {
        String checked = pair.split(" ")[0];
        String altern = pair.split(" ")[1];

        if (checked.length() == altern.length()) {
            return selectSequence(sequence, altern);
        }

        // Eviter les boucles infinies
        if (turn++ > codes.size()) {
            return sequence;
        }

        // Définir que checked est le plus court et altern le plus long
        if (checked.length() > altern.length()) {
            String transit = altern;
            altern = checked;
            checked = transit;
        }

        String digitsToCheck = "";
        for (int i = checked.length(); i < altern.length(); i++) {
            digitsToCheck = digitsToCheck + String.valueOf(altern.charAt(i));
        }
        //System.out.println(digitsToCheck);

        for (String extraCode : codes) {
            boolean isOk = true;
            int maxLength = extraCode.length() < digitsToCheck.length() ? extraCode.length() : digitsToCheck.length();

            for (int i = 0; i < maxLength; i++) {
                if (extraCode.charAt(i) != digitsToCheck.charAt(i)) {
                    isOk = false;
                    break;
                }
            }
            if (isOk) {
                String potentialPair = checked + extraCode + " " + altern;
                //System.out.println("potentialPair = " + potentialPair);
                sequence = checkAlternative(potentialPair, sequence, codes, turn);
            }
        }
        return sequence;
    }

    ////
    ////  ================  Fonction crashDecode  ================  ////
    ////

    /**
     * @param listOfCodes La liste des codes binaires dans la table.
     * @return La plus courte séquence ambiguë possible, et s'il y en a plusieurs, la première dans l'ordre lexicographique.
     * Si aucune séquence ambiguë n'existe, renvoyez "X". Tous les codes fournis sont distincts.
     */
    public static String crashDecode(List<String> listOfCodes) {

        String sequence = "X";
        final List<String> codes = List.copyOf(listOfCodes);
        //System.out.println("Original list of codes => " + codes);

        ////
        ////  ========  Réinterprétation de la question posée  ========  ////
        ////

        for (String code : codes) {
            ArrayList<String> potentialPairs = new ArrayList<>();

            // Extraction de tous les codes qui commencent comme le code testé
            for (String altern : codes) {
                // Si le code alternatif est plus court, il est ignoré pour éviter qu'il ne soit testé deux fois
                if (altern.length() <= code.length()) continue;

                boolean isOk = true;
                for (int i = 0; i < code.length(); i++) {
                    if (code.charAt(i) != altern.charAt(i)) {
                        isOk = false;
                        break;
                    }
                }
                if (isOk) {
                    String potentialPair = code + " " + altern;
                    potentialPairs.add(potentialPair);
                }
            }
            //System.out.println(potentialPairs);

            // Pour chaque paire potentielle, recherche d'une adéquation complète
            for (String pair : potentialPairs) {
                sequence = checkAlternative(pair, sequence, codes, 0);
            }
        }
        //return sequence;

        ////
        ////  ==============  Ancien code  ==============  ////
        ////  ========  Erreur d'interprétation  ========  ////
        ////

        String oldSequence = "X";
        StringBuilder builder = new StringBuilder();
        for (String code : codes) {
            builder.append(code);
        }
        String oneline = builder.toString();
        //System.out.println("One line => " + oneline);

        // Alerte en cas de doublon (il ne doit pas y en avoir)
        for (int indexA = 0; indexA < codes.size(); indexA++) {
            String codeA = codes.get(indexA);
            for (int indexB = indexA + 1; indexB < codes.size(); indexB++) {
                String codeB = codes.get(indexB);
                if (codeA.equals(codeB)) {
                    System.out.println("Code en double");
                }
            }
        }

        // Recherche d'une séquence ambigüe

        int codesIndex = 0;
        for (int i = 0; i < oneline.length(); i++) {
            String current = codes.get(codesIndex++);       // A la fin de chaque tour, la boucle avance au prochain code de la liste
            int currentLength = current.length();
            int nexti = i + currentLength;      // A la fin de chaque tour, l'index avance au premier caractère du nouveau code

            // Extraction d'une première liste de codes dont les chiffres sont semblables à ceux du code en cours
            // (ou le premier chiffre seulement si l'un des deux codes ne contient qu'un caractère)

            List<String> extractedList = new ArrayList<>();
            for (String code : codes) {
                if (code.charAt(0) == current.charAt(0)) {
                    if (code.length() == 1 || currentLength == 1) {
                        extractedList.add(code);
                    } else {
                        int smaller = Math.min(code.length(), current.length());
                        for (int stringindex = 0; stringindex < smaller; stringindex++) {
                            if (code.charAt(stringindex) != current.charAt(stringindex)) {
                                smaller = 0;
                                break;
                            }
                        }
                        if (smaller > 0) {
                            extractedList.add(code);
                        }
                    }
                }
            }

            // Recherche, à partir de cette première liste, d'une concordance dans la suite du code

            if (extractedList.size() > 1) {
                extractedList.remove(current);
                //System.out.println("ExtractedList pour le code " + current + " => " + extractedList);

                for (String altern : extractedList) {

                    int alternLength = altern.length();
                    boolean currentShorter = currentLength < alternLength;
                    boolean currentLonger = currentLength > alternLength;
                    if (!currentShorter && !currentLonger) {
                        continue;   // Les deux ne peuvent pas avoir la même longueur et les mêmes caractères (il n'y a pas de doublon)
                    }

                    if (currentShorter) {
                        // Premier cas : le code courant est plus court que le code alternatif
                        // S'il n'est pas le dernier de la ligne, on cherche celui qui suit
                        oldSequence = checkCurrentShorter(codes, codesIndex, current, altern, oldSequence);

                    } else {    // => if (currentLonger)
                        // Deuxième cas : le code courant est plus long que le code alternatif
                        oldSequence = checkAlternShorter(codes, codesIndex, current, altern, oldSequence);
                    }
                }
            }
            i = nexti - 1;
            //System.out.println("En fin de boucle i = " + i);
        }

        return sequence;
    }

    /* ==== Solution controle below ==== */
    private static final Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    /**
     * Try a solution
     * @param ambiguousSequence La plus courte séquence ambiguë possible. Si aucune séquence ambiguë n'existe, renvoyez "X".
     */
    public static void trySolution(String ambiguousSequence, String outputFile) {
        System.out.println(gson.toJson("Mon résultat => " + ambiguousSequence));
        System.out.println(gson.toJson("Le bon résultat => " + outputFile));
        System.out.println((ambiguousSequence).equals(outputFile));

        // Test de la fonction selectSequence() :
        /*String A = "011";
        String B = "01";
        String C = "001";
        System.out.println("selectSequence(A, B) = " + selectSequence(A, B));
        System.out.println("selectSequence(B, A) = " + selectSequence(B, A));
        System.out.println("selectSequence(A, C) = " + selectSequence(A, C));
        System.out.println("selectSequence(C, A) = " + selectSequence(C, A));
        System.out.println("selectSequence(B, C) = " + selectSequence(B, C));
        System.out.println("selectSequence(C, B) = " + selectSequence(C, B));*/
    }

    public static void main(String args[]) {
        //try (Scanner in = new Scanner(System.in)) {

        for (int inputNum = 1; inputNum < 12; inputNum++) {

            //String inputFile = "src/main/resources/FightingTheVoid/Test_1_input.txt" => ["01","101","011"]
            //String outputFile = "src/main/resources/FightingTheVoid/Test_1_output.txt" => "01101"

            String inputFile = "src/main/resources/FightingTheVoid/Test_" + inputNum + "_input.txt";
            String outputFile = "src/main/resources/FightingTheVoid/Test_" + inputNum + "_output.txt";
            System.out.println();
            System.out.println("InputNum = " + inputNum);

            try (Scanner in = new Scanner(new File(inputFile));
                 Scanner out = new Scanner(new File(outputFile));
            ) {
                trySolution(
                        crashDecode(gson.fromJson(in.nextLine(), new TypeToken<List<String>>() {
                        }.getType())),
                        gson.fromJson(out.nextLine(), new TypeToken<String>() {
                        }.getType())
                );

                /* Test 0K pour onze des douze propositions
                    (le Test_6 n'avait pas répondu au bout d'une demi-heure
                    => Je le referai quand j'aurai le temps de le laisser tourner). * */

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /* ==== Solution controle above ==== */
}
