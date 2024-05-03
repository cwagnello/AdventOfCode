package com.cwagnello;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
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
        Set<Point> visitedPoints = new HashSet<>();
        String filename = "/home/cwagnello/workspace/AdventOfCode/2015/Day03/src/com/cwagnello/input.txt";
        try {
            Point current = new Point();
            Point santa = new Point(0,0);
            Point robot = new Point(0,0);
            visitedPoints.add(santa);
            visitedPoints.add(robot);

            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = br.readLine()) != null) {
                char[] moves = line.toCharArray();
                for (int i = 0; i < moves.length; i++) {
                    current = (i % 2 == 0) ? santa : robot;

                    switch (moves[i]) {
                        case '^' :
                            current = current.moveUp();
                            break;
                        case '>' :
                            current = current.moveRight();
                            break;
                        case 'v' :
                            current = current.moveDown();
                            break;
                        case '<' :
                            current = current.moveLeft();
                            break;
                        default : 
                    }
                    if (i % 2 == 0) { 
                        santa = current; 
                    } else { 
                        robot = current; 
                    }
                    //System.out.println("move: " + moves[i] + " " + current + ", hashCode: " + current.hashCode());
                    visitedPoints.add(current);
                }
            }
            System.out.println("Number of houses that received at least 1 present: " + visitedPoints.size());
        }
        catch (IOException ioe) {
            
        }
    }
    
}
