package com.cwagnello.aoc2024.day11;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

@Slf4j
public class Solution {
    public static void main(String[] args) {
        List<String> input = readFile("src/main/resources/input.txt");

        log.info("2024 day 11 part 1: {}", part1(parse(input)));
        log.info("2024 day 11 part 2: {}", part2(parse(input)));
    }

    private static long part1(Map<String, Long> stones) {
        return process(stones, 25);
    }

    private static long part2(Map<String, Long> stones) {
        return process(stones, 75);
    }

    private static long process(Map<String, Long> stones, int iterations) {

        for (int i = 0; i < iterations; i++) {
            Map<String, Long> updated = new HashMap<>();
            Set<String> keys = new HashSet<>(stones.keySet());
            for (String stone: keys) {
                long stoneCount = stones.getOrDefault(stone, 0L);

                if (stone.equals("0")) {
                    updated.put("1", stoneCount + updated.getOrDefault("1", 0L));
                } else if (stone.length() % 2 == 0) {
                    int n = stone.length();
                    String key1 = stone.substring(0, n / 2);
                    updated.put(key1, stoneCount + updated.getOrDefault(key1, 0L));
                    //remove leading zeros before adding into the list
                    String key2 = Long.toString(Long.parseLong(stone.substring(n / 2)));
                    updated.put(key2, stoneCount + updated.getOrDefault(key2, 0L));
                } else {
                    String key = Long.toString(Long.parseLong(stone) * 2024);
                    updated.put(key, stoneCount + updated.getOrDefault(key, 0L));
                }
                stones.remove(stone);
            }
            stones = updated;
            //log.info("Iteration: {}, number of stones: {}", i, updated.values().stream().mapToLong(Long::longValue).sum());
        }

        return stones.values().stream().mapToLong(Long::longValue).sum();
    }

    private static Map<String, Long> parse(List<String> input) {
        Map<String, Long> stones = new HashMap<>();
        for (String s: input.getFirst().split("\\s")) {
            stones.put(s, 1L);
        }
        return stones;
    }

    private static List<String> readFile(final String fileName) {
        File file = new File(fileName);
        List<String> input = new ArrayList<>();
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                //assume input data is well formed
                input.add(line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return input;
    }

}
