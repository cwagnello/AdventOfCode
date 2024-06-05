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
        List<Integer> cups = input.stream().map(Integer::parseInt).toList();
        System.out.println("2015 Day 17 part 1: " + part1(cups, 150));
        System.out.println("2015 Day 17 part 2: " + part2(cups, 150));
    }

    private static int part1(List<Integer> cups, int quantity) {
        int count = 0;
        for (int bitmask = 0; bitmask < Math.pow(2, cups.size()); ++bitmask) {
            int sum = 0;
            for (int shift = 0; shift < cups.size(); shift++) {
                if (((bitmask >> shift) & 1) == 1) {
                    sum += cups.get(shift);
                }
            }
            if (sum == quantity) {
                count++;
            }
        }
        return count;
    }

    private static int part2(List<Integer> cups, int quantity) {
        int count = 0;
        int minCups = Integer.MAX_VALUE;
        for (int bitmask = 0; bitmask < Math.pow(2, cups.size()); ++bitmask) {
            int sum = 0;
            int cupsCount = 0;
            for (int shift = 0; shift < cups.size(); shift++) {
                if (((bitmask >> shift) & 1) == 1) {
                    sum += cups.get(shift);
                    cupsCount++;
                }
            }
            if (sum == quantity) {
                if (cupsCount < minCups) {
                    count = 0;
                    minCups = cupsCount;
                }
                if (cupsCount == minCups) {
                    count++;
                }
            }
        }
        return count;
    }
}