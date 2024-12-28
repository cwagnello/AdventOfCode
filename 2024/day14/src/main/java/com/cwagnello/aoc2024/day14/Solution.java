package com.cwagnello.aoc2024.day14;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Solution {
    public static void main(String[] args) {
        List<String> input = readFile("src/main/resources/input.txt");
        List<Robot> robots = parse(input);
        //log.info("Sample: {}", part1(robots, 11, 7, 100));
        log.info("2024 day 14 part 1: {}", part1(robots, 101, 103, 100));
        log.info("2024 day 14 part 2: {}", part2(robots, 101, 103));
    }

    private static long part1(List<Robot> robots, long width, long height, long iterations) {
        Map<Integer, Integer> count = new HashMap<>();
        for (Robot robot: robots) {
            long dx = (robot.getPositionX() + iterations * robot.getVelocityX()) % width;
            long dy = (robot.getPositionY() + iterations * robot.getVelocityY()) % height;
            if (dx < 0) {
                dx += width;
            }
            if (dy < 0) {
                dy += height;
            }
            int quadrant = getQuadrant(dx, dy, width, height);
            //log.info("{}", robot);
            if (quadrant != -1) {
                //log.info("x: {}, y: {}", dx, dy);
                count.put(quadrant, count.getOrDefault(quadrant, 0) + 1);
            }
        }
        //log.info("Safety factors: {}", count);
        return count.values().stream().reduce((a, b) -> a * b).get();
    }

    private static long part2(List<Robot> robots, long width, long height) {
        int i = 0;
        int minSafetyFactor = Integer.MAX_VALUE;
        int elapsed = 0;
        while (i < width * height) {
            char[][] grid = new char[(int)height][(int)width];
            Map<Integer, Integer> count = new HashMap<>();
            for (Robot robot : robots) {
                long dx = (robot.getPositionX() + i * robot.getVelocityX()) % width;
                long dy = (robot.getPositionY() + i * robot.getVelocityY()) % height;
                if (dx < 0) {
                    dx += width;
                }
                if (dy < 0) {
                    dy += height;
                }
                grid[(int)dy][(int)dx] = '*';
                int quadrant = getQuadrant(dx, dy, width, height);
                //log.info("{}", robot);
                if (quadrant != -1) {
                    //log.info("x: {}, y: {}", dx, dy);
                    count.put(quadrant, count.getOrDefault(quadrant, 0) + 1);
                }
            }

            for (int row = 0; row < height; row++) {
                StringBuilder sb = new StringBuilder();
                for (int col = 0; col < width; col++) {
                    sb.append(grid[row][col] == '*' ? '*' : '.');
                }
                if (sb.toString().contains("**********")) {
                    elapsed = i;
                    //log.info("Christmas tree: {}", count.values().stream().reduce((a, b) -> a * b).get());
                    //log.info("minSafetyFactor: {}", minSafetyFactor);
                }
            }
            i++;
        }

        return elapsed;
    }

    private static int getQuadrant(long x, long y, long width, long height) {
        if (x < width / 2 && y < height / 2) {
            return 0;
        }
        else if (x > width / 2 && y < height / 2) {
            return 1;
        }
        else if (x < width / 2 && y > height / 2) {
            return 2;
        }
        else if (x > width / 2 && y > height / 2) {
            return 3;
        }
        else
            return -1;
    }

    private static List<Robot> parse(List<String> input) {
        List<Robot> robots = new ArrayList<>();
        Pattern pattern = Pattern.compile("p=(-*\\d+),(-*\\d+)\\s+v=(-*\\d+),(-*\\d+)");
        for (String line: input) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                robots.add(
                        new Robot(
                                new Coordinate(Long.parseLong(matcher.group(1)), Long.parseLong(matcher.group(2))),
                                new Velocity(Long.parseLong(matcher.group(3)), Long.parseLong(matcher.group(4)))));
            }
        }
        return robots;
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
