package com.cwagnello.aoc2016.day08;

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
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Screen screen = new Screen(50, 6);
        System.out.println("2016 day 8 part 1: " + part1(input, screen));
        System.out.println("2016 day 8 part 2: ");
        screen.print();
    }

    private static int part1(List<String> input, Screen screen) {
        for (Instruction instruction: Parser.getInstance().parse(input)) {
            instruction.apply(screen);
        }
        return screen.countOnPixels();
    }
}
