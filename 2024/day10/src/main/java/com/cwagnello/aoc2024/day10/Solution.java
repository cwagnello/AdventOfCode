package com.cwagnello.aoc2024.day10;

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
    private static final int[] DIRECTIONS = {0, 1, 0, -1, 0};

    public static void main(String[] args) {
        List<String> input = readFile("src/main/resources/input.txt");
        int[][] grid = parse(input);
        log.info("2024 day 10 part 1: {}", part1(grid));
        log.info("2024 day 10 part 2: {}", part2(grid));
    }

    private static long part1(int[][] grid) {
        long count = 0;
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (grid[row][col] == 0) {
                    Set<String> reachablePeaks = new HashSet<>();
                    List<Pair> path = new ArrayList<>();
                    path.add(new Pair(row, col));
                    dfs(grid, path, reachablePeaks, row, col);
                    count += reachablePeaks.size();
                }
            }
        }
        return count;
    }

    private static long part2(int[][] grid) {
        long count = 0;
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (grid[row][col] == 0) {
                    Set<String> uniquePaths = new HashSet<>();
                    List<Pair> path = new ArrayList<>();
                    path.add(new Pair(row, col));
                    dfs2(grid, path, uniquePaths, row, col);
                    count += uniquePaths.size();
                }
            }
        }
        return count;
    }

    private static void dfs(int[][] grid, List<Pair> path, Set<String> reachablePeaks, int row, int col) {
        if (path.size() == 10) {
            reachablePeaks.add(path.getLast().toString());
            return;
        }
        for (int i = 1; i < DIRECTIONS.length; i++) {
            int dRow = row + DIRECTIONS[i - 1];
            int dCol = col + DIRECTIONS[i];
            if (inBounds(grid, dRow, dCol)) {
                if (grid[dRow][dCol] == grid[row][col] + 1) {
                    path.add(new Pair(dRow, dCol));
                    dfs(grid, path, reachablePeaks, dRow, dCol);
                    if (!path.isEmpty()) {
                        path.removeLast();
                    }
                }
            }
        }
    }

    private static void dfs2(int[][] grid, List<Pair> path, Set<String> uniquePaths, int row, int col) {
        if (path.size() == 10) {
            StringBuilder sb = new StringBuilder();
            for (Pair pair: path) {
                sb.append(pair.toString());
                sb.append("|");
            }
            sb.deleteCharAt(sb.length() - 1);
            uniquePaths.add(sb.toString());
            return;
        }
        for (int i = 1; i < DIRECTIONS.length; i++) {
            int dRow = row + DIRECTIONS[i - 1];
            int dCol = col + DIRECTIONS[i];
            if (inBounds(grid, dRow, dCol)) {
                if (grid[dRow][dCol] == grid[row][col] + 1) {
                    path.add(new Pair(dRow, dCol));
                    dfs2(grid, path, uniquePaths, dRow, dCol);
                    if (!path.isEmpty()) {
                        path.removeLast();
                    }
                }
            }
        }
    }

    private static boolean inBounds(int[][] grid, int row, int col) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
    }

    private static int[][] parse(List<String> input) {
        int[][] grid = new int[input.size()][input.getFirst().length()];

        for (int row = 0; row < input.size(); row++) {
            char[] digits = input.get(row).toCharArray();
            for (int col = 0; col < digits.length; col++) {
                grid[row][col] = digits[col] - '0';
            }
        }
        return grid;
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
