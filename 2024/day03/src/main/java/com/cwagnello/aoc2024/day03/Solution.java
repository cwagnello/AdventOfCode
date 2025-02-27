package com.cwagnello.aoc2024.day03;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Solution {
    public static void main(String[] args) {

        String input = readInput();
        log.info("2024 day 2 part 1: {}", part1(input));
        log.info("2024 day 2 part 2: {}", part2(input));
    }

    private static long part1(String input) {
        long sum = 0;
        Pattern pattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            long a = Long.parseLong(matcher.group(1));
            long b = Long.parseLong(matcher.group(2));
            sum += a * b;
        }
        return sum;
    }

    private static long part2(String input) {
        Pattern pattern = Pattern.compile("don't\\(\\).*?do\\(\\)", Pattern.DOTALL);
        return part1(String.join("", pattern.split(input)));
    }

    private static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    private static String readInput() {
        String file = "src/main/resources/input.txt";
        String input = null;
        try {
            input = readFile(file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("Error reading input file", e);
        }
        return input;
    }

}
