package com.cwagnello;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        System.out.println("Day 18 - Part1: " + part1(input));
        System.out.println("Day 18 - Part2: " + part2(input));
    }

    private static long part1(List<String> input) {
        //U 2 (#7a21e3)
        Pattern pattern = Pattern.compile("(\\w)\\s(\\d+)\\s\\(#(.{6})\\)");
        List<DigPlan> plans = new ArrayList<>();
        for (String plan : input) {
            Matcher matcher = pattern.matcher(plan);
            if (matcher.matches()) {
                plans.add(new DigPlan(matcher.group(1), Integer.parseInt(matcher.group(2)), matcher.group(3)));
            }
        }
        int row = 0;
        int col = 0;
        List<Pair> pairs = new ArrayList<>();
        pairs.add(new Pair(0, 0));
        for (DigPlan plan: plans) {
            switch (plan.direction) {
                case "R": col += plan.distance;
                    break;
                case "L": col -= plan.distance;
                    break;
                case "D": row += plan.distance;
                    break;
                case "U": row -= plan.distance;
                    break;
            }
            pairs.add(new Pair(row, col));
        }

        return calculateArea(pairs);
    }

    private static long part2(List<String> input) {
        Pattern pattern = Pattern.compile("(\\w)\\s(\\d+)\\s\\(#(.{6})\\)");
        long sum = 0;
        List<DigPlan> plans = new ArrayList<>();
        for (String plan : input) {
            Matcher matcher = pattern.matcher(plan);
            if (matcher.matches()) {
                plans.add(new DigPlan(matcher.group(1), Integer.parseInt(matcher.group(2)), matcher.group(3)));
            }
        }
        int row = 0;
        int col = 0;
        List<Pair> pairs = new ArrayList<>();
        pairs.add(new Pair(0, 0));
        for (DigPlan plan : plans) {
            int distance = Integer.parseInt(plan.color.substring(0,5), 16);
            switch (plan.color.substring(5,6)) {
                //0 means R, 1 means D, 2 means L, and 3 means U.
                case "0": col += distance;
                    break;
                case "1": row += distance;
                    break;
                case "2": col -= distance;
                    break;
                case "3": row -= distance;
                    break;
            }
            pairs.add(new Pair(row, col));
        }
        return calculateArea(pairs);
    }

    // Calculate area of a polygon specified by consecutive coordinates
    private static long calculateArea(List<Pair> pairs) {
        long sum = 0;
        long perimeter = 0;
        for (int i = 1; i < pairs.size(); i++) {
            long x0 = pairs.get(i - 1).col;
            long x1 = pairs.get(i).col;
            long y0 = pairs.get(i - 1).row;
            long y1 = pairs.get(i).row;
            long dx = Math.abs(x1 - x0);
            long dy = Math.abs(y1 - y0);
            perimeter += dx + dy;
            sum += (x1 - x0) * (y1 + y0);
        }
        sum /= 2;
        return Math.abs(sum) - perimeter / 2 + 1 + perimeter;
    }
}

class DigPlan {
    String direction;
    int distance;
    String color;
    public DigPlan(String direction, int distance, String color) {
        this.direction = direction;
        this.distance = distance;
        this.color = color;
    }
}

class Pair {
    int row;
    int col;
    public Pair(int row, int col) {
        this.row = row;
        this.col = col;
    }
}