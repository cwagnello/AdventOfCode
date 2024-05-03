package com.cwagnello;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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

        System.out.println("Part1: " + part1(input));
        System.out.println("Part2: " + part2(input));
    }


    private static int part1(List<String> input) {
        List<Integer> time = Arrays.stream(input.get(0).split("\\s+")).filter(n -> !n.isBlank()).filter(n -> !n.matches("\\D+")).map(Integer::parseInt).collect(Collectors.toList());
        List<Integer> distance = Arrays.stream(input.get(1).split("\\s+")).filter(n -> !n.isBlank()).filter(n -> !n.matches("\\D+")).map(Integer::parseInt).collect(Collectors.toList());

        List<Race> raceData = new ArrayList<>();
        for (int i = 0; i < time.size(); i++) {
            raceData.add(new Race(time.get(i), distance.get(i)));
        }
        int result = 1;
        for (Race race : raceData) {
            List<Integer> results = new ArrayList<>();
            for (int i = 1; i < race.getTime(); i++) {
                int myDistance = (race.getTime() - i) * i;
                if (myDistance > race.getDistance()) {
                    results.add(myDistance);
                }
            }
            result *= results.size();
        }
        return result;
    }

    private static long part2(List<String> input) {
        List<String> timeData = Arrays.stream(input.get(0).split("\\s+")).filter(n -> !n.isBlank()).filter(n -> !n.matches("\\D+")).collect(Collectors.toList());
        List<String> distanceData = Arrays.stream(input.get(1).split("\\s+")).filter(n -> !n.isBlank()).filter(n -> !n.matches("\\D+")).collect(Collectors.toList());
        long time = Long.parseLong(String.join("", timeData));
        long distance = Long.parseLong(String.join("", distanceData));
        long start = 0, end = time / 2, middle = (start + end) / 2;

//        long max = max(time, distance);

//        System.out.println("Max = " + max + ", " + (time - max) * max);
//        System.out.println("Left = " + (time - (max - 1)) * (max - 1));
//        System.out.println("Right = " + (time - (max + 1)) * (max + 1));
        // Find the start time
        while (start <= end) {
            middle = (start + end) / 2;
            long myDistance = (time - middle) * middle;
            if (myDistance == distance) {
                return middle;
            }
            else if (myDistance < distance) {
                start = middle + 1;
            }
            else {
                end = middle - 1;
            }
        }

        return (time - middle) - middle + 1;
    }

    private static long max(long time, long distance) {
        long left = 0;
        long right = time;

        while (left < right) {
            System.out.println("Left: " + left + ", Right: " + right);

            long leftThird = left + (right - left) / 3;
            long rightThird = right - (right - left) / 3;

            long distanceLeft = (time - leftThird) * leftThird;
            long distanceRight = (time - rightThird) * rightThird;

            if (distanceLeft < distanceRight) {
                left = leftThird + 1;
            }
            else {
                right = rightThird - 1;
            }
        }
        return (left + right) / 2;
    }
}

class Race {
    private int time;
    private int distance;

    public Race(int time, int distance) {
        this.setTime(time);
        this.setDistance(distance);
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Race{" +
                "time=" + time +
                ", distance=" + distance +
                '}';
    }
}