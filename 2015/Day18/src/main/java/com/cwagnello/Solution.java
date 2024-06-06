package com.cwagnello;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
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
        char[][] grid = parse(input);
        //System.out.println("2015 Day 18 sample: " + part1(grid, 4));
        //System.out.println("2015 Day 18 sample: " + part2(grid, 5));
        System.out.println("2015 Day 18 part 1: " + part1(grid, 100));
        grid = parse(input);
        System.out.println("2015 Day 18 part 2: " + part2(grid, 100));
    }

    private static int part1(char[][] grid, int steps) {
        for (int i = 0; i < steps; i++) {
            char[][] output = new char[grid.length][grid[0].length];
            for (int row = 0; row < grid.length; row++) {
                for (int col = 0; col < grid[0].length; col++) {
                    update(grid, output, row, col);
                }
            }

            for (int j = 0; j < grid.length; j++) {
                grid[j] = Arrays.copyOf(output[j], grid[0].length);
            }
        }

        return countLights(grid);
    }

    private static int part2(char[][] grid, int steps) {
        int n = grid.length;
        int m = grid.length;
        for (int i = 0; i < steps; i++) {
            char[][] output = new char[n][m];
            for (int row = 0; row < n; row++) {
                for (int col = 0; col < m; col++) {
                    grid[0][0] = '#';
                    grid[0][m - 1] = '#';
                    grid[n - 1][0] = '#';
                    grid[n - 1][m - 1] = '#';
                    update(grid, output, row, col);
                }
            }

            for (int j = 0; j < n; j++) {
                grid[j] = Arrays.copyOf(output[j], m);
            }
        }
        grid[0][0] = '#';
        grid[0][m - 1] = '#';
        grid[n - 1][0] = '#';
        grid[n - 1][m - 1] = '#';

        return countLights(grid);
    }

    private static int countLights(char[][] grid) {
        int count = 0;
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (grid[row][col] == '#') {
                    count++;
                }
            }
        }
        return count;
    }

    private static void update(char[][] grid, char[][] output, int row, int col) {
        int neighbors = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i >= 0 && i < grid.length && j >= 0 && j < grid[0].length && grid[i][j] == '#') {
                    if (!(i == row && j == col)) {
                        neighbors++;
                    }
                }
            }
        }
        if (grid[row][col] == '#' && (neighbors == 2 || neighbors == 3)) {
            output[row][col] = '#';
        }
        else if (grid[row][col] == '.' && neighbors == 3) {
            output[row][col] = '#';
        }
        else {
            output[row][col] = '.';
        }
    }

    private static char[][] parse(List<String> input) {
        char[][] grid = new char[input.size()][input.get(0).length()];
        for (int i = 0; i < input.size(); i++) {
            grid[i] = input.get(i).toCharArray();
        }
        return grid;
    }
}