package com.cwagnello.aoc2024.day12;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

@Slf4j
public class Solution {
    private static final int[] DIRECTIONS = new int[]{0, 1, 0, -1, 0};
    private static final int ROW = 0;
    private static final int COL = 1;

    public static void main(String[] args) {
        List<String> input = readFile("src/main/resources/input.txt");
        char[][] grid = parse(input);
        log.info("2024 day 12 part 1: {}", part1(grid));
        log.info("2024 day 12 part 2: {}", part2(grid));
    }

    private static long part1(char[][] grid) {
        long sum = 0;
        Set<String> visited = new HashSet<>();
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (!visited.contains(row + "_" + col)) {
                    long area = 0, perimeter = 0;
                    Queue<int[]> queue = new LinkedList<>();
                    queue.add(new int[]{row, col});
                    visited.add(row + "_" + col);
                    while (!queue.isEmpty()) {
                        int[] current = queue.remove();
                        area++;
                        perimeter += calculatePerimeter(grid, current[ROW], current[COL]);
                        for (int i = 1; i < DIRECTIONS.length; i++) {
                            int dRow = current[ROW] + DIRECTIONS[i - 1];
                            int dCol = current[COL] + DIRECTIONS[i];
                            if (inBounds(grid, dRow, dCol) && !visited.contains(dRow + "_" + dCol) && grid[row][col] == grid[dRow][dCol]) {
                                queue.add(new int[]{dRow, dCol});
                                visited.add(dRow + "_" + dCol);
                            }
                        }
                    }
                    //log.info("char: {}, area: {}, perimeter: {}", grid[row][col], area, perimeter);
                    sum += area * perimeter;
                    //log.info("Visited: {}", visited);
                }
            }
        }
        return sum;
    }

    private static long part2(char[][] grid) {
        long sum = 0;
        Set<String> visited = new HashSet<>();
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (!visited.contains(row + "_" + col)) {
                    long area = 0, corners = 0;
                    Queue<int[]> queue = new LinkedList<>();
                    queue.add(new int[]{row, col});
                    visited.add(row + "_" + col);
                    while (!queue.isEmpty()) {
                        int[] current = queue.remove();
                        area++;
                        corners += calculateCorners(grid, current[ROW], current[COL]);
                        for (int i = 1; i < DIRECTIONS.length; i++) {
                            int dRow = current[ROW] + DIRECTIONS[i - 1];
                            int dCol = current[COL] + DIRECTIONS[i];
                            if (inBounds(grid, dRow, dCol) && !visited.contains(dRow + "_" + dCol) && grid[row][col] == grid[dRow][dCol]) {
                                queue.add(new int[]{dRow, dCol});
                                visited.add(dRow + "_" + dCol);
                            }
                        }
                    }
                    //log.info("char: {}, area: {}, corners: {}", grid[row][col], area, corners);
                    sum += area * corners;
                    //log.info("Visited: {}", visited);
                }
            }
        }
        return sum;
    }

    private static long calculateCorners(char[][] grid, int row, int col) {
        char current = grid[row][col];
        long corners = 0;

        //Up and Left
        if (inBounds(grid, row - 1, col) && inBounds(grid, row, col - 1)) {
            if (current != grid[row - 1][col] && current != grid[row][col - 1]) {
                corners++;
            }
            else if (current == grid[row - 1][col] && current == grid[row][col - 1] && current != grid[row - 1][col - 1]) {
                corners++;
            }
        }
        else if (!inBounds(grid, row - 1, col) && !inBounds(grid, row, col - 1)) {
            corners++;
        }
        else if ((!inBounds(grid, row - 1, col) && inBounds(grid, row, col - 1) && current != grid[row][col - 1])
              || (inBounds(grid, row - 1, col) && !inBounds(grid, row, col - 1) && current != grid[row - 1][col])) {
            corners++;
        }

        //Up and Right
        if (inBounds(grid, row - 1, col) && inBounds(grid, row, col + 1)) {
            if (current != grid[row - 1][col] && current != grid[row][col + 1]) {
                corners++;
            }
            else if (current == grid[row - 1][col] && current == grid[row][col + 1] && current != grid[row - 1][col + 1]) {
                corners++;
            }
        }
        else if (!inBounds(grid, row - 1, col) && !inBounds(grid, row, col + 1)) {
            corners++;
        }
        else if ((!inBounds(grid, row - 1, col) && inBounds(grid, row, col + 1) && current != grid[row][col + 1])
             || (inBounds(grid, row - 1, col) && !inBounds(grid, row, col + 1) && current != grid[row - 1][col])) {
            corners++;
        }

        //Down and Left
        if (inBounds(grid, row + 1, col) && inBounds(grid, row, col - 1)) {
            if (current != grid[row + 1][col] && current != grid[row][col - 1]) {
                corners++;
            }
            else if (current == grid[row + 1][col] && current == grid[row][col - 1] && current != grid[row + 1][col - 1]) {
                corners++;
            }
        }
        else if (!inBounds(grid, row + 1, col) && !inBounds(grid, row, col - 1)) {
            corners++;
        }
        else if ((!inBounds(grid, row + 1, col) && inBounds(grid, row, col - 1) && current != grid[row][col - 1])
              || (inBounds(grid, row + 1, col) && !inBounds(grid, row, col - 1) && current != grid[row + 1][col])) {
            corners++;
        }

        //Down and Right
        if (inBounds(grid, row + 1, col) && inBounds(grid, row, col + 1)) {
            if (current != grid[row + 1][col] && current != grid[row][col + 1]) {
                corners++;
            }
            else if (current == grid[row + 1][col] && current == grid[row][col + 1] && current != grid[row + 1][col + 1]) {
                corners++;
            }
        }
        else if (!inBounds(grid, row + 1, col) && !inBounds(grid, row, col + 1)) {
            corners++;
        }
        else if ((!inBounds(grid, row + 1, col) && inBounds(grid, row, col + 1) && current != grid[row][col + 1])
              || (inBounds(grid, row + 1, col) && !inBounds(grid, row, col + 1) && current != grid[row + 1][col])) {
            corners++;
        }

        return corners;
    }

    private static long calculatePerimeter(char[][] grid, int row, int col) {
        char current = grid[row][col];
        long perimeter = 0;
        for (int i = 1; i < DIRECTIONS.length; i++) {
            int dRow = row + DIRECTIONS[i - 1];
            int dCol = col + DIRECTIONS[i];
            if (inBounds(grid, dRow, dCol)) {
                if (grid[dRow][dCol] != current) {
                    perimeter++;
                }
            }
            else {
                perimeter++;
            }
        }
        return perimeter;
    }

    private static boolean inBounds(char[][] grid, int row, int col) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
    }

    private static char[][] parse(List<String> input) {
        char[][] grid = new char[input.size()][input.getFirst().length()];
        for (int i = 0; i < input.size(); i++) {
            grid[i] = input.get(i).toCharArray();
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
