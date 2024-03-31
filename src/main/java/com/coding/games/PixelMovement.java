package com.coding.games;

import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
public class PixelMovement {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        if (in.hasNextLine()) {
            in.nextLine();
        }

        // game loop
        while (true) {
            String command = in.nextLine();
            for (int i = 0; i < n; i++) {

                // Write an action using System.out.println()
                // To debug: System.err.println("Debug messages...");

                // Print the i-th line of the image after the command was executed
                System.out.println(".#.#.");
            }
        }
    }
}
