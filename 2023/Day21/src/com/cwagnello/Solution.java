package com.cwagnello;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Scanner;

public class Solution {
    public static void main (String[] args) {
        File file = new File("src/com/cwagnello/input.txt");
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

        char[][] grid = generateGrid(input);
        Map<Coordinate, Long> distances = dijkstra(grid);
        System.out.println("Day 21 - Part1: " + part1(distances, 64));
        System.out.println("Day 21 - Part2: " + part2(distances, 26501365));

        part3(distances, grid);
    }

    private static long part1(Map<Coordinate, Long> distances, long steps) {
        long count = 0;
        for (long distance : distances.values()) {
            if (distance < steps + 1 && distance % 2 == 0) {
                count++;
            }
        }
        return count;
    }

    private static long part2(Map<Coordinate, Long> distances, long steps) {
        //Credit for showing the way to get the answer geometrically
        //https://github.com/villuna/aoc23/wiki/A-Geometric-solution-to-advent-of-code-2023,-day-21
        long gridSize = 131;
        long numberOfGrids = (steps - gridSize / 2) / gridSize;

        long numberOfOddFullGrid = distances.values().stream().filter(s -> s % 2 == 1).count();
        long numberOfEvenFullGrid = distances.values().stream().filter(s -> s % 2 == 0).count();

        long numberOfOddCorners = distances.values().stream().filter(s -> s > 65 && s % 2 == 1).count();
        long numberOfEvenCorners = distances.values().stream().filter(s -> s > 65 && s % 2 == 0).count();

        //Formula is basically sum up:
        // 1. all odd-parity full grids multiplied by (max grids reached in any direction + 1) ^ 2
        // 2. all even-parity full grids multiplied by (max grids reached in any direction) ^ 2
        // 3. subtract the odd parity corner parts multiplied by (max grids reached in any direction + 1)
        // 4. add the even parity corner parts multiplied by (max grids reached in any direction + 1)
        return numberOfOddFullGrid * (numberOfGrids + 1) * (numberOfGrids + 1)
                + numberOfEvenFullGrid * numberOfGrids * numberOfGrids
                - numberOfOddCorners * (numberOfGrids + 1)
                + numberOfEvenCorners * numberOfGrids;
    }

    private static void part3(Map<Coordinate, Long> distances, char[][] grid) {
        for (Map.Entry<Coordinate, Long> coordinate : distances.entrySet()) {
            if (coordinate.getValue() <= 65 && coordinate.getValue() % 2 == 1) {
                grid[coordinate.getKey().row][coordinate.getKey().col] = 'O';
            }
        }
        for (int row = 0; row < grid.length; row++) {
            System.out.println(new String(grid[row]));
        }
    }

    private static char[][] generateGrid(List<String> input) {
        char[][] grid = new char[input.size()][input.get(0).length()];
        for (int row = 0; row < input.size(); row++) {
            grid[row] = input.get(row).toCharArray();
        }
        return grid;
    }

    private static Map<Coordinate, Long> dijkstra (char[][] grid) {
        Map<Coordinate, Long> distances = new HashMap<>();
        Queue<Coordinate> queue = new LinkedList<>();
        Coordinate start = findStart(grid);

        distances.put(start, 0L);
        queue.offer(start);

        while (!queue.isEmpty()) {
            Coordinate current = queue.remove();
            List<Coordinate> neighbors = getNeighbors(grid, current);
            long currentDistance = distances.get(current);
            for (Coordinate neighbor: neighbors) {
                if (currentDistance + 1 < distances.getOrDefault(neighbor, Long.MAX_VALUE)) {
                    distances.put(neighbor, currentDistance + 1);
                    queue.offer(neighbor);
                }
            }
        }
        return distances;
    }

    private static Coordinate findStart(char[][] grid) {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if(grid[row][col] == 'S') {
                    return new Coordinate(row, col);
                }
            }
        }
        throw new IllegalStateException("No start coordinate found");
    }

    private static List<Coordinate> getNeighbors(char[][] grid, Coordinate coordinate) {
        int direction[][] = {{0,1}, {0,-1}, {-1,0}, {1,0}};
        List<Coordinate> neighbors = new ArrayList<>();
        for (int[] dir: direction) {
            int newRow = dir[0] + coordinate.row;
            int newCol = dir[1] + coordinate.col;
            if (newRow >= 0 && newRow < grid.length && newCol >= 0 && newCol < grid[0].length && grid[newRow][newCol] != '#') {
                neighbors.add(new Coordinate(newRow, newCol));
            }
        }
        return neighbors;
    }
}

class Coordinate {
    int row;
    int col;
    public Coordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.row, this.col);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return row == that.row && col == that.col;
    }
}