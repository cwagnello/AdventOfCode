package com.cwagnello;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

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
        char[][] grid = new char[input.size()][input.get(0).length()];
        for (int row = 0; row < input.size(); row++) {
            grid[row] = input.get(row).toCharArray();
        }
        Solution solution = new Solution();
        System.out.println("Day 16 - Part1: " + solution.part1(grid));
        System.out.println("Day 16 - Part2: " + solution.part2(grid));

    }

    private int part1(char[][] grid) {
        Deque<Beam> queue = new LinkedList<>();
        Set<Beam> visited = new HashSet<>();
        Set<String> energized = new HashSet<>();
        energized.add("0_0");
        queue.add(new Right(0, 0));

        process(grid, queue, visited, energized);

        return energized.size();
    }

    private int part2(char[][] grid) {
        List<Beam> start = new ArrayList<>();
        for (int index = 0; index < grid.length; index++) {
            start.add(new Right(index, 0));
            start.add(new Left(index, grid[0].length - 1));
            start.add(new Up(grid.length - 1, index));
            start.add(new Down(0, index));
        }
        int max = 0;
        for (Beam b : start) {
            Deque<Beam> queue = new LinkedList<>();
            Set<Beam> visited = new HashSet<>();
            Set<String> energized = new HashSet<>();
            energized.add(b.row + "_" + b.col);
            queue.add(b);

            process(grid, queue, visited, energized);

            max = Math.max (max, energized.size());
        }

        return max;

    }

    private void process(char[][] grid, Deque<Beam> queue, Set<Beam> visited, Set<String> energized) {
        while (!queue.isEmpty()) {
            Beam current = queue.poll();
            visited.add(current);
            List<Beam> newBeams = new ArrayList<>();
            if (grid[current.row][current.col] == '.') {
                switch (current) {
                    case Right right -> newBeams = List.of(new Right(right.row, right.col + 1));
                    case Left left -> newBeams = List.of(new Left(left.row, left.col - 1));
                    case Up up -> newBeams = List.of(new Up(up.row - 1, up.col));
                    case Down down -> newBeams = List.of(new Down(down.row + 1, down.col));
                    default -> throw new IllegalStateException("Unexpected value: " + current);
                }
            }
            else if (grid[current.row][current.col] == '/') {
                switch(current) {
                    case Right right -> newBeams = List.of(new Up(right.row - 1, right.col));
                    case Left left -> newBeams = List.of(new Down(left.row + 1, left.col));
                    case Up up -> newBeams = List.of(new Right(up.row, up.col + 1));
                    case Down down -> newBeams = List.of(new Left(down.row, down.col - 1));
                    default -> throw new IllegalStateException("Unexpected value: " + current);
                }
            }
            else if (grid[current.row][current.col] == '\\') {
                switch(current) {
                    case Right b -> newBeams = List.of(new Down(b.row + 1, b.col));
                    case Left b -> newBeams = List.of(new Up(b.row - 1, b.col));
                    case Up b -> newBeams = List.of(new Left(b.row, b.col - 1));
                    case Down b -> newBeams = List.of(new Right(b.row, b.col + 1));
                    default -> throw new IllegalStateException("Unexpected value: " + current);
                }
            }
            else if (grid[current.row][current.col] == '|') {
                switch(current) {
                    case Right b -> newBeams = List.of(new Down(b.row + 1, b.col), new Up(b.row - 1, b.col));
                    case Left b -> newBeams = List.of(new Down(b.row + 1, b.col), new Up(b.row - 1, b.col));
                    case Up b -> newBeams = List.of(new Up(b.row - 1, b.col));
                    case Down b -> newBeams = List.of(new Down(b.row + 1, b.col));
                    default -> throw new IllegalStateException("Unexpected value: " + current);
                }
            }
            else if (grid[current.row][current.col] == '-') {
                switch(current) {
                    case Right b -> newBeams = List.of(new Right(b.row, b.col + 1));
                    case Left b -> newBeams = List.of(new Left(b.row, b.col - 1));
                    case Up b -> newBeams = List.of(new Left(b.row, b.col - 1), new Right(b.row, b.col + 1));
                    case Down b -> newBeams = List.of(new Left(b.row, b.col - 1), new Right(b.row, b.col + 1));
                    default -> throw new IllegalStateException("Unexpected value: " + current);
                }
            }

            for (Beam beam : newBeams) {
                if (Solution.isInBounds(grid, beam) && !visited.contains(beam)) {
                    queue.add(beam);
                    energized.add(beam.row + "_" + beam.col);
                }
            }

        }
    }

    private static boolean isInBounds(char[][] grid, Beam beam) {
        return beam.row >= 0 && beam.col >= 0 && beam.row < grid.length && beam.col < grid[0].length;
    }

}

abstract class Beam {
    int row;
    int col;

    public Beam() {

    }
    public Beam(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Beam beam = (Beam) o;
        return row == beam.row && col == beam.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}

class Up extends Beam {
    public Up(int row, int col) {
        super(row, col);
    }
}

class Down extends Beam {
    public Down(int row, int col) {
        super(row, col);
    }
}

class Right extends Beam {
    public Right(int row, int col) {
        super(row, col);
    }
}

class Left extends Beam {
    public Left(int row, int col) {
        super(row, col);
    }
}