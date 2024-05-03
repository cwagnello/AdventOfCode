package com.cwagnello;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(new File("input.txt")).useDelimiter(Pattern.compile("\\n\\n"))) {
            List<String> groups = new ArrayList<>();

            while (sc.hasNext()) {
                String record = sc.next().replaceAll("\\n", " ");
                groups.add(record);
            }
            part1(groups);
            part2(groups);
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private static void part1(List<String> groups) {
        int sum = 0;
        for (String group: groups) {
            Set<Character> chars = new HashSet<>();
            for (char c: group.replaceAll("\\s+", "").toCharArray()) {
                chars.add(c);
            }
            sum += chars.size();
        }
        System.out.println(sum);
    }

    private static void part2(List<String> groups) {
        int sum = 0;
        for (String group: groups) {
            Map<Character, Integer> frequency = new HashMap<>();

            for (char c: group.replaceAll("\\s+", "").toCharArray()) {
                frequency.put(c, frequency.getOrDefault(c, 0) + 1);
            }
            String[] people = group.split("\\s+");
            //System.out.println("Number in group: " + people.length);
            for (char c: frequency.keySet()) {
                //System.out.println("char: " + c + ", count: " + frequency.get(c));
                if (frequency.get(c) == people.length) {
                    sum++;
                }
            }
            //System.out.println("Sum: " + sum);
        }
        System.out.println(sum);
    }

}
