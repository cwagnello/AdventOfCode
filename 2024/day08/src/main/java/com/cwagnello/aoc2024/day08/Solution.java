package com.cwagnello.aoc2024.day08;

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
        char[][] grid = parse(input);
        log.info("2024 day08 part 1: {}", part1(grid));
        log.info("2024 day08 part 2: {}", part2(grid));
    }

    private static long part1(char[][] grid) {
        Map<Character, List<Pair>> map = new HashMap<>();
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (grid[row][col] != '.') {
                    if (!map.containsKey(grid[row][col])) {
                        map.put(grid[row][col], new ArrayList<>());
                    }
                    map.get(grid[row][col]).add(new Pair(row, col));
                }
            }
        }
        Set<Pair> antinodes = new HashSet<>();
        for (Character key: map.keySet()) {
            List<Pair> pairs = map.get(key);
            for (int i = 0; i < pairs.size() - 1; i++) {
                for (int j = i + 1; j < pairs.size(); j++) {
                    Pair p1 = pairs.get(i);
                    Pair p2 = pairs.get(j);
                    int dRow = p2.getRow() - p1.getRow();
                    int dCol = p2.getColumn() - p1.getColumn();

                    Pair antiNode1 = new Pair(p1.getRow() - dRow, p1.getColumn() - dCol);
                    Pair antiNode2 = new Pair(p2.getRow() + dRow, p2.getColumn() + dCol);

                    if (inBounds(grid, antiNode1)) {
                        antinodes.add(antiNode1);
                    }
                    if (inBounds(grid, antiNode2)) {
                        antinodes.add(antiNode2);
                    }
                }
            }
        }
        return antinodes.size();
    }

    private static long part2(char[][] grid) {
        Map<Character, List<Pair>> map = new HashMap<>();
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (grid[row][col] != '.') {
                    if (!map.containsKey(grid[row][col])) {
                        map.put(grid[row][col], new ArrayList<>());
                    }
                    map.get(grid[row][col]).add(new Pair(row, col));
                }
            }
        }
        Set<Pair> antinodes = new HashSet<>();
        for (Character key: map.keySet()) {
            List<Pair> pairs = map.get(key);
            for (int i = 0; i < pairs.size() - 1; i++) {
                for (int j = i + 1; j < pairs.size(); j++) {
                    Pair p1 = pairs.get(i);
                    Pair p2 = pairs.get(j);
                    antinodes.add(p1);
                    antinodes.add(p2);

                    int dRow = p2.getRow() - p1.getRow();
                    int dCol = p2.getColumn() - p1.getColumn();

                    for (int k = 1; k < grid.length; k++) {
                        Pair antiNode1 = new Pair(p1.getRow() - k * dRow, p1.getColumn() - k * dCol);
                        Pair antiNode2 = new Pair(p2.getRow() + k * dRow, p2.getColumn() + k * dCol);
                        if (inBounds(grid, antiNode1)) {
                            antinodes.add(antiNode1);
                        }
                        if (inBounds(grid, antiNode2)) {
                            antinodes.add(antiNode2);
                        }
                    }
                }
            }
        }
        return antinodes.size();
    }

    private static boolean inBounds(char[][] grid, Pair pair) {
        return pair.getRow() >= 0
                && pair.getColumn() >= 0
                && pair.getRow() < grid.length
                && pair.getColumn() < grid[0].length;
    }

    private static char[][] parse(List<String> input) {
        int height = input.size();
        int width = input.getFirst().length();
        char[][] grid = new char[height][width];
        for (int i = 0; i < height; i++) {
            grid[i] = input.get(i).toCharArray();
        }
        return grid;
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
