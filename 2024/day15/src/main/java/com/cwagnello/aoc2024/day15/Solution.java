package com.cwagnello.aoc2024.day15;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

@Slf4j
public class Solution {
    public static void main (String[] args) {
        List<String> inputGrid = readFile("src/main/resources/input-grid.txt");
        List<String> inputMoves = readFile("src/main/resources/input-moves.txt");
//        List<String> inputGrid = readFile("src/main/resources/sample-grid.txt");
//        List<String> inputMoves = readFile("src/main/resources/sample-moves.txt");

        log.info("2024 day 15 part 1: {}", part1(parseGrid(inputGrid), parseMoves(inputMoves)));
        log.info("2024 day 15 part 2: {}", part2(parseGrid2(inputGrid), parseMoves(inputMoves)));
    }

    private static long part1(char[][] grid, String[] moves) {
        Coordinate robot = findStart(grid);
        for (String move: moves) {
            int dRow = 0;
            int dCol = 0;
            switch (move) {
                case "^" -> dRow -= 1;
                case "v" -> dRow += 1;
                case ">" -> dCol += 1;
                case "<" -> dCol -= 1;
            }
            if (grid[robot.getRow() + dRow][robot.getColumn() + dCol] == '.') {
                grid[robot.getRow()][robot.getColumn()] = '.';
                robot.setRow(robot.getRow() + dRow);
                robot.setColumn(robot.getColumn() + dCol);
                grid[robot.getRow()][robot.getColumn()] = '@';
            }
            else if (grid[robot.getRow() + dRow][robot.getColumn() + dCol] == 'O') {
                int i = 0;
                while (grid[robot.getRow() + i * dRow][robot.getColumn() + i * dCol] != '#') {
                    if (grid[robot.getRow() + i * dRow][robot.getColumn() + i * dCol] == '.') {
                        grid[robot.getRow() + i * dRow][robot.getColumn() + i * dCol] = 'O';
                        while (grid[robot.getRow() + i * dRow][robot.getColumn() + i * dCol] == 'O') {
                            i--;
                        }
                        grid[robot.getRow() + i * dRow][robot.getColumn() + i * dCol] = '.';
                        i++;
                        grid[robot.getRow() + i * dRow][robot.getColumn() + i * dCol] = '@';
                        robot.setRow(robot.getRow() + i * dRow);
                        robot.setColumn(robot.getColumn() + i * dCol);
                        break;
                    }
                    i++;
                }
            }
        }
        //printGrid(grid);
        return calculateSum(grid);
    }

