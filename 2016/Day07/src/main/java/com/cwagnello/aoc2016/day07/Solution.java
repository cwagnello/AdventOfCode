package com.cwagnello.aoc2016.day07;

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

        List<Ipv7> ipv7Addresses = parse(input);
        System.out.println("2016 day 7 part 1: " + part1(ipv7Addresses));
        System.out.println("2016 day 7 part 2: " + part2(ipv7Addresses));
    }

    private static int part1(List<Ipv7> ipv7Addresses) {
        int count = 0;

        for (Ipv7 ipv7Address : ipv7Addresses) {
            if (ipv7Address.supportsTls()) {
                count++;
            }
        }
        return count;
    }

    private static int part2(List<Ipv7> ipv7Addresses) {
        int count = 0;
        for (Ipv7 ipv7Address: ipv7Addresses) {
            if (ipv7Address.supportsSsl()) {
                count++;
            }
        }
        return count;
    }

    private static List<Ipv7> parse(List<String> input) {
        return input.stream().map(Ipv7::new).toList();
    }
}
