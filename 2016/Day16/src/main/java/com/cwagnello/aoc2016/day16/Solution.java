package com.cwagnello.aoc2016.day16;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
        InputData inputData = parse(input);
        System.out.println("2016 day 16 part 1: " + part1(inputData));
        System.out.println("2016 day 16 part 2: " + part2(inputData));
    }

    private static String part1(InputData inputData) {
        String dragonCurve = calculateDragonCurve(inputData);
        return checkSum(dragonCurve);
    }

    private static String part2(InputData inputData) {
        inputData = new InputData(inputData.start(), 35651584);
        String dragonCurve = calculateDragonCurve(inputData);
        return checkSum(dragonCurve);
    }

    private static String calculateDragonCurve(InputData inputData) {
        StringBuilder a = new StringBuilder(inputData.start());
        while (a.length() < inputData.diskLength()) {
            StringBuilder b = new StringBuilder(a);
            b.reverse();
            a.append("0");
            for (int i = 0; i < b.length(); i++) {
                a.append(b.charAt(i) == '0' ? "1" : "0");
            }
        }
        return a.substring(0, inputData.diskLength());
    }

    private static String checkSum(String dragonCurve) {
        String test = dragonCurve;
        StringBuilder checkSum = new StringBuilder();
        while (test.length() % 2 == 0) {
            for (int i = 0; i < test.length(); i += 2) {
                if (test.charAt(i) == test.charAt(i + 1)) {
                    checkSum.append("1");
                } else {
                    checkSum.append("0");
                }
            }
            test = checkSum.toString();
            checkSum = new StringBuilder();
        }
        return test;
    }

    private static InputData parse(List<String> input) {
        return new InputData(input.get(0), Integer.parseInt(input.get(1)));
    }
}
