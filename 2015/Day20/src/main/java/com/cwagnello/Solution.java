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
        int numPresents = parse(input);
        //System.out.println("2015 Day 20 sample: ");
        //sample(10);
        System.out.println("2015 Day 20 part1: " + part1(numPresents));
        System.out.println("2015 Day 20 part2: " + part2(numPresents));
    }

    private static void sample(int numPresents) {
        for (int i = 1; i <= numPresents; i++) {
            System.out.println(String.format("i: %d, presents: %d", i, factorization(i).stream().mapToInt(Integer::intValue).map(n -> n * 10).sum()));
        }
    }
    private static int part1(int numPresents) {
        int houseNumber = 0;
        int sum = 0;
        while (sum < numPresents) {
            sum = factorization(++houseNumber).stream().mapToInt(Integer::intValue).map(n -> n * 10).sum();
        }
        return houseNumber;
    }

    private static int part2(int numPresents) {
        int houseNumber = 0;
        int sum = 0;
        while (sum < numPresents) {
            sum = 0;
            List<Integer> factors = factorization(++houseNumber);
            for (int n : factors) {
                if (houseNumber / n <= 50) {
                    sum += n * 11;
                }
            }
        }
        return houseNumber;
    }

    private static List<Integer> factorization(int n) {
        List<Integer> factors = new ArrayList<>();
        for (int i = 1; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                factors.add(i);
                if ((n / i) * (n / i) != n) {
                    factors.add(n / i);
                }
            }
        }
        return factors;
    }

    private static int parse(List<String> input) {
        return Integer.parseInt(input.get(0));
    }
}