package com.cwagnello.aoc2024.day01;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        System.out.println("2024 day01 part1: " + part1(input));
        System.out.println("2024 day01 part2: " + part2(input));
    }

    public static long part1(final List<String> input) {
        final List<Long> left = new ArrayList<>();
        final List<Long> right = new ArrayList<>();
        for (final String line: input) {
            String[] numbers = line.split("\\s+");
            left.add(Long.parseLong(numbers[0]));
            right.add(Long.parseLong(numbers[1]));
        }
        Collections.sort(left);
        Collections.sort(right);
        long diff = 0;
        for (int i = 0; i < left.size(); i++) {
            diff += Math.abs(right.get(i) - left.get(i));
        }
        return diff;
    }

    public static long part2(final List<String> input) {
        final List<Long> left = new ArrayList<>();
        final Map<Long, Long> frequency = new HashMap<>();

        for (final String line: input) {
            String[] numbers = line.split("\\s+");
            left.add(Long.parseLong(numbers[0]));
            long right = Long.parseLong(numbers[1]);
            frequency.put(right, frequency.getOrDefault(right, 0L) + 1);
        }
        long similarity = 0;
        for (long n : left) {
            similarity += n * frequency.getOrDefault(n, 0L);
        }
        return similarity;
    }

}
