package com.cwagnello;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author cwagnello
 */
public class Solution {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        File file = new File("src/main/resources/input.txt");
        List<String> input = new ArrayList<>();
        try (
                Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                //assume input data is well formed
                input.add(line);
            }
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        char[] characters = input.get(0).toCharArray();

        System.out.println("2015 Day01 part1: " + part1(characters));
        System.out.println("2015 Day01 part2: " + part2(characters));
    }

    private static int part1(char[] characters) {
        int sum = 0;
        for (char c : characters) {
            switch (c) {
                case '(' : { sum++; break; }
                case ')' : { sum--; break; }
                default : //no-op
            }
        }
        return sum;
    }

    private static int part2(char[] characters) {
        int sum = 0;
        int countChars = 0;
        for (char c : characters) {
            countChars++;
            switch (c) {
                case '(' : { sum++; break; }
                case ')' : { sum--; break; }
                default : //no-op
            }
            if (sum < 0) {
                return countChars;
            }
        }
        return -1;
    }

}
