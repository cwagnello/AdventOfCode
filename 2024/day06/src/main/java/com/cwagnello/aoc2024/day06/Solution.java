package com.cwagnello.aoc2024.day06;

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
    public static void main(String[] args) {
        List<String> input = readFile("src/main/resources/input.txt");

        char[][] grid = parse(input);
        log.info("2024 day 6 part 1: {}", part1(grid));
        log.info("2024 day 6 part 2: {}", part2(grid));
    }

    private static long part2(char[][] grid) {
        Guard guard = findStart(grid);
        Guard start = new Guard(guard.getRow(), guard.getColumn());

        Set<String> path = guardTraversal(grid, guard);
        path.remove(start.getRow() + "_" + start.getColumn());

        long count = 0;
        for (int row = 0; row < grid.length; row++){
            for (int col = 0; col < grid[0].length; col++) {
                if (grid[row][col] == '#') {
                    continue;
                }
                grid[row][col] = '#';

                Set<Guard> visited = new HashSet<>();
                guard = new Guard(start.getRow(), start.getColumn());
                //visited.add(guard);
                while (true) {
                    //log.info(guard.toString());
                    int dRow = getNewRow(guard);
                    int dCol = getNewColumn(guard);
                    if (dRow < 0 || dRow >= grid.length || dCol < 0 || dCol >= grid[0].length) {
                        grid[row][col] = '.';
                        break;
                    }
                    if (grid[dRow][dCol] == '#') {
                        guard = new Guard(guard);
                        guard.setDirection(getNewDirection(guard));
                    } else {
                        guard = new Guard(guard);
                        guard.setRow(dRow);
                        guard.setColumn(dCol);
                    }
                    if (visited.contains(guard)) {
                        count++;
                        grid[row][col] = '.';
                        break;
                    }
                    visited.add(guard);
                }
            }
        }

        return count;
    }

    private static long part1(char[][] grid) {
        Guard guard = findStart(grid);
        return guardTraversal(grid, guard).size();
    }

    private static Set<String> guardTraversal(char[][] grid, Guard guard) {
        Set<String> visited = new HashSet<>();
        visited.add(guard.getRow() + "_" + guard.getColumn());

        while (true) {
            int dRow = getNewRow(guard);
            int dCol = getNewColumn(guard);
            if (dRow < 0 || dRow >= grid.length || dCol < 0 || dCol >= grid[0].length) {
                break;
            }
            if (grid[dRow][dCol] == '#') {
                guard.setDirection(getNewDirection(guard));
                continue;
            }
            guard.setRow(dRow);
            guard.setColumn(dCol);
            visited.add(guard.getRow() + "_" + guard.getColumn());
        }
        return visited;
    }

    private static Direction getNewDirection(Guard guard) {
        return switch (guard.getDirection()) {
            case UP -> Direction.RIGHT;
            case RIGHT -> Direction.DOWN;
            case DOWN -> Direction.LEFT;
            case LEFT -> Direction.UP;
        };
    }

    private static int getNewRow(Guard guard) {
        int row = guard.getRow();
        return switch (guard.getDirection()) {
            case UP -> row - 1;
            case DOWN -> row + 1;
            default -> row;
        };
    }

    private static int getNewColumn(Guard guard) {
        int column = guard.getColumn();
        return switch (guard.getDirection()) {
            case LEFT -> column - 1;
            case RIGHT -> column + 1;
            default -> column;
        };
    }

    private static Guard findStart(char[][] grid) {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (grid[row][col] == '^') {
                    return new Guard(row, col);
                }
            }
        }
        throw new IllegalStateException("Could not find start point");
    }

    private static char[][] parse(List<String> input) {
        char[][] grid = new char[input.size()][input.get(0).length()];
        for (int i = 0; i < input.size(); i++) {
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
