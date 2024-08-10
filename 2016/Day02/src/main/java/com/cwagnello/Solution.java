package com.cwagnello;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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
        System.out.println("2016 Day 02 part 1: " + part1(parse(input)));
        System.out.println("2016 Day 02 part 2: " + part2(parse(input)));
    }

    private static int part1(List<List<Direction>> instructions) {
        int code = 0;
        Keypad1 keypad = Keypad1.getKeypad();
        for (List<Direction> keyPresses: instructions) {
            keyPresses.forEach(keypad::apply);
            int digit = keypad.getValue();
            code *= 10;
            code += digit;
        }
        return code;
    }

    private static String part2(List<List<Direction>> instructions) {
        StringBuilder sb = new StringBuilder();
        Keypad2 keypad = Keypad2.getKeypad();
        for (List<Direction> keyPresses: instructions) {
            keyPresses.forEach(keypad::apply);
            sb.append(keypad.getValue());
        }

        return sb.toString();
    }

    private static List<List<Direction>> parse(List<String> input) {
        List<List<Direction>> instructions = new ArrayList<>();
        for (String line: input) {
            instructions.add(Arrays.stream(line.split("")).map(Direction::value).toList());
        }
        return instructions;
    }
}
