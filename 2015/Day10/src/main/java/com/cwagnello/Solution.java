package com.cwagnello;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
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
        String digits = input.get(0);
        System.out.println("2015 Day10 - part1: " + part1(digits));
        System.out.println("2015 Day10 - part2: " + part2(digits));
    }

    private static int part1(String input) {
        return runLengthEncoding(input, 40);
    }

    private static int part2(String input) {
        return runLengthEncoding(input, 50);
    }

    private static int runLengthEncoding(String input, int iterations) {
        StringBuilder sb;
        for (int i = 0; i < iterations; i++) {
            sb = new StringBuilder();
            for (int j = 0; j < input.length(); j++) {
                char current = input.charAt(j);
                int count = 0;
                while (j < input.length() && current == input.charAt(j)) {
                    count++;
                    j++;
                }
                j--;
                sb.append(count);
                sb.append(current);
            }
            input = sb.toString();
        }
        return input.length();
    }
}
