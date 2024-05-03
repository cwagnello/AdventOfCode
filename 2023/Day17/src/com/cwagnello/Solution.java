package com.cwagnello;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        File file = new File("src/com/cwagnello/test.txt");
        List<String> input = new ArrayList<>();

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                //assume input data is well formed
                input.add(line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        int[][] grid = new int[input.size()][input.get(0).length()];
        int row = 0;
        for (String line: input) {
            char[] c = line.toCharArray();
            for (int col = 0; col < line.length(); col++) {
                grid[row][col] = c[col] - '0';
            }
            row++;
        }

        System.out.println("Day 17 - Part1: " + part1(grid));
    }

    private static int part1(int[][] grid) {
        return 0;
    }
}
