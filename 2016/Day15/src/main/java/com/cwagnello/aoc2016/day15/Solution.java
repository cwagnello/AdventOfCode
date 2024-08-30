package com.cwagnello.aoc2016.day15;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution {
    public static void main(String[] args) {
        File file = new File("src/main/resources/input.txt");
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

        List<Integer> positions = new ArrayList<>();
        List<Integer> offsets = new ArrayList<>();

        Pattern pattern = Pattern.compile("(\\d+)\\s+positions.*?position\\s+(\\d+)");
        for (String line : input) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                int position = Integer.parseInt(matcher.group(1));
                int offset = Integer.parseInt(matcher.group(2));
                positions.add(position);
                offsets.add(offset);
            }
        }
        System.out.println("2016 day 15 part 1: " + part1(positions, offsets));
        System.out.println("2016 day 15 part 2: " + part2(positions, offsets));
    }

    private static long part1(List<Integer> positions, List<Integer> offsets) {
        return simulate(positions, offsets);
    }

    private static long part2(List<Integer> positions, List<Integer> offsets) {
        positions.add(11);
        offsets.add(0);
        return simulate(positions, offsets);
    }

    private static long simulate(List<Integer> positions, List<Integer> offsets) {
        long time = 0;
        while (time < maxTime(positions)) {
            boolean isValid = true;
            for (int i = 0; i < positions.size(); i++) {
                if ((time + 1 + i + offsets.get(i)) % positions.get(i) != 0) {
                    isValid = false;
                    break;
                }
            }
            if (isValid) {
                return time;
            }
            time++;
        }
        return -1;
    }

    private static long maxTime (List<Integer> positions) {
        return positions.stream().reduce((a, b) -> a * b).get();
    }

}