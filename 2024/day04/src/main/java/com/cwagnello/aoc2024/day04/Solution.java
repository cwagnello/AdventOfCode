package com.cwagnello.aoc2024.day04;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Slf4j
class Solution {

    private static final int[][] DIRECTIONS = {{-1, 0}, {-1, -1}, {0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}};
    private static final char[] XMAS = {'X', 'M', 'A', 'S'};

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

        char[][] grid = parse(input);
        log.info("2024 day 4 part 1: {}", part1(grid));
        log.info("2024 day 4 part 2: {}", part2(grid));
    }

    private static long part1(char[][] grid) {
        long count = 0;
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (grid[row][col] == 'X') {
                    for (int[] dir: DIRECTIONS) {
                        if (searchForXmas(grid, dir, row, col)) {
                           count++;
                        }
                    }
                }
            }
        }
        return count;
    }

    private static long part2(char[][] grid) {
        long count = 0;
        for (int row = 1; row < grid.length - 1; row++) {
            for (int col = 1; col < grid[0].length - 1; col++) {
                if (grid[row][col] == 'A') {
                    if ((grid[row - 1][col - 1] == 'M' && grid[row + 1][col + 1] == 'S'
                     || grid[row - 1][col - 1] == 'S' && grid[row + 1][col + 1] == 'M')
                     && (grid[row - 1][col + 1] == 'M' && grid[row + 1][col - 1] == 'S'
                     || grid[row - 1][col + 1] == 'S' && grid[row + 1][col - 1] == 'M')) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private static boolean searchForXmas(char[][] grid, int[] dir, int row, int col) {
        for (int index = 1; index < XMAS.length; index++) {
            int dRow = row + dir[0] * index;
            int dCol = col + dir[1] * index;
            if (!inBounds(grid, dRow, dCol)) {
                return false;
            }
            if (grid[dRow][dCol] != XMAS[index]) {
                return false;
            }
        }
        return true;
    }

    private static boolean inBounds(char[][] grid, int row, int col) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
    }

    private static char[][] parse(List<String> input) {
        char[][] grid = new char[input.size()][input.get(0).length()];
        for (int i = 0; i < input.size(); i++) {
            grid[i] = input.get(i).toCharArray();
        }
        return grid;
    }
}