package com.cwagnello;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Solution {
    private static final int[][] direction = {{-1,0}, {1,0}, {0,-1}, {0,1}};
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

        List<Pair> points = new ArrayList<>();
        Pair start = findStart(input);
        points.add(start);
        char[][] grid = makeGrid(input);
        System.out.println("Day10 - Part1: " + part1(start, grid));
        System.out.println("Day10 - Part2: " + part2(start, grid));
    }

    private static int part1(Pair start, char[][] grid) {
        Deque<Pair> queue = new LinkedList<>();
        //Special case for the start get each valid connected pipe and add it to the queue
        for (int[] dir : direction) {
            int dRow = dir[0] + start.getRow();
            int dCol = dir[1] + start.getColumn();
            if (isInBounds(grid, new Pair(dRow, dCol)) && isConnected(grid, new Pair(dRow, dCol), start)) {
                queue.add(new Pair(dRow, dCol));
            }
        }

        boolean[][] visited = new boolean[grid.length][grid[0].length];
        visited[start.getRow()][start.getColumn()] = true;

        int count = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            count++;

            for (int i = 0; i < size; i++) {
                Pair current = queue.removeFirst();
                visited[current.getRow()][current.getColumn()] = true;

                for (int[] dir : direction) {
                    int dRow = dir[0] + current.getRow();
                    int dCol = dir[1] + current.getColumn();
                    Pair next = new Pair(dRow, dCol);
                    if (isInBounds(grid, next) && !visited[dRow][dCol] && isConnected(grid, current, next)) {
                        queue.add(new Pair(dRow, dCol));
                    }
                }
            }
        }
        return count;
    }

    private static int part2(Pair start, char[][] grid) {
        Deque<Pair> queue = new LinkedList<>();
        //Special case for the start get each valid connected pipe and add it to the queue
        for (int[] dir : direction) {
            int dRow = dir[0] + start.getRow();
            int dCol = dir[1] + start.getColumn();
            if (isInBounds(grid, new Pair(dRow, dCol)) && isConnected(grid, new Pair(dRow, dCol), start)) {
                queue.add(new Pair(dRow, dCol));
            }
            if (!queue.isEmpty()) {
                break;
            }
        }

        boolean[][] visited = new boolean[grid.length][grid[0].length];
        visited[start.getRow()][start.getColumn()] = true;
        List<Pair> points = new ArrayList<>();
        points.add(start);

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                Pair current = queue.removeFirst();
                visited[current.getRow()][current.getColumn()] = true;
                points.add(current);

                for (int[] dir : direction) {
                    int dRow = dir[0] + current.getRow();
                    int dCol = dir[1] + current.getColumn();
                    Pair next = new Pair(dRow, dCol);
                    if (isInBounds(grid, next) && !visited[dRow][dCol] && isConnected(grid, current, next)) {
                        queue.add(new Pair(dRow, dCol));
                    }
                }
            }
        }
        //System.out.println("Points: " + points);
        points.add(start);
        int sum = 0;
        for (int i = 1; i < points.size(); i++) {
            int x0 = points.get(i - 1).getColumn();
            int x1 = points.get(i).getColumn();
            int y0 = points.get(i - 1).getRow();
            int y1 = points.get(i).getRow();
            sum += (x1 - x0) * (y1 + y0);
        }
        sum /= 2;
        return sum - points.size() / 2 + 1;
    }

    /**
     * | is a vertical pipe connecting north and south.
     * - is a horizontal pipe connecting east and west.
     * L is a 90-degree bend connecting north and east.
     * J is a 90-degree bend connecting north and west.
     * 7 is a 90-degree bend connecting south and west.
     * F is a 90-degree bend connecting south and east.
     */
    private static boolean isConnected(char[][] grid, Pair current, Pair next) {
        return switch (grid[current.getRow()][current.getColumn()]) {
            case '|' ->
                    isConnectedAbove(grid, current, next)
                 || isConnectedBelow(grid, current, next);
            case '-' ->
                    isConnectedLeft(grid, current, next)
                 || isConnectedRight(grid, current, next);
            case 'L' ->
                    isConnectedAbove(grid, current, next)
                 || isConnectedRight(grid, current, next);
            case 'J' ->
                    isConnectedAbove(grid, current, next)
                 || isConnectedLeft(grid, current, next);
            case '7' ->
                    isConnectedLeft(grid, current, next)
                 || isConnectedBelow(grid, current, next);
            case 'F' ->
                    isConnectedRight(grid, current, next)
                 || isConnectedBelow(grid, current, next);
            default -> false;
        };
    }

    private static boolean isConnectedRight(char[][] grid, Pair current, Pair next) {
        return (grid[next.getRow()][next.getColumn()] == 'S' || grid[next.getRow()][next.getColumn()] == '-' || grid[next.getRow()][next.getColumn()] == '7' || grid[next.getRow()][next.getColumn()] == 'J') && (current.getColumn() - next.getColumn()) == -1;
    }

    private static boolean isConnectedLeft(char[][] grid, Pair current, Pair next) {
        return (grid[next.getRow()][next.getColumn()] == 'S' || grid[next.getRow()][next.getColumn()] == '-' || grid[next.getRow()][next.getColumn()] == 'L' || grid[next.getRow()][next.getColumn()] == 'F') && (current.getColumn() - next.getColumn()) == 1;
    }

    private static boolean isConnectedBelow(char[][] grid, Pair current, Pair next) {
        return (grid[next.getRow()][next.getColumn()] == 'S' || grid[next.getRow()][next.getColumn()] == '|' || grid[next.getRow()][next.getColumn()] == 'L' || grid[next.getRow()][next.getColumn()] == 'J') && (current.getRow() - next.getRow()) == -1;
    }

    private static boolean isConnectedAbove(char[][] grid, Pair current, Pair next) {
        return (grid[next.getRow()][next.getColumn()] == 'S' || grid[next.getRow()][next.getColumn()] == '|' || grid[next.getRow()][next.getColumn()] == '7' || grid[next.getRow()][next.getColumn()] == 'F') && (current.getRow() - next.getRow()) == 1;
    }

    private static boolean isInBounds(char[][] grid, Pair current) {
        return current.getRow() >= 0 && current.getRow() < grid.length && current.getColumn() >= 0 && current.getColumn() < grid[0].length;
    }

    private static Pair findStart(List<String> input) {
        Pair start = null;
        for (int row = 0; row < input.size(); row++) {
            int column = input.get(row).indexOf("S");
            if (column != -1) {
                start = new Pair(row, column);
            }
        }
        return start;
    }
    private static char[][] makeGrid(List<String> input) {
        char[][] grid = new char[input.size()][input.get(0).length()];
        for (int i = 0; i < grid.length; i++) {
            grid[i] = input.get(i).toCharArray();
        }
        return grid;
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