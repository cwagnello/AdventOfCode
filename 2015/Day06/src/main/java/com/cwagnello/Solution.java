package com.cwagnello;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cwagnello
 */
public class Solution {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        List<Instruction> instructions = new ArrayList<>();
        String filename = "src/main/resources/input.txt";
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = br.readLine()) != null) {
                instructions.add(new Instruction(line));
            }
        }
        catch (FileNotFoundException ex) {
            System.out.println("File not found: " + ex);
        }
        catch (IOException ex) {
            System.out.println("Error reading file: " + ex);
        }

        System.out.println("2015 Day06 part1: " + part1(instructions));
        System.out.println("2015 Day06 part2: " + part2(instructions));
    }

    private static int part1(List<Instruction> instructions) {
        return process(instructions, true);
    }

    private static int part2(List<Instruction> instructions) {
        return process(instructions, false);
    }
    private static int process(List<Instruction> instructions, boolean isPart1) {
        int[][] lights = new int[1000][1000];

        for (Instruction instruction: instructions) {
            switch (instruction.getCommand()) {
                case "toggle" -> toggle(lights, instruction.getP1(), instruction.getP2(), isPart1);
                case "turn on" -> turnOn(lights, instruction.getP1(), instruction.getP2(), isPart1);
                case "turn off" -> turnOff(lights, instruction.getP1(), instruction.getP2(), isPart1);
            }
        }
        return countLights(lights);
    }

    private static void toggle(int[][] lights, Point p1, Point p2, boolean isPart1) {
        for (int x = p1.getX(); x <= p2.getX(); x++) {
            for (int y = p1.getY(); y <= p2.getY(); y++) {
                lights[x][y] = isPart1 ? (lights[x][y] == 0 ? 1 : 0) : lights[x][y] + 2;
            }
        }
    }

    private static void turnOn(int[][] lights, Point p1, Point p2, boolean isPart1) {
        for (int x = p1.getX(); x <= p2.getX(); x++) {
            for (int y = p1.getY(); y <= p2.getY(); y++) {
                lights[x][y] = isPart1 ? 1 : lights[x][y] + 1;
            }
        }
    }

    private static void turnOff(int[][] lights, Point p1, Point p2, boolean isPart1) {
        for (int x = p1.getX(); x <= p2.getX(); x++) {
            for (int y = p1.getY(); y <= p2.getY(); y++) {
                lights[x][y] = isPart1 ? 0 : (lights[x][y] > 0 ? lights[x][y] - 1 : 0);
            }
        }
    }

    private static int countLights(int[][] lights) {
        int count = 0;
        for (int x = 0; x < 1000; x++) {
            for (int y = 0; y < 1000; y++) {
                count += lights[x][y];
            }
        }
        return count;
    }

}
