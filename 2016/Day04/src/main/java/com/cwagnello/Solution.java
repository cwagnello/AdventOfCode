package com.cwagnello;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
        List<Room> rooms = parse(input);
        System.out.println("2016 day 4 part 1: " + part1(rooms));
        System.out.println("2016 day 4 part 2: " + part2(rooms));
    }

    private static long part1(List<Room> rooms) {
        long sum = 0;
        for (Room room: rooms) {
            if (room.isValid()) {
                sum += room.getSectorId();
            }
        }
        return sum;
    }

    private static long part2(List<Room> rooms) {
        for (Room room : rooms) {
            long rotations = room.getSectorId();
            String[] words = room.getEncryptedName().split("-");
            for (String word : words) {
                StringBuilder sb = new StringBuilder();
                for (char c : word.toCharArray()) {
                    long index = c - 'a';
                    index += room.getSectorId();
                    sb.append((char)(index % 26 + 'a'));
                }
                if (sb.toString().contains("north")) {
                    return room.getSectorId();
                }
            }
        }
        return -1;
    }

    private static List<Room> parse(List<String> input) {
        return input.stream().map(Room::new).toList();
    }
}
