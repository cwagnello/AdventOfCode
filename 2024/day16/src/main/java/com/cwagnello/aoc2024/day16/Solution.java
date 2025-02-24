package com.cwagnello.aoc2024.day16;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

@Slf4j
public class Solution {

    public static void main(String[] args) {
        List<String> input = readFile("src/main/resources/input.txt");
        long start = System.currentTimeMillis();
        char[][] grid = parse(input);
        log.info("2024 day 16 part 1: {}", part1(grid));

        log.info("Time: {}", System.currentTimeMillis() - start);
        log.info("2024 day 16 part 2: {}", part2(grid));
    }

    private static long part1(char[][] grid) {
        Node bestScore = dijkstra(grid);
        //printGrid(grid);
        return bestScore.getScore();
    }

    private static long part2(char[][] grid) {
        return 0L;
    }

    private static Node dijkstra(char[][] grid) {
        Node start = new Node(grid.length - 2, 1, null, DIRECTION.EAST, 0);
        Node end = new Node(1, grid[0].length - 2);
        Map<String, Node> graph = new HashMap<>();
        Queue<Node> queue = new LinkedList<>();
        queue.add(start);

        while (!queue.isEmpty()) {
            Node current = queue.remove();
            if (current.equals(end) && current.getScore() < end.getScore()) {
                //log.info("Current -> {}", current);
                end = new Node(current);
            }
            for (DIRECTION dir: DIRECTION.values()) {
                int dRow = dir.getDiffRow() + current.getRow();
                int dCol = dir.getDiffCol() + current.getColumn();
                Node next = new Node(dRow, dCol, null, dir, Long.MAX_VALUE);
                if (!graph.containsKey(generateKey(next))) {
                    graph.put(generateKey(next), next);
                }
                next = graph.get(generateKey(next));

                if (isInBounds(grid, next.getRow(), next.getColumn()) && !isAWall(grid, next)) {
                    if (next.getDirection().equals(current.getDirection())) {
                        if (current.getScore() + 1 < next.getScore()) {
                            next.setScore(current.getScore() + 1);
                            next.setStep(current);
                            queue.add(next);
                        }
                    }
                    else {
                        if (current.getScore() + 1001 < next.getScore()) {
                            next.setScore(current.getScore() + 1001);
                            next.setStep(current);
                            queue.add(next);
                        }
                    }
                }
            }
        }
        return end;
    }

    private static String generateKey(Node node) {
        return "" + node.getRow() + "_" + node.getColumn() + "_" + node.getDirection();
    }

    private static void printGrid(char[][] grid) {
        StringBuilder sb = new StringBuilder("\n");
        for (char[] chars : grid) {
            sb.append(new String(chars));
            sb.append("\n");
        }
        log.info(sb.toString());
    }

    private static boolean isAWall(char[][] grid, Node node) {
        return grid[node.getRow()][node.getColumn()] == '#';
    }


    private static boolean isInBounds(char[][] grid, int row, int col) {
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
