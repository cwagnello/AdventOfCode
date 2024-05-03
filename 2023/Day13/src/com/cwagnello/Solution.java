package com.cwagnello;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Solution {

    public static void main(String[] args) {
        String input = readInput();
        String[] grids = input.split("\\n\\n");

        System.out.println("Day13 - Part1: " + part1(grids));
        System.out.println("Day13 - Part2: " + part2(grids));
    }

    private static long part1(String[] grids) {
        long sum = 0;
        for (String g : grids) {
            String[] lines = g.split("\\n");
            char[][] grid = new char[lines.length][lines[0].length()];
            for (int i = 0; i < lines.length; i++) {
                grid[i] = lines[i].toCharArray();
            }
            int row = findRowPalindrome(grid);
            int column = findColumnPalindrome(grid);
            if (row != -1) {
                System.out.println("P1 reflection point: " + row);
                sum += 100L * row;
            }
            if (column != -1) {
                sum += column;
            }
        }
        return sum;
    }

    private static long part2(String[] grids) {
        long sum = 0;
        for (String g : grids) {
            String[] lines = g.split("\\n");
            char[][] grid = new char[lines.length][lines[0].length()];
            for (int i = 0; i < lines.length; i++) {
                grid[i] = lines[i].toCharArray();
            }
            int row = findRowPalindrome2(grid);
            int column = findColumnPalindrome(grid);
            if (row != -1) {
                System.out.println("P2 reflection point: " + row);
                sum += 100L * row;
            }
            if (column != -1) {
                sum += column;
            }
        }
        return sum;

    }

    private static int findRowPalindrome2(char[][] grid) {
        int reflection = -1;
        boolean isPalindrome = false;

        for (int i = 0; i < grid.length; i++) {
            int row1 = i;
            int row2 = i + 1;
            while (row1 >= 0 && row2 < grid.length) {
                if (isRowPalindromish(grid, row1, row2) == 1) {
                    row1 = -1;
                    isPalindrome = true;
                }
                else {
                    row1--;
                    row2++;
                    isPalindrome = false;
                }
            }
            if (isPalindrome) {
                reflection = i;
                break;
            }
        }
        return reflection + 1;
    }

    private static int findRowPalindrome(char[][] grid) {
        int reflection = -1;
        boolean isPalindrome = false;

        for (int i = 0; i < grid.length; i++) {
            int row1 = i;
            int row2 = i + 1;
            while (row1 >= 0 && row2 < grid.length) {
                if (!isRowPalindrome(grid, row1, row2)) {
                    row1 = -1;
                    isPalindrome = false;
                }
                else {
                    row1--;
                    row2++;
                    isPalindrome = true;
                }
            }
            if (isPalindrome) {
                reflection = i;
                break;
            }
        }
        return reflection + 1;
    }

    private static int isRowPalindromish(char[][] grid, int row1, int row2) {
        int count = 0;
        for (int col = 0; col < grid[0].length; col++) {
            if (grid[row1][col] != grid[row2][col]) {
                count++;
            }
        }
        return count;
    }

    private static boolean isRowPalindrome(char[][] grid, int row1, int row2) {
        for (int col = 0; col < grid[0].length; col++) {
            if (grid[row1][col] != grid[row2][col]) {
                return false;
            }
        }
        return true;
    }

    private static boolean isColumnPalindrome(char[][] grid, int col1, int col2) {
        for (int row = 0; row < grid.length; row++) {
            if (grid[row][col1] != grid[row][col2]) {
                return false;
            }
        }
        return true;
    }

    private static int findColumnPalindrome(char[][] grid) {
        int reflection = -1;
        boolean isPalindrome = false;

        for (int i = 0; i < grid[0].length; i++) {
            int col1 = i;
            int col2 = i + 1;
            while (col1 >= 0 && col2 < grid[0].length) {
                if (!isColumnPalindrome(grid, col1, col2)) {
                    col1 = -1;
                    isPalindrome = false;
                }
                else {
                    isPalindrome = true;
                    col1--;
                    col2++;
                }
            }
            if (isPalindrome) {
                reflection = i;
                break;
            }
        }
        return reflection + 1;
    }

    private static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    private static String readInput() {
        String file = "src/com/cwagnello/input.txt";
        String input = null;
        try {
            input = readFile(file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return input;
    }

}
