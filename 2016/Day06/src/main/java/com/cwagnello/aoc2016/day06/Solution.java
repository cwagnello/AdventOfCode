package com.cwagnello.aoc2016.day06;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {

        File file = new File("src/main/resources/input.txt");
        List<String> input = new ArrayList<>();
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                //assume input data is well formed
                input.add(line);
            }
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("2016 day 6 part 1: " + part1(input));
        System.out.println("2016 day 6 part 2: " + part2(input));
    }

    private static String part1(List<String> input) {
        String correctedMessage = "";
        int n = input.get(0).length();
        int[][] frequency = new int[n][26];

        for (String message: input) {
            for (int i = 0; i < n; i++) {
                frequency[i][message.charAt(i) - 'a']++;
            }
        }
        for (int i = 0; i < n; i++) {
            int maxIndex = 0;
            int max = 0;
            for (int j = 0; j < 26; j++) {
                if (frequency[i][j] > max) {
                    maxIndex = j;
                    max = frequency[i][j];
                }
            }
            correctedMessage += (char)(maxIndex + 'a');
        }
        return correctedMessage;
    }


    private static String part2(List<String> input) {
        String correctedMessage = "";
        int n = input.get(0).length();
        int[][] frequency = new int[n][26];

        for (String message: input) {
            for (int i = 0; i < n; i++) {
                frequency[i][message.charAt(i) - 'a']++;
            }
        }
        for (int i = 0; i < n; i++) {
            int minIndex = 0;
            int min = Integer.MAX_VALUE;
            for (int j = 0; j < 26; j++) {
                if (frequency[i][j] < min && frequency[i][j] > 0) {
                    minIndex = j;
                    min = frequency[i][j];
                }
            }
            correctedMessage += (char)(minIndex + 'a');
        }
        return correctedMessage;
    }

}
