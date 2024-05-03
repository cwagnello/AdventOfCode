package com.cwagnello;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;


public class Solution {
    public static void main (String[] args) {
        File file = new File("src/com/cwagnello/test.txt");
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

        System.out.println("Day12 - Part1: " + part1(input));
        System.out.println("Day12 - Part2: " + part2(input));
    }


    private static List<Record> parseInput(List<String> input) {
        List<Record> records = new ArrayList<>();
        for (String s : input) {
            String[] parts = s.split("\\s+");
            records.add(new Record(parts[0], parts[1]));
        }
        return records;
    }

    private static long part2(List<String> input) {
        int sum = 0;
        int countQs = 0;
        List<Record> records = parseInput(input);
        for (Record record : records) {
            record.setMap(record.getMap() + "?" +record.getMap());
            record.setSpringLocations(record.getSpringLocations() + "," +record.getSpringLocations());

            ValidMapRecord.init(record.getSpringLocations());

            countQs = countQuestionMarks(record.getMap());

            for (int i = 0; i < Math.pow(2, countQs); i++) {
                char[] springMap = record.getMap().toCharArray();
                int bitMask = i;
                for (int j = 0; j < springMap.length; j++) {
                    if (springMap[j] == '?') {
                        springMap[j] = bitMask % 2 == 0 ? '.' : '#';
                        bitMask /= 2;
                    }
                }
                if (ValidMapRecord.matches(new String(springMap))) {
                    sum++;
                }
            }
        }
        return sum;
    }

    private static long part1(List<String> input) {
        int sum = 0;
        int countQs = 0;
        List<Record> records = parseInput(input);
        for (Record record : records) {
            ValidMapRecord.init(record.getSpringLocations());

            countQs = countQuestionMarks(record.getMap());

            for (int i = 0; i < Math.pow(2, countQs); i++) {
                char[] springMap = record.getMap().toCharArray();
                int bitMask = i;
                for (int j = 0; j < springMap.length; j++) {
                    if (springMap[j] == '?') {
                        springMap[j] = bitMask % 2 == 0 ? '.' : '#';
                        bitMask /= 2;
                    }
                }
                if (ValidMapRecord.matches(new String(springMap))) {
                    sum++;
                }
            }
        }
        return sum;
    }

    private static int countQuestionMarks(String input) {
        int count = 0;
        for (char c: input.toCharArray()) {
            if (c == '?') {
                count++;
            }
        }
        return count;
    }
}


class Record {
    private String map;
    private String springLocations;
    public Record(String map, String springLocations) {
        this.setMap(map);
        this.setSpringLocations(springLocations);
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getSpringLocations() {
        return springLocations;
    }

    public void setSpringLocations(String springLocations) {
        this.springLocations = springLocations;
    }
}

class ValidMapRecord {
    private static Pattern regExp;

    public static void init (String locations) {
        regExp = buildRegexp(locations);
    }


    /**
     * ?###???????? 3,2,1
     * \\.*?#{3}\\.+?#{2}\\.+?#{1}\\.*?
     *
     */
    private static Pattern buildRegexp(String input) {
        String[] locations = input.split(",");
        StringBuilder sb = new StringBuilder();
        sb.append("\\.*");
        for (String location : locations) {
            sb.append("#{");
            sb.append(location);
            sb.append("}");
            sb.append("\\.+");
        }
        sb.replace(sb.lastIndexOf("+"), sb.length(), "*");
        return Pattern.compile(sb.toString());
    }

    public static boolean matches(String input) {
        return regExp.matcher(input).matches();
    }
}