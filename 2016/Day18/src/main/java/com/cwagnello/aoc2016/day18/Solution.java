package com.cwagnello.aoc2016.day18;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Solution {
    private static final char SAFE = '.';
    private static final char TRAP = '^';

    public static void main(String[] args) {
        List<String> input = readInputFile("src/main/resources/input.txt");
        char[] firstRow = parse(input);
        char[][] grid = new char[2][firstRow.length];
        grid[0] = firstRow;
        //        System.out.println("2016 day 18 sample: " + part1(grid, 10));
        System.out.println("2016 day 18 part 1: " + part1(grid, 40));
        grid[0] = firstRow;
        System.out.println("2016 day 18 part 1: " + part2(grid, 400000));
    }

    private static int part1(char[][] grid, int rows) {
        return process(grid, rows);
    }

    private static int part2(char[][] grid, int rows) {
        return process(grid, rows);
    }

    private static int process(char[][] grid, int rows) {
        int countSafeTiles = 0;
        for (int column = 0; column < grid[0].length; column++) {
            if (grid[0][column] == SAFE) {
                countSafeTiles++;
            }
        }
        for (int row = 1; row < rows; row++) {
            for (int column = 0; column < grid[0].length; column++) {
                grid[1][column] = isTrap(grid, column) ? TRAP : SAFE;
                if (grid[1][column] == SAFE) {
                    countSafeTiles++;
                }
            }
            grid[0] = Arrays.copyOf(grid[1], grid[0].length);
        }
        return countSafeTiles;
    }

    private static boolean isTrap(char[][] grid, int column) {
        String tilesAbove = "";
        if (column == 0) {
            tilesAbove = new String(new char[]{SAFE, grid[0][column], grid[0][column + 1]});
        }
        else if (column == grid[0].length - 1) {
            tilesAbove = new String(new char[]{grid[0][column - 1], grid[0][column], SAFE});
        }
        else {
            tilesAbove = new String(new char[]{grid[0][column - 1], grid[0][column], grid[0][column + 1]});
        }

        return tilesAbove.equals("..^") || tilesAbove.equals(".^^") || tilesAbove.equals("^..") || tilesAbove.equals("^^.");
    }

    private static char[] parse(List<String> input) {
        return input.getFirst().toCharArray();
    }

    private static List<String> readInputFile(final String relativeFilePath) {
        File file = new File(relativeFilePath);
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