    private static long part2(char[][] grid, String[] moves) {
        Coordinate robot = findStart(grid);
        for (String move: moves) {
            int dRow = 0;
            int dCol = 0;
            switch (move) {
                case "^" -> dRow -= 1;
                case "v" -> dRow += 1;
                case ">" -> dCol += 1;
                case "<" -> dCol -= 1;
            }
            if (grid[robot.getRow() + dRow][robot.getColumn() + dCol] == '.') {
                grid[robot.getRow()][robot.getColumn()] = '.';
                robot.setRow(robot.getRow() + dRow);
                robot.setColumn(robot.getColumn() + dCol);
                grid[robot.getRow()][robot.getColumn()] = '@';
            }
            else if (grid[robot.getRow() + dRow][robot.getColumn() + dCol] == '['
            || grid[robot.getRow() + dRow][robot.getColumn() + dCol] == ']') {
                boolean foundWall = false;
                List<Coordinate> coordinates = new ArrayList<>();
                coordinates.add(robot);
                Deque<Coordinate> queue = new LinkedList<>();
                queue.add(robot);
                while (!queue.isEmpty()) {
                    int size = queue.size();
                    for (int j = 0; j < size; j++) {
                        Coordinate current = queue.remove();
                        //log.info("row: {}, col: {}, dir:({},{})", current.getRow(), current.getColumn(), dRow, dCol);
                        if (grid[current.getRow() + dRow][current.getColumn() + dCol] == '#') {
                            foundWall = true;
                            break;
                        }
                        if (dCol != 0) {
                            if (grid[current.getRow() + dRow][current.getColumn() + dCol] == '['
                             || grid[current.getRow() + dRow][current.getColumn() + dCol] == ']') {
                                queue.add(new Coordinate(current.getRow() + dRow, current.getColumn() + dCol));
                                coordinates.add(queue.getLast());
                            }
                        }
                        else {
                            if (grid[current.getRow() + dRow][current.getColumn() + dCol] == '[') {
                                queue.add(new Coordinate(current.getRow() + dRow, current.getColumn() + dCol));
                                if (!coordinates.contains(queue.getLast())) {
                                    coordinates.add(queue.getLast());
                                }
                                queue.add(new Coordinate(current.getRow() + dRow, current.getColumn() + dCol + 1));
                                if (!coordinates.contains(queue.getLast())) {
                                    coordinates.add(queue.getLast());
                                }
                            } else if (grid[current.getRow() + dRow][current.getColumn() + dCol] == ']') {
                                queue.add(new Coordinate(current.getRow() + dRow, current.getColumn() + dCol));
                                if (!coordinates.contains(queue.getLast())) {
                                    coordinates.add(queue.getLast());
                                }
                                queue.add(new Coordinate(current.getRow() + dRow, current.getColumn() + dCol - 1));
                                if (!coordinates.contains(queue.getLast())) {
                                    coordinates.add(queue.getLast());
                                }
                            }
                        }
                    }
                }
                if (!foundWall) {
                    //If we get here then we have space to move every box one space
                    for (Coordinate coordinate : coordinates.reversed()) {
                        grid[coordinate.getRow() + dRow][coordinate.getColumn() + dCol] = grid[coordinate.getRow()][coordinate.getColumn()];
                        grid[coordinate.getRow()][coordinate.getColumn()] = '.';
                    }
                    robot.setRow(robot.getRow() + dRow);
                    robot.setColumn(robot.getColumn() + dCol);
                }
            }
        }
        //printGrid(grid);
        return calculateSum(grid);
    }

    private static void printGrid(char[][] grid) {
        StringBuilder sb = new StringBuilder("\n");
        for (char[] chars : grid) {
            sb.append(new String(chars));
            sb.append("\n");
        }
        log.info(sb.toString());
    }

    private static long calculateSum(char[][] grid) {
        long sum = 0;
        for(int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (grid[row][col] == 'O' || grid[row][col] == '[') {
                    sum += 100L * row + col;
                }
            }
        }
        return sum;
    }

    private static Coordinate findStart(char[][] grid) {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (grid[row][col] == '@') {
                    return new Coordinate(row, col);
                }
            }
        }
        throw new IllegalStateException("Starting position not found");
    }

    private static char[][] parseGrid(List<String> input) {
        char[][] grid = new char[input.size()][input.getFirst().length()];
        for (int i = 0; i < input.size(); i++) {
            grid[i] = input.get(i).toCharArray();
        }
        return grid;
    }

    private static char[][] parseGrid2(List<String> input) {
        char[][] grid = parseGrid(input);
        char[][] grid2 = new char[input.size()][2 * input.getFirst().length()];
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                switch(grid[row][col]) {
                    case '#' -> {
                        grid2[row][2 * col] = '#';
                        grid2[row][2 * col + 1] = '#';
                    }
                    case '.' -> {
                        grid2[row][2 * col] = '.';
                        grid2[row][2 * col + 1] = '.';
                    }
                    case 'O' -> {
                        grid2[row][2 * col] = '[';
                        grid2[row][2 * col + 1] = ']';
                    }
                    case '@' -> {
                        grid2[row][2 * col] = '@';
                        grid2[row][2 * col + 1] = '.';
                    }
                }
            }
        }
        return grid2;
    }

    private static String[] parseMoves(List<String> input) {
        StringBuilder sb = new StringBuilder();
        for (String line: input) {
            sb.append(line);
        }
        return sb.toString().split("");
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
