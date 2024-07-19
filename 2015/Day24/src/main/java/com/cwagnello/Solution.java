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
        List<Long> weights = parse(input);
        System.out.println("2015 Day 24 part 1: " + part1(weights));
        System.out.println("2015 Day 24 part 2: " + part2(weights));
    }

    private static long part1(List<Long> weights) {
        List<List<Long>> combinations = generateCombinations(weights, 3);
        //System.out.println("Number of combos: " + combinations.size());
        //System.out.println("Combos: " + combinations);

        Presents fullList = new Presents();
        fullList.addAll(weights);

        long minProduct = Long.MAX_VALUE;
        combinations.sort((a, b) -> a.size() - b.size());
        int i = 0;
        while (combinations.get(0).size() >= combinations.get(i).size()) {
            minProduct = Math.min(minProduct, combinations.get(i).stream().mapToLong(x -> x).reduce(1, (a, b) -> a * b));
            i++;
        }
        return minProduct;
    }

    private static long part2(List<Long> weights) {
        List<List<Long>> combinations = generateCombinations(weights, 4);
        //System.out.println("Number of combos: " + combinations.size());

        Presents fullList = new Presents();
        fullList.addAll(weights);

        long minProduct = Long.MAX_VALUE;
        combinations.sort((a, b) -> a.size() - b.size());
        int i = 0;
        while (combinations.get(0).size() >= combinations.get(i).size()) {
            minProduct = Math.min(minProduct, combinations.get(i).stream().mapToLong(x -> x).reduce(1, (a, b) -> a * b));
            i++;
        }
        return minProduct;
    }

    private static List<List<Long>> generateCombinations(List<Long> weights, int groups) {
        long target = weights.stream().mapToLong(Long::longValue).sum() / groups;
        List<List<Long>> combinations = new ArrayList<>();
        for (int bitmask = 0; bitmask < Math.pow(2, weights.size()); bitmask++) {
            List<Long> combination = new ArrayList<>();
            long sum = 0;
            for (int i = 0; i < 32; i++) {
                if (((bitmask >> i) & 1) == 1) {
                    combination.add(weights.get(i));
                    sum += weights.get(i);
                    if (sum > target) {
                        i = 32;
                    }
                }
            }
            if (combination.stream().mapToLong(Long::longValue).sum() == target) {
                combinations.add(combination);
            }
        }
        return combinations;
    }

    private static List<Long> parse(List<String> input) {
        return input.stream().map(Long::parseLong).toList();
    }

    private static long sum(List<Long> combination) {
        return combination.stream().mapToLong(Long::longValue).sum();
    }
}