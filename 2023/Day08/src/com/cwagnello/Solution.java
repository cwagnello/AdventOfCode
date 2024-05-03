package com.cwagnello;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution {
    public static void main(String[] args) {
        File file = new File("src/com/cwagnello/input.txt");
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
        Pattern pattern = Pattern.compile("(\\w{3}) = \\((\\w{3}), (\\w{3})\\)");
        String sequence = input.get(0);
        Map<String, List<String>> graph = new HashMap<>();
        Set<String> endsInA = new HashSet<>();

        for (int i = 2; i < input.size(); i++) {
            Matcher matcher = pattern.matcher(input.get(i));
            if (matcher.find()) {
                String value = matcher.group(1);
                String left = matcher.group(2);
                String right = matcher.group(3);
                graph.put(value, List.of(left, right));

                if (value.endsWith("A")) {
                    endsInA.add(value);
                }
            }
        }
        System.out.println("Part1: " + process(sequence, "AAA", "ZZZ", graph));

        long prev = 1;
        long gcd = 1;
        List<Long> numbers = new ArrayList<>();
        for (String a: endsInA) {
            long next = process(sequence, a, "Z", graph);
            numbers.add(next);
            //System.out.println("Part2 (" + a + "): " + next);
            gcd = gcd(next, prev);
            prev = next;
        }
        long lcm = 1;
        for (int i = 0; i < numbers.size(); i++) {
            lcm *= numbers.get(i);
            lcm /= gcd;
        }
        System.out.println("Part2: " + lcm * gcd);
    }

    private static long gcd(long a, long b) {
        while (b != 0) {
            long t = a;
            a = b;
            b = t % b;
        }
        return a;
    }

    private static long process(String sequence, String start, String end, Map<String, List<String>> graph) {
        long increment = 0;
        long count = 1;
        String current = start;
        while (true) {
            int index = (int)(increment % sequence.length());
            current = switch (sequence.charAt(index)) {
                case 'L' -> graph.get(current).get(0);
                case 'R' -> graph.get(current).get(1);
                default -> throw new IllegalArgumentException("Sequence choice invalid: " + sequence.charAt(index));
            };
            if (current.endsWith(end)) {
                break;
            }
            count++;
            increment++;
        }
        return count;
    }
}

