package com.cwagnello;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
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

        char[][] universe = new char[input.size()][input.get(0).length()];
        for (int i = 0; i < universe.length; i++) {
            universe[i] = input.get(i).toCharArray();
        }

        List<Pair> locations = findPlanets(universe);
        List<Integer> emptyRows = new ArrayList<>();
        List<Integer> emptyColumns = new ArrayList<>();
        findEmptySpace(universe, emptyRows, emptyColumns);
        System.out.println("EmptyRows: " + emptyRows);
        System.out.println("EmptyColumns: " + emptyColumns);
        System.out.println("Day11 - Part1: " + process(locations, emptyRows, emptyColumns, 1));
        System.out.println("Day11 - Part2: " + process(locations, emptyRows, emptyColumns, 999999));
    }

    private static long process(List<Pair> locations, List<Integer> emptyRows, List<Integer> emptyColumns, long expansion) {
        long sum = 0;
        for (int i = 0; i < locations.size(); i++) {
            Pair current = locations.get(i);
            for (int j = i + 1; j < locations.size(); j++) {
                Pair next = locations.get(j);
                sum += Math.abs(current.getRow() - next.getRow())
                        + Math.abs(current.getColumn() - next.getColumn());
                for (int num : emptyRows) {
                    if ((current.getRow() < num && next.getRow() > num) || (current.getRow() > num && next.getRow() < num)) {
                        sum += expansion;
                    }
                }
                for (int num : emptyColumns) {
                    if ((current.getColumn() < num && next.getColumn() > num) || (current.getColumn() > num && next.getColumn() < num)) {
                        sum += expansion;
                    }
                }
            }
        }
        return sum;
    }

    private static List<Pair> findPlanets(char[][] universe) {
        List<Pair> locations = new ArrayList<>();

        for (int row = 0; row < universe.length; row++) {
            for (int col = 0; col < universe[0].length; col++) {
                if (universe[row][col] == '#') {
                    locations.add(new Pair(row, col));
                }
            }
        }
        return locations;
    }
    private static void findEmptySpace(char[][] grid, List<Integer> emptyRows, List<Integer> emptyColumns) {
        for (int row = 0; row < grid.length; row++) {
            boolean hasPlanetRow = false;
            boolean hasPlanetColumn = false;
            int col = 0;
            for ( ; col < grid[0].length; col++) {
                if (grid[row][col] == '#') {
                    hasPlanetRow = true;
                }
                if (grid[col][row] == '#') {
                    hasPlanetColumn = true;
                }
            }
            if (hasPlanetRow == false) {
                emptyRows.add(row);
            }
            if (hasPlanetColumn == false) {
                emptyColumns.add(row);
            }
        }
    }
}

class Pair {
    private int row;
    private int column;

    public Pair(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return row == pair.row && column == pair.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    @Override
    public String toString() {
        return "(" + row +
                ", " + column + ')';
    }
}