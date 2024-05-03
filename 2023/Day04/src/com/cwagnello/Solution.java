package com.cwagnello;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Solution {
    public static void main (String[] args) {
        File file = new File("src/com/cwagnello/input.txt");
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

        System.out.println("Part1 sum: " + part1(input));
        System.out.println("Part2 sum: " + part2(input));

    }

    private static int part1(List<String> input) {
        List<Set<Integer>> winningNumbers = new ArrayList<>();
        List<Set<Integer>> myNumbers = new ArrayList<>();

        for (String s: input) {
            int cardIndex = s.indexOf(':');
            int numsIndex = s.indexOf('|');
            winningNumbers.add(Arrays.stream(s.substring(cardIndex + 1, numsIndex).split("\\s+")).filter(n -> !n.isBlank()).map(Integer::parseInt).collect(Collectors.toSet()));
            myNumbers.add(Arrays.stream(s.substring(numsIndex + 1).split("\\s+")).filter(n -> !n.isBlank()).map(Integer::parseInt).collect(Collectors.toSet()));
        }

        int sum = 0;
        for (int i = 0; i < winningNumbers.size(); i++) {
            int count = 0;
            for (int number: myNumbers.get(i)) {
                if (winningNumbers.get(i).contains(number)) {
                    count++;
                }
            }
            sum += Math.pow(2, count - 1);
        }
        return sum;
    }

    private static int part2(List<String> input) {
        List<Set<Integer>> winningNumbers = new ArrayList<>();
        List<Set<Integer>> myNumbers = new ArrayList<>();

        for (String s: input) {
            int cardIndex = s.indexOf(':');
            int numsIndex = s.indexOf('|');
            winningNumbers.add(Arrays.stream(s.substring(cardIndex + 1, numsIndex).split("\\s+")).filter(n -> !n.isBlank()).map(Integer::parseInt).collect(Collectors.toSet()));
            myNumbers.add(Arrays.stream(s.substring(numsIndex + 1).split("\\s+")).filter(n -> !n.isBlank()).map(Integer::parseInt).collect(Collectors.toSet()));
        }

        int sum = 0;
        // Card id -> copy count
        Map<Integer, Integer> data = new HashMap<>();
        for (int i = 0; i < winningNumbers.size(); i++) {
            if (!data.containsKey(i)) {
                data.put(i, 1);
            }
            int numberOfCardsToUpdate = 0;
            for (int number: myNumbers.get(i)) {
                if (winningNumbers.get(i).contains(number)) {
                    numberOfCardsToUpdate++;
                }
            }
            int copyCount = 0;
            int j = i + 1;
            while (j < winningNumbers.size() && j <= i + numberOfCardsToUpdate) {
                copyCount = data.getOrDefault(i, 1);
                int current =  data.getOrDefault(j,1);
                data.put(j, current + copyCount);
                j++;
            }
            sum += data.get(i);
        }
        return sum;
    }
}
