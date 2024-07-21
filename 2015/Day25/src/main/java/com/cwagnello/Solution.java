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

        Pair location = parse(input);

        System.out.println("2015 Day 25 part 1: " + part1(location));
        //System.out.println("2015 Day 25 part 2: " + part2(weights));
    }

    private static Pair parse(List<String> input) {
        Pattern pattern = Pattern.compile(".*?row\\s+(\\d+).*?column\\s+(\\d+)");
        Matcher matcher = pattern.matcher(input.get(0));
        if (matcher.find()) {
            long row = Long.parseLong(matcher.group(1));
            long column = Long.parseLong(matcher.group(2));
            return new Pair(row, column);
        }
        throw new IllegalStateException("Unable to parse input");
    }

    private static long part1(Pair location) {
        long n = location.row() + (location.column() - 1) - 1;
        long index = n * (n + 1) / 2 + location.column();
        long product = 20151125;
        long multiplicand = 252533;
        long dividend = 33554393;
        for (long i = 1; i < index; i++) {
            product *= multiplicand;
            long mod = product % dividend;
            product = mod;
        }
        return product;
    }
}
