package com.cwagnello.aoc2024.day13;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Solution {
    public static void main(String[] args) {
        String file = "src/main/resources/input.txt";
        String input = readInput(file);
        log.info("2024 day 12 part 1: {}", part1(input));
        log.info("2024 day 12 part 2: {}", part2(input));
    }

    private static long part1(String input) {
        List<Prize> prizes = parse(input);
        long sum = 0;
        for (Prize prize: prizes) {
            prize.solve();
            sum += prize.minimumTokens();
        }
        return sum;
    }

    private static long part2(String input) {
        long offset = 10000000000000L;
        List<Prize> prizes = parse(input);
        long sum = 0;
        for (Prize prize: prizes) {
            prize.setLocation(new Coordinate(prize.getLocation().x() + offset, prize.getLocation().y() + offset));
            prize.solve();
            sum += prize.minimumTokens();
        }
        return sum;
    }

    private static List<Prize> parse(String input) {
        List<Prize> prizes = new ArrayList<>();
        String[] blocks = input.split("\\n\\n");
        Pattern pattern = Pattern.compile("X[+=](\\d+).*?Y[+=](\\d+)");
        for (String block: blocks) {
            String[] lines = block.split("\\n");

            Prize.PrizeBuilder builder = Prize.builder();
            Matcher matcher = pattern.matcher(lines[0]);
            if (matcher.find()) {
                builder.buttonA(new Coordinate(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))));
            }

            matcher = pattern.matcher(lines[1]);
            if (matcher.find()) {
                builder.buttonB(new Coordinate(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))));
            }

            matcher = pattern.matcher(lines[2]);
            if (matcher.find()) {
                builder.location(new Coordinate(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))));
            }
            prizes.add(builder.build());
        }
        return prizes;
    }

    public static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    private static String readInput(String file) {
        String input = null;
        try {
            input = readFile(file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("Error reading file", e);
        }
        return input;
    }

}
