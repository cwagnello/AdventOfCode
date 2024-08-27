package com.cwagnello.aoc2016.day13;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class Solution {
    private static final int[] directions = {0, -1, 0, 1, 0};

    public static void main(String[] args) {

        File file = new File("src/main/resources/input.txt");
        List<String> input = new ArrayList<>();
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                //assume input data is well formed
                input.add(line);
            }
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Favorite.set(Integer.parseInt(input.getFirst()));
        Coordinate end = new Coordinate(39, 31);

        System.out.println("2016 day 13 part 1: " + part1(end));
        System.out.println("2016 day 13 part 2: " + part2());
    }

    private static int part1(Coordinate end) {
        Set<Coordinate> visited = new HashSet<>();
        Queue<Coordinate> queue = new LinkedList<>();

        Coordinate start = new Coordinate(1, 1);
        queue.offer(start);
        int steps = 0;

        while (!queue.isEmpty()) {
            int queueSize = queue.size();
            for (int i = 0; i < queueSize; i++) {
                Coordinate current = queue.remove();
                if (current.equals(end)) {
                    return steps;
                }
                for (int j = 0; j < directions.length - 1; j++) {
                    int dy = directions[j] + current.getY();
                    int dx = directions[j + 1] + current.getX();
                    Coordinate newCoordinate = new Coordinate(dy, dx);
                    if (isInBounds(dy, dx) && !visited.contains(newCoordinate) && newCoordinate.isOpen()) {
                        visited.add(newCoordinate);
                        queue.offer(newCoordinate);
                    }
                }
            }
            steps++;
        }
        return -1;
    }

    private static int part2() {
        Set<Coordinate> visited = new HashSet<>();
        Queue<Coordinate> queue = new LinkedList<>();

        Coordinate start = new Coordinate(1, 1);
        queue.offer(start);
        int steps = 0;

        while (steps < 50) {
            int queueSize = queue.size();
            for (int i = 0; i < queueSize; i++) {
                Coordinate current = queue.remove();
                for (int j = 0; j < directions.length - 1; j++) {
                    int dy = directions[j] + current.getY();
                    int dx = directions[j + 1] + current.getX();
                    Coordinate newCoordinate = new Coordinate(dy, dx);
                    if (isInBounds(dy, dx) && !visited.contains(newCoordinate) && newCoordinate.isOpen()) {
                        visited.add(newCoordinate);
                        queue.offer(newCoordinate);
                    }
                }
            }
            steps++;
        }
        return visited.size();
    }

    private static boolean isInBounds(int y, int x) {
        return x >= 0 && y >= 0;
    }

}
