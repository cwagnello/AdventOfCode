package com.cwagnello;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        File file = new File("src/main/resources/test.txt");
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

        System.out.println("2015 Day08 - part1: " + part1(input));
        System.out.println("2015 Day08 - part2: " + part2(input));
    }

    private static int part1(List<String> input) {
        int count = 0;
        int formattedCount = 0;
        for (String line: input) {
            count += line.length();

            formattedCount -= 2;
            char[] characters = line.toCharArray();
            for (int i = 0; i < characters.length; i++) {
                if (characters[i] == '\\' && characters[i+1] == 'x') {
                    i += 3;
                }
                else if(characters[i] == '\\') {
                    i += 1;
                }
                formattedCount++;
            }
        }
        return count - formattedCount;
    }

    private static int part2(List<String> input) {
        int count = 0;
        int formattedCount = 0;
        for (String line: input) {
            count += line.length();

            formattedCount += 2;
            char[] characters = line.toCharArray();
            for (int i = 0; i < characters.length; i++) {
                if (characters[i] == '\\' || characters[i] == '"') {
                    formattedCount++;
                }
                formattedCount++;
            }
        }
        return formattedCount - count;
    }

}
