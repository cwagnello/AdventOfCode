package com.cwagnello;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author cwagnello
 */
public class Solution {

    /**
     * @param args the command line arguments
     */
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

        int numHouses = process(input.get(0), 0, 1).size();
        System.out.println("2015 Day03 part1: " + numHouses);

        Set<Point> houses = process(input.get(0), 0, 2);
        houses.addAll(process(input.get(0), 1, 2));
        numHouses = houses.size();
        System.out.println("2015 Day03 part2: " + numHouses);
    }

    private static Set<Point> process(String input, int startIndex, int stepSize) {
        Point santa = new Point(0,0);
        Set<Point> visitedPoints = new HashSet<>();

        visitedPoints.add(santa);

        char[] moves = input.toCharArray();
        for (int i = startIndex; i < moves.length; i += stepSize) {

            santa = switch (moves[i]) {
                case '^' -> santa.moveUp();
                case '>' -> santa.moveRight();
                case 'v' -> santa.moveDown();
                case '<' -> santa.moveLeft();
                default -> throw new IllegalStateException("Unknown move type: " + moves[i]);
            };
            visitedPoints.add(santa);
        }
        return visitedPoints;
    }

}
