package com.cwagnello.aoc2024.day05;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

@Slf4j
public class Solution {
    public static void main(String[] args) {
        String rulesFile = "src/main/resources/input1.txt";
        String updatesFile = "src/main/resources/input2.txt";
        Set<String> rules = new HashSet<>(readFile(rulesFile));
        List<String> updates = readFile(updatesFile);

        log.info("2024 day 5 part 1: {}", part1(rules, updates));
        log.info("2024 day 5 part 2: {}", part2(rules, updates));
    }

    private static long part1(Set<String> rules, List<String> updates) {
        long sum = 0;
        for (String update: updates) {
            String[] pageNumbers = update.split(",");
            if (isValid(rules, pageNumbers)) {
                sum += Long.parseLong(pageNumbers[pageNumbers.length / 2]);
            }
        }

        return sum;
    }

    private static long part2(Set<String> rules, List<String> updates) {
        long sum = 0;
        for (String update: updates) {
            String[] pageNumbers = update.split(",");
            if (!isValid(rules, pageNumbers)) {
                makeValid(rules, pageNumbers);
                sum += Long.parseLong(pageNumbers[pageNumbers.length / 2]);
            }
        }

        return sum;
    }

    private static void makeValid(Set<String> rules, String[] pageNumbers) {
        while (!isValid(rules, pageNumbers)) {
            for (int i = 0; i < pageNumbers.length; i++) {
                for (int j = i + 1; j < pageNumbers.length; j++) {
                    String testRule = pageNumbers[i] + "|" + pageNumbers[j];
                    if (!rules.contains(testRule)) {
                        String temp = pageNumbers[i];
                        pageNumbers[i] = pageNumbers[j];
                        pageNumbers[j] = temp;
                        i = pageNumbers.length;
                        j = pageNumbers.length;
                    }
                }
            }
        }
    }

    private static boolean isValid(Set<String> rules, String[] pageNumbers) {
        for (int i = 0; i < pageNumbers.length; i++) {
            for (int j = i + 1; j < pageNumbers.length; j++) {
                String testRule = pageNumbers[i] + "|" + pageNumbers[j];
                if (!rules.contains(testRule)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static List<String> readFile(String fileName) {
        File file = new File(fileName);
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
        return input;
    }
}