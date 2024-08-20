package com.cwagnello.aoc2016.day09;

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
        System.out.println("2016 day 09 part 1: " + part1(input));
        System.out.println("2016 day 09 part 2: " + part2(input));
    }

    private static long part1(List<String> input) {
        //System.out.printf("String: %s, length: %s%n", decompressed, decompressed.length());
        return decompress(input.get(0));
    }

    private static long part2(List<String> input) {
//        for (String line : input) {
//            System.out.printf("%s -> %d%n", line, recursiveParse(line));
//        }
        return recursiveDecompress(input.get(0));
    }

    private static long decompress(String line) {
        long count = 0;
        int i = 0;
        while (i < line.length()) {
            if (line.charAt(i) == '(') {
                i++; //Skip the open paren
                int start = i;
                while (line.charAt(i) != ')') {
                    i++;
                }

                String[] markerParts = line.substring(start, i).split("x");
                int length = Integer.parseInt(markerParts[0]);
                int repeat = Integer.parseInt(markerParts[1]);
                i++; // Skip the close paren
                i += length;
                count += length * repeat;
            }
            else {
                count++;
                i++;
            }
        }
        return count;
    }

    private static long recursiveDecompress(String line) {
        long count = 0;
        int i = 0;
        while (i < line.length()) {
            if (line.charAt(i) == '(') {
                i++; //Skip the open paren
                int start = i;
                while (line.charAt(i) != ')') {
                    i++;
                }

                String[] markerParts = line.substring(start, i).split("x");
                int length = Integer.parseInt(markerParts[0]);
                long repeat = Long.parseLong(markerParts[1]);
                i++; // Skip the close paren
                count += repeat * recursiveDecompress(line.substring(i, i + length));
                i += length;
            }
            else {
                count++;
                i++;
            }
        }
        return count;

    }
}
