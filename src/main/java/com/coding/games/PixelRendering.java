package com.coding.games;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PixelRendering {

    /*  A chaque tour vous recevez une instruction représentée par deux valeurs : un type (C pour column ou R pour row),
     *  et une coordonnée (le numéro de la colonne ou de la ligne concernée). Soit tous les pixels de la colonne deviennent noirs ("#"),
     *  soit tous les pixels de la ligne deviennent blancs ("."). Vous devez afficher l'image après chaque commande reçue. */

    public static void main(String args[]) {
        //Scanner in = new Scanner(System.in);

        try {
            String inputFile = "src/main/resources/2-PixelRendering.txt";
            Scanner in = new Scanner(new File(inputFile));

            int n = in.nextInt();
            String expected = "";
            List<String> image = new ArrayList<>();
            for (int row = 0; row < n; row++) {
                image.add(".".repeat(n));
            }

            if (in.hasNextLine()) {
                in.nextLine();
            }

            // game loop
            while (in.hasNextLine()) {
                String command = in.nextLine();
                String type = command.split(" ")[0];
                int coord = Integer.parseInt(command.split(" ")[1]);

                // Evolution de l'image
                if (type.equals("C")) {
                    for (int row = 0; row < image.size(); row++) {
                        String string = image.get(row);
                        string = string.substring(0, coord) + "#" + string.substring(coord + 1);
                        image.set(row, string);
                    }
                } else if (type.equals("R")) {
                    String string = ".".repeat(n);
                    image.set(coord, string);
                }
                // Image finale attendue
                else if (type.equals("=>")) {
                    expected = command.substring(5);
                }
            }

            for (int i = 0; i < n; i++) {
                System.out.println(image.get(i));
            }
            System.out.println(image.toString().equals(expected));

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
