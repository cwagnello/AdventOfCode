package com.cwagnello;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {
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

        String santaPassword = input.get(0);
        String newPassword = part1(santaPassword);
        System.out.println("2015 Day11 part1: " + newPassword);
        System.out.println("2015 Day11 part2: " + part1(newPassword));
    }

    private static String part1(String current) {
        char[] characters = current.toCharArray();

        while (true) {
            int index = characters.length - 1;
            incrementChar(characters, index);
            if(twoPairsOfChars(characters) && checkThreeConsecutiveIncreasingChars(characters)) {
                break;
            }

            boolean hasCarry = characters[index] - 'a' == 26;
            while (index >= 0 && hasCarry) {
                if (characters[index] - 'a' == 26) {
                    characters[index] = 'a';
                    index--;
                }
                else {
                    break;
                }
                incrementChar(characters, index);
            }
            if(twoPairsOfChars(characters) && checkThreeConsecutiveIncreasingChars(characters)) {
                break;
            }
        }

        return new String(characters);
    }

    private static void incrementChar(char[] characters, int index) {
        characters[index]++;
        if (characters[index] == 'i' || characters[index] == 'l' || characters[index] == 'o') {
            characters[index]++;
        }
    }

    private static boolean twoPairsOfChars(char[] characters) {
        int count = 0;
        for (int i = 0; i < characters.length - 1; i++) {
            if (characters[i] == characters[i + 1]) {
                count++;
                i++;
            }
        }
        return count >= 2;
    }

    private static boolean checkThreeConsecutiveIncreasingChars(char[] characters) {
        for (int i = 0; i < characters.length - 2; i++) {
            if ((characters[i + 1] - characters[i] == 1) && (characters[i + 2] - characters[i + 1] == 1)) {
                return true;
            }
        }
        return false;
    }
}