package com.coding.games;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PixelRendering {

    public static void main(String args[]) {
        //Scanner in = new Scanner(System.in);

        try {
            String inputFile = "src/main/resources/2-PixelRendering.txt";
            Scanner in = new Scanner(new File(inputFile));

            int n = in.nextInt();
            if (in.hasNextLine()) {
                in.nextLine();
            }
            List<String> image = new ArrayList<>();
            for (int row = 0; row < n; row++) {
                String string = "";
                for (int col = 0; col < n; col++) {
                    string += ".";
                }
                image.add(string);
            }

            while (in.hasNextLine()) {
                /*  A chaque tour vous recevez une instruction représentée par deux valeurs : un type (C pour column ou R pour row),
                *  et une coordonnée (le numéro de la colonne ou de la ligne concernée). Soit tous les pixels de la colonne deviennent noirs ("#"),
                *  soit tous les pixels de la ligne deviennent blancs ("."). Vous devez afficher l'image après chaque commande reçue. */

                String command = in.nextLine();
                String type = command.split(" ")[0];

                if (type.equals("=>")) {
                    // Image finale attendue
                    String expected = command.substring(3);
                    System.out.println(image);
                    System.out.println(image.toString().equals(expected));

                } else {
                    // Evolution de l'image
                    int coord = Integer.valueOf(command.split(" ")[1]);

                    if (type.equals("C")) {
                        for (int row = 0; row < image.size(); row++) {
                            String string = image.get(row);
                            string = string.substring(0, coord) + "#" + string.substring(coord + 1);
                            image.set(row, string);
                        }
                    } else if (type.equals("R")){
                        String string = "";
                        for (int col = 0; col < image.get(0).length(); col++) {
                            string += ".";
                        }
                        image.set(coord, string);
                    }
                }

                // Print the i-th line of the image after the command was executed
                //System.out.println(".#.#.");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
