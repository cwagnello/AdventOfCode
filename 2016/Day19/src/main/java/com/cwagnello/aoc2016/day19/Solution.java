package com.cwagnello.aoc2016.day19;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        List<String> input = readInputFile("src/main/resources/input.txt");
        int numberOfElves = Integer.parseInt(parse(input));
        //int numberOfElves = 6562;
        Elf head = createCircularBuffer(numberOfElves);
        System.out.println("2016 day 18 part 1: " + part1(numberOfElves, head.getLeft()));
        //head = createCircularBuffer(numberOfElves);
        System.out.println("2016 day 18 part 2: " + part2(numberOfElves, head.getLeft()));
    }

    private static int part1(int numberOfElves, Elf firstElf) {
        Elf current = firstElf;

        while (numberOfElves > 1) {
            current.setLeft(current.getLeft().getLeft());
            current = current.getLeft();
            numberOfElves--;
        }
        return current.getId();
    }

    private static int part2(int numberOfElves, Elf firstElf) {
        int powersOfThree = 1;

        while (numberOfElves - powersOfThree > 0) {
            powersOfThree *= 3;
        }
        powersOfThree /= 3;

        return numberOfElves - powersOfThree;

//Brute force implementation, keeping it just for the sake of it.
//        Elf current = firstElf;
//
//        while (numberOfElves > 1) {
//            Elf next = current.getLeft();
//            int offset = numberOfElves / 2 - 1;
////            System.out.printf("Offset: %d%n", offset);
//            while (offset > 0) {
//                current = current.getLeft();
//                offset--;
//            }
////            System.out.printf("Current elf: %d, next available elf: %d%n", current.getId(), current.getLeft().getLeft().getId());
////            System.out.printf("Removing elf: %d%n%n", current.getLeft().getId());
//            current.setLeft(current.getLeft().getLeft());
//            current = next;
//            numberOfElves--;
//        }
//        return current.getId();
    }

    private static Elf createCircularBuffer(int numberOfElves) {
        Elf current = new Elf(0, null);
        Elf head = current;
        Elf left = null;

        for (int i = 1; i <= numberOfElves; i++) {
            left = new Elf(i, null);
            current.setLeft(left);
            current = left;
        }
        //Make linked list circular, aka last elf points to first elf
        current.setLeft(head.getLeft());
        return head;
    }

    private static String parse(List<String> input) {
        return input.getFirst();
    }

    private static List<String> readInputFile(final String relativeFilePath) {
        File file = new File(relativeFilePath);
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
