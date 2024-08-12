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
        List<Triangle> triangles = parse(input);
        System.out.println("2016 Day 3 part 1: " + part1(triangles));

        triangles = parse2(input);
        System.out.println("2016 Day 3 part 2: " + part1(triangles));
    }

    private static int part1(List<Triangle> triangles) {
        int count = 0;
        for (Triangle triangle: triangles) {
            if (triangle.isValid()) {
                count++;
            }
        }
        return count;
    }

    private static List<Triangle> parse(List<String> input) {
        List<Triangle> triangles = new ArrayList<>();
        for (String line: input) {
            String[] sides = line.trim().split("\\s+");
            triangles.add(new Triangle(Integer.parseInt(sides[0]), Integer.parseInt(sides[1]), Integer.parseInt(sides[2])));
        }
        return triangles;
    }

    private static List<Triangle> parse2(List<String> input) {
        List<Triangle> triangles = new ArrayList<>();
        for (int i = 0; i < input.size(); i += 3) {
            String[] sides1 = input.get(i).trim().split("\\s+");
            String[] sides2 = input.get(i + 1).trim().split("\\s+");
            String[] sides3 = input.get(i + 2).trim().split("\\s+");
            triangles.add(new Triangle(Integer.parseInt(sides1[0]), Integer.parseInt(sides2[0]), Integer.parseInt(sides3[0])));
            triangles.add(new Triangle(Integer.parseInt(sides1[1]), Integer.parseInt(sides2[1]), Integer.parseInt(sides3[1])));
            triangles.add(new Triangle(Integer.parseInt(sides1[2]), Integer.parseInt(sides2[2]), Integer.parseInt(sides3[2])));
        }
        return triangles;
    }
}
