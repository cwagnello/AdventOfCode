package com.cwagnello;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        Map<String, Integer> properties = Map.of(
                "children", 3,
                "cats", 7,
                "samoyeds", 2,
                "pomeranians", 3,
                "akitas", 0,
                "vizslas", 0,
                "goldfish", 5,
                "trees", 3,
                "cars", 2,
                "perfumes", 1);

        AuntSue mysteryAunt = new AuntSue(-1, properties);
        List<AuntSue> aunts = parse(input);
        System.out.println("2015 Day16 part1: " + part1(aunts, mysteryAunt));
        System.out.println("2015 Day16 part2: " + part2(aunts, mysteryAunt));
    }

    private static int part1(List<AuntSue> aunts, AuntSue mysteryAunt) {
        for (AuntSue aunt : aunts) {
            if (propertiesMatch(mysteryAunt, aunt)) {
                return aunt.index();
            }
        }
        return -1;
    }

    private static int part2(List<AuntSue> aunts, AuntSue mysteryAunt) {
        for (AuntSue aunt : aunts) {
            boolean matchFound = true;
            for (String property : aunt.keys()) {
                matchFound = switch (property) {
                    case "cats", "trees" -> aunt.get(property) > mysteryAunt.get(property);
                    case "pomeranians", "goldfish" -> aunt.get(property) < mysteryAunt.get(property);
                    default -> aunt.get(property) == mysteryAunt.get(property);
                };
                if (!matchFound) {
                    break;
                }
            }
            if (matchFound) {
                return aunt.index();
            }
        }
        return -1;
    }

    private static boolean propertiesMatch(AuntSue mysteryAunt, AuntSue aunt) {
        for (String property : aunt.keys()){
            if (mysteryAunt.get(property) != aunt.get(property)) {
                return false;
            }
        }
        return true;
    }

    private static List<AuntSue> parse(List<String> input) {
        List<AuntSue> aunts = new ArrayList<>();
        Pattern pattern = Pattern.compile("^Sue\\s+(\\d+):(.*)$");
        Pattern propertyPattern = Pattern.compile("(\\w+):\\s+(\\d+)");
        for (String line : input) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                int index = Integer.parseInt(matcher.group(1));
                Map<String, Integer> properties = new HashMap<>();

                String[] parts = matcher.group(2).split(",");
                for (String part : parts) {
                    Matcher propertyMatcher = propertyPattern.matcher(part);
                    if (propertyMatcher.find()) {
                        properties.put(propertyMatcher.group(1), Integer.parseInt(propertyMatcher.group(2)));
                    }
                }
                AuntSue auntSue = new AuntSue(index, properties);
                aunts.add(auntSue);
            }
        }
        return aunts;
    }
}