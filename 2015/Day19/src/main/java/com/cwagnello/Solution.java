package com.cwagnello;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

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

        List<Replacement> replacements = new ArrayList<>();
        String formula = parse(input, replacements);
        System.out.println("2015 Day 19 part1: " + part1(formula, replacements));
        System.out.println("2015 Day 19 part2: " + part2(formula, replacements));
    }

    private static int part1(String formula, List<Replacement> replacements) {
        Set<String> combos = new HashSet<>();
        for (Replacement replacement : replacements) {
            int index = 0;
            while (index != -1) {
                index = formula.indexOf(replacement.key(), index);
                if (index == -1) {
                    break;
                }
                String newFormula = formula.substring(0, index) + replacement.value() + formula.substring(index + replacement.key().length());
                combos.add(newFormula);
                index++;
            }
        }
        return combos.size();
    }

    private static int part2(String formula, List<Replacement> replacements) {
        int count = 0;
        String newFormula = formula;
        while (!"e".equals(newFormula)) {
            String previous = newFormula;
            for (Replacement replacement : replacements) {
                int index = 0;
                while (index != -1) {
                    index = newFormula.indexOf(replacement.value(), index);
                    if (index == -1) {
                        break;
                    }
                    count++;
                    newFormula = newFormula.substring(0, index) + replacement.key() + newFormula.substring(index + replacement.value().length());
                    index++;
                }
            }
            if (previous.equals(newFormula) && !"e".equals(newFormula)) {
                System.out.println(String.format("Reached a dead end: %s, shuffling replacement rules and retrying.", newFormula));
                Collections.shuffle(replacements);
                newFormula = formula;
                count = 0;
            }
        }
        return count;
    }

    private static String parse(List<String> input, List<Replacement> replacements) {
        String formula = "";
        for (String line : input) {
            if (line.isEmpty()) {
                //Do nothing
            }
            else if (line.contains("=>")) {
                String[] parts = line.split("\\s=>\\s");
                replacements.add(new Replacement(parts[0], parts[1]));
            }
            else {
                formula = line;
            }
        }
        return formula;
    }

}