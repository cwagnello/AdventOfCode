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

        List<Ingredient> ingredients = parse(input);
        System.out.println("2015 Day15 part1: " + part1(ingredients));
        System.out.println("2015 Day15 part2: " + part2(ingredients));
    }

    private static int part1(List<Ingredient> ingredients) {
        int maxScore = 0;
        for (int i = 0; i <= 100; i++) {
            for (int j = 0; j <= 100 - i; j++) {
                for (int k = 0; k <= 100 - i - j; k++) {
                    for (int m = 0; m <= 100 - i - j - k; m++) {
                        if (i + j + k + m == 100) {
                            int totalScore = 1;
                            int capacity = ingredients.get(0).capacity() * i + ingredients.get(1).capacity() * j + ingredients.get(2).capacity() * k + ingredients.get(3).capacity() * m;
                            int durability = ingredients.get(0).durability() * i + ingredients.get(1).durability() * j + ingredients.get(2).durability() * k + ingredients.get(3).durability() * m;
                            int flavor = ingredients.get(0).flavor() * i + ingredients.get(1).flavor() * j + ingredients.get(2).flavor() * k + ingredients.get(3).flavor() * m;
                            int texture = ingredients.get(0).texture() * i + ingredients.get(1).texture() * j + ingredients.get(2).texture() * k + ingredients.get(3).texture() * m;
                            totalScore *= Math.max(0, capacity);
                            totalScore *= Math.max(0, durability);
                            totalScore *= Math.max(0, flavor);
                            totalScore *= Math.max(0, texture);
                            maxScore = Math.max(maxScore, totalScore);
                        }
                    }
                }
            }
        }
        return maxScore;
    }

    private static int part2(List<Ingredient> ingredients) {
        int maxScore = 0;
        for (int i1 = 0; i1 <= 100; i1++) {
            for (int i2 = 0; i2 <= 100 - i1; i2++) {
                for (int i3 = 0; i3 <= 100 - i1 - i2; i3++) {
                    for (int i4 = 0; i4 <= 100 - i1 - i2 - i3; i4++) {
                        if (i1 + i2 + i3 + i4 == 100) {
                            int calories = ingredients.get(0).calories() * i1 + ingredients.get(1).calories() * i2 + ingredients.get(2).calories() * i3 + ingredients.get(3).calories() * i4;
                            if (calories == 500) {
                                int totalScore = 1;
                                int capacity = ingredients.get(0).capacity() * i1 + ingredients.get(1).capacity() * i2 + ingredients.get(2).capacity() * i3 + ingredients.get(3).capacity() * i4;
                                int durability = ingredients.get(0).durability() * i1 + ingredients.get(1).durability() * i2 + ingredients.get(2).durability() * i3 + ingredients.get(3).durability() * i4;
                                int flavor = ingredients.get(0).flavor() * i1 + ingredients.get(1).flavor() * i2 + ingredients.get(2).flavor() * i3 + ingredients.get(3).flavor() * i4;
                                int texture = ingredients.get(0).texture() * i1 + ingredients.get(1).texture() * i2 + ingredients.get(2).texture() * i3 + ingredients.get(3).texture() * i4;
                                totalScore *= Math.max(0, capacity);
                                totalScore *= Math.max(0, durability);
                                totalScore *= Math.max(0, flavor);
                                totalScore *= Math.max(0, texture);

                                maxScore = Math.max(maxScore, totalScore);
                            }
                        }
                    }
                }
            }
        }
        return maxScore;
    }

    private static List<Ingredient> parse(List<String> input) {
        List<Ingredient> ingredients = new ArrayList<>();
        Pattern pattern = Pattern.compile("^(\\w+):.*?(-*\\d+).*?(-*\\d+).*?(-*\\d+).*?(-*\\d+).*?(-*\\d+)$");
        for (String line : input) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                String name = matcher.group(1);
                int capacity = Integer.parseInt(matcher.group(2));
                int durability = Integer.parseInt(matcher.group(3));
                int flavor = Integer.parseInt(matcher.group(4));
                int texture = Integer.parseInt(matcher.group(5));
                int calories = Integer.parseInt(matcher.group(6));
                ingredients.add(new Ingredient(name, capacity, durability, flavor, texture, calories));
            }
        }
        return ingredients;
    }
}
