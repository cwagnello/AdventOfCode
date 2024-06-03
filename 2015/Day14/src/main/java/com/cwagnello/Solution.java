package com.cwagnello;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution {
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
        List<Reindeer> reindeer = parse(input);
//        System.out.println("2015 Day 14 part 1: " + part1(reindeer, 1000));
//        System.out.println("2015 Day 14 part 2: " + part2(reindeer, 1000));
        System.out.println("2015 Day 14 part 1: " + part1(reindeer, 2503));
        System.out.println("2015 Day 14 part 2: " + part2(reindeer, 2503));
    }

    /*
        distance = velocity * time
    */
    private static int part1(List<Reindeer> reindeer, int totalTime) {
        int maxDistance = 0;
        for (Reindeer deer : reindeer) {
            maxDistance = Math.max(maxDistance, distanceTraveled(totalTime, deer));
        }
        return maxDistance;
    }

    private static int part2(List<Reindeer> reindeer, int totalTime) {
        int maxPoints = 0;
        int[] distances = new int[reindeer.size()];
        int[] points = new int[reindeer.size()];

        for (int iteration = 1; iteration <= totalTime; iteration++) {
            int deerIndex = 0;
            List<Integer> maxIndex =  new ArrayList<>();
            int max = 0;
            for (Reindeer deer : reindeer) {
                distances[deerIndex] = distanceTraveled(iteration, deer);
                if (distances[deerIndex] > max) {
                    maxIndex.clear();
                    maxIndex.add(deerIndex);
                    max = distances[deerIndex];
                }
                else if (distances[deerIndex] == max) {
                    maxIndex.add(deerIndex);
                }
                deerIndex++;
            }
            for (int index : maxIndex) {
                points[index]++;
                maxPoints = Math.max(maxPoints, points[index]);
            }
        }
        return maxPoints;
    }

    private static int distanceTraveled(int totalTime, Reindeer deer) {
        int onePeriod = deer.duration() + deer.rest();
        int iterations = totalTime / onePeriod;
        int deerDistance = iterations * deer.velocity() * deer.duration();
        int remainingTime = totalTime - iterations * onePeriod;
        if (remainingTime > deer.duration()) {
            deerDistance += deer.velocity() * deer.duration();
        }
        else {
            deerDistance += deer.velocity() * remainingTime;
        }
        //System.out.println(String.format("%s -> iterations: %d, distance: %d", deer.name(), iterations, deerDistance));

        return deerDistance;
    }

    private static List<Reindeer> parse(List<String> input) {
        Pattern pattern = Pattern.compile("^(\\w+).*?(\\d+).*?(\\d+).*?(\\d+)");
        List<Reindeer> reindeer = new ArrayList<>();
        for (String line : input) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                String name = matcher.group(1);
                int velocity = Integer.parseInt(matcher.group(2));
                int duration = Integer.parseInt(matcher.group(3));
                int rest = Integer.parseInt(matcher.group(4));
                reindeer.add(new Reindeer(name, velocity, duration, rest));
            }
        }
        return reindeer;
    }
}
