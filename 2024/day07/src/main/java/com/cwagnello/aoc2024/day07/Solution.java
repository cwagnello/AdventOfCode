package com.cwagnello.aoc2024.day07;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Slf4j
public class Solution {
    public static void main(String[] args) {
        List<String> input = readFile("src/main/resources/input.txt");
        log.info("2024 day07 part1: {}", part1(input));
        log.info("2024 day07 part2: {}", part2(input));
    }

    private static long part1(List<String> input) {
        long sum = 0;
        for (String equation: input) {
            String[] parts = equation.split(":\\s+");
            EquationData equationData = new EquationData(Long.parseLong(parts[0]), parts[1]);
            if (equationData.findValidEquation()) {
                sum += equationData.getResult();
            }
        }
        return sum;
    }

    private static long part2(List<String> input) {
        long sum = 0;
        Map<Integer, List<String>> equationPermutations = new HashMap<>();
        for (String equation: input) {
            String[] parts = equation.split(":\\s+");
            EquationData equationData = new EquationData(Long.parseLong(parts[0]), parts[1]);
            int n = equationData.getOperands().size() - 1;
            if (!equationPermutations.containsKey(n)) {
                dfs(n, "", equationPermutations);
            }
            if (equationData.isValid(equationPermutations.get(n))) {
                sum += equationData.getResult();
            }
        }
        return sum;
    }

    private static void dfs(int n, String equation, Map<Integer, List<String>> permutations) {
        if (equation.length() == n) {
            if (!permutations.containsKey(n)) {
                permutations.put(n, new ArrayList<>());
            }
            permutations.get(n).add(equation);
            return;
        }
        for (int i = 0; i < 3; i++) {
            dfs(n, equation + i, permutations);
        }
    }

    private static List<String> readFile(final String fileName) {
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
