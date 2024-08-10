package com.cwagnello;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution {

    //north -> up, east -> right, south -> down, west -> up
    private static int[][] DIR = {{0, -1}, {1, 0}, {0, 1}, {-1, 0}};

    public static void main(String[] args) {

        File file = new File("src/main/resources/input.txt");
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
        String[] directions = parse(input);
        System.out.println("2016 Day01 part1: " + part1(directions));
        System.out.println("2016 Day01 part2: " + part2(directions));
    }

    private static int part1(String[] directions) {
        Pattern pattern = Pattern.compile("^(\\w)(\\d+)$");
        int x = 0;
        int y = 0;
        int orientation = 0;
        for (String direction : directions) {
            Matcher matcher = pattern.matcher(direction);
            if (matcher.find()) {
                String turn = matcher.group(1);
                int steps = Integer.parseInt(matcher.group(2));
                orientation = calculateOrientation(turn, orientation);
                x += DIR[orientation][0] * steps;
                y += DIR[orientation][1] * steps;
            }
        }
        return Math.abs(x) + Math.abs(y);
    }

    private static int part2(String[] directions) {
        Pattern pattern = Pattern.compile("^(\\w)(\\d+)$");
        int x = 0;
        int y = 0;
        int orientation = 0;
        Set<String> visited = new HashSet<>();

        for (String direction : directions) {
            Matcher matcher = pattern.matcher(direction);
            if (matcher.find()) {
                String turn = matcher.group(1);
                int steps = Integer.parseInt(matcher.group(2));
                orientation = calculateOrientation(turn, orientation);
                for (int dx = 0; dx < steps; dx++) {
                    x += DIR[orientation][0];
                    y += DIR[orientation][1];
                    String newCoordinate = new String(x + "_" + y);
                    if (visited.contains(newCoordinate)) {
                        return Math.abs(x) + Math.abs(y);
                    }
                    visited.add(newCoordinate);
                }
            }
        }
        return Math.abs(x) + Math.abs(y);
    }

    private static int calculateOrientation(String turn, int orientation) {
        orientation = turn.equals("R") ? orientation + 1 : orientation - 1;

        if (orientation >= 4) {
            orientation -= 4;
        }
        else if (orientation < 0) {
            orientation += 4;
        }
        return orientation;
    }

    private static String[] parse (List<String> input) {
        return input.get(0).split(",\\s+");
    }
}
