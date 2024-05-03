package com.cwagnello;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class Solution {

    public static void main (String[] args) {
        String data = readInput();
        String[] lines = data.split("\\n");

        System.out.println("Day14 - Part1: " + part1(makeGrid(lines)));
        long startTime = System.currentTimeMillis();
        System.out.println("Day14 - Part2: " + part2(makeGrid(lines)));
        System.out.println("Elapsed time: " + (System.currentTimeMillis() - startTime));
    }

    private static char[][] makeGrid(String[] lines) {
        char[][] grid = new char[lines.length][lines[0].length()];

        for (int row = 0; row < lines.length; row++) {
            grid[row] = lines[row].toCharArray();
        }
        return grid;
    }

    private static long part1(char[][] grid) {
        north(grid);
        return calculateSum(grid);
    }

    private static String calculateHash(char[][] grid) {

        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col += 4) {
                int hex = 0;
                for (int i = 0; i < 4; i++) {
                    if (col + i < grid[0].length && grid[row][col + i] == 'O') {
                        hex += Math.pow(2, i);
                    }
                }
                sb.append(String.format("%01x", hex));
            }
        }
        return sb.toString();
    }

    private static long calculateSum(char[][] grid) {
        long sum = 0;
        for (int row = grid.length - 1; row >= 0; row--) {
            for (int col = grid[0].length - 1; col >= 0; col--) {
                if (grid[row][col] == 'O') {
                    sum += grid.length - row;
                }
            }
        }
        return sum;
    }

    private static long part2(char[][] grid) {
        Set<String> hashes = new HashSet<>();
        int cycleLength = 0;
        String firstRepeat = "";
        boolean isInCycle = false;
        int nonCycle = 0;

        for (int i = 0; i < 400; i++) {
            north(grid);
            west(grid);
            south(grid);
            east(grid);
            String gridSnapshot = calculateHash(grid);
            if (firstRepeat.length() == 0 && hashes.contains(gridSnapshot)) {
                firstRepeat = gridSnapshot;
                isInCycle = true;
            }
            if (isInCycle) {
                if (firstRepeat.equals(gridSnapshot) && cycleLength > 0) {
                    nonCycle = i;
                    break;
                }
                cycleLength++;
            }

            hashes.add(gridSnapshot);
            //System.out.println("Grid sum: " + calculateSum(grid) + ", hash: " + calculateHash(grid));
        }
        int endIndex = (1000_000_000 - nonCycle - 1) % cycleLength;
        for (int i = 0; i < endIndex; i++) {
            north(grid);
            west(grid);
            south(grid);
            east(grid);
        }
        return calculateSum(grid);
    }
    private static void north(char[][] grid) {
        for (int col = 0; col < grid[0].length; col++) {
            int index = 0;
            for (int row = 0; row < grid.length; row++) {
                if (grid[row][col] == '#') {
                    index = row + 1;
                    continue;
                }
                if (grid[row][col] != '.') {
                    char temp = grid[index][col];
                    grid[index][col] = grid[row][col];
                    grid[row][col] = temp;
                    index++;
                }
            }
        }
    }

    private static void south(char[][] grid) {
        for (int col = 0; col < grid[0].length; col++) {
            int index = grid.length - 1;
            for (int row = grid.length - 1; row >= 0; row--) {
                if (grid[row][col] == '#') {
                    index = row - 1;
                    continue;
                }
                if (grid[row][col] != '.') {
                    char temp = grid[index][col];
                    grid[index][col] = grid[row][col];
                    grid[row][col] = temp;
                    index--;
                }
            }
        }
    }

    private static void west(char[][] grid) {
        for (int row = 0; row < grid.length; row++) {
            int index = grid[0].length - 1;
            for (int col = grid[0].length - 1; col >= 0; col--) {
                if (grid[row][col] == '#') {
                    index = col - 1;
                    continue;
                }
                if (grid[row][col] != 'O') {
                    char temp = grid[row][index];
                    grid[row][index] = grid[row][col];
                    grid[row][col] = temp;
                    index--;
                }
            }
        }
    }

    private static void east(char[][] grid) {
        for (int row = 0; row < grid.length; row++) {
            int index = 0;
            for (int col = 0; col < grid[0].length; col++) {
                if (grid[row][col] == '#') {
                    index = col + 1;
                    continue;
                }
                if (grid[row][col] != 'O') {
                    char temp = grid[row][index];
                    grid[row][index] = grid[row][col];
                    grid[row][col] = temp;
                    index++;
                }
            }
        }
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
