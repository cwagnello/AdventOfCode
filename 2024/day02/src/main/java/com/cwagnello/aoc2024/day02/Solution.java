package com.cwagnello.aoc2024.day02;

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
        List<List<Integer>> reportData = new ArrayList<>();
        for (String line: input) {
            List<Integer> current = new ArrayList<>();
            String[] numbers = line.split("\\s+");
            for (String n: numbers) {
                current.add(Integer.parseInt(n));
            }
            reportData.add(current);
        }
        System.out.println("2024 day 2 part 1: " + part1(reportData));
        System.out.println("2024 day 2 part 2: " + part2(reportData));
    }

    private static long part1(List<List<Integer>> reportData) {
        long count = 0;
        for (List<Integer> report: reportData) {
            if ((isStrictlyIncreasing(report) || isStrictlyDecreasing(report)) && isWithinLimits(report)) {
                count++;
            }
        }
        return count;
    }

    private static long part2(List<List<Integer>> reportData) {
        long count = 0;
        for (List<Integer> report: reportData) {
            if ((isStrictlyIncreasing(report) || isStrictlyDecreasing(report)) && isWithinLimits(report)) {
                count++;
            }
            else {
                for (int indexToRemove = 0; indexToRemove < report.size(); indexToRemove++) {
                    boolean isSafe = false;
                    List<Integer> shortenedReport = new ArrayList<>(report);
                    shortenedReport.remove(indexToRemove);
                    if ((isStrictlyIncreasing(shortenedReport) || isStrictlyDecreasing(shortenedReport)) && isWithinLimits(shortenedReport)) {
                        isSafe = true;
                    }
                    if (isSafe) {
                        count++;
                        break;
                    }
                }
            }
        }
        return count;
    }

    private static boolean isStrictlyIncreasing(List<Integer> numbers) {
        for (int i = 1; i < numbers.size(); i++) {
            if (numbers.get(i) - numbers.get(i - 1) <= 0) {
                return false;
            }
        }
        return true;
    }

    private static boolean isStrictlyDecreasing(List<Integer> numbers) {
        for (int i = 1; i < numbers.size(); i++) {
            if (numbers.get(i) - numbers.get(i - 1) >= 0) {
                return false;
            }
        }
        return true;
    }

    private static boolean isWithinLimits(List<Integer> numbers) {
        for (int i = 1; i < numbers.size(); i++) {
            int diff = Math.abs(numbers.get(i) - numbers.get(i - 1));
            if (diff < 1 || diff > 3) {
                return false;
            }
        }
        return true;
    }
}
