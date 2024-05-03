package com.cwagnello;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author cwagnello
 */
public class Solution {
    private static boolean[][] lights = new boolean[1000][1000];
    private static int[][] lights2 = new int[1000][1000];

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        init();
        String filename = "/home/cwagnello/workspace/AdventOfCode/2015/Day06/src/com/cwagnello/input.txt";
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = br.readLine()) != null) {
                Instruction instruction = new Instruction(line);
                process(instruction);
            }
            System.out.println("Number of lights that are on: " + countLights());
            System.out.println("Total brightness is: " + countLights2());
        }
        catch (FileNotFoundException ex) {
            System.out.println("File not found: " + ex);
        }
        catch (IOException ex) {
            System.out.println("Error reading file: " + ex);
        }
    }

    private static void init() {
        for (int x = 0; x < 1000; x++) {
            for (int y = 0; y < 1000; y++) {
                lights[x][y] = false;
                lights2[x][y] = 0;
            }
        }        
    }

    private static void process(Instruction instruction) {
        switch (instruction.getCommand()) {
            case "toggle":
                toggle(instruction.getP1(), instruction.getP2());
                break;
            case "turn on":
                turnOn(instruction.getP1(), instruction.getP2());
                break;
            case "turn off":
                turnOff(instruction.getP1(), instruction.getP2());
                break;
            default:
        }
    }

    private static void toggle(Point p1, Point p2) {
        for (int x = p1.getX(); x <= p2.getX(); x++) {
            for (int y = p1.getY(); y <= p2.getY(); y++) {
                lights[x][y] = !lights[x][y];
                lights2[x][y] += 2;
            }
        }
    }

    private static void turnOn(Point p1, Point p2) {
        for (int x = p1.getX(); x <= p2.getX(); x++) {
            for (int y = p1.getY(); y <= p2.getY(); y++) {
                lights[x][y] = true;
                lights2[x][y]++;
            }
        }
    }

    private static void turnOff(Point p1, Point p2) {
        for (int x = p1.getX(); x <= p2.getX(); x++) {
            for (int y = p1.getY(); y <= p2.getY(); y++) {
                lights[x][y] = false;
                if (lights2[x][y] > 0) {
                    lights2[x][y]--;
                }
            }
        }
    }

    private static int countLights() {
        int count = 0;
        for (int x = 0; x < 1000; x++) {
            for (int y = 0; y < 1000; y++) {
                if (lights[x][y]) { count++; }
            }
        }
        return count;
    }

    private static int countLights2() {
        int count = 0;
        for (int x = 0; x < 1000; x++) {
            for (int y = 0; y < 1000; y++) {
                count += lights2[x][y];
            }
        }
        return count;
    }

}
