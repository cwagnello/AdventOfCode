package com.cwagnello;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author cwagnello
 */
public class Solution {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            int totalSize = 0;
            int totalLengthOfRibbon = 0;
            //String filename = "src/main/resources/test.txt";
            String filename = "src/main/resources/input.txt";
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            while((line = br.readLine()) != null) {
                String[] dimensions = line.split("x");
                int[] dimInts = new int[3];
                for (int i = 0; i < dimInts.length; i++) {
                    dimInts[i] = Integer.parseInt(dimensions[i]);
                }
                int[] area = 
                { 
                    dimInts[0] * dimInts[1], 
                    dimInts[1] * dimInts[2], 
                    dimInts[0] * dimInts[2]
                };
                int[] perimeter = 
                { 
                    2 * dimInts[0] + 2 * dimInts[1], 
                    2 * dimInts[1] + 2 * dimInts[2],
                    2 * dimInts[0] + 2 * dimInts[2]
                };
                int smallestSize = area[0];
                int smallestPerimeter = perimeter[0];
                for (int i = 1; i < area.length; i++) {
                    if (area[i] < smallestSize) {
                        smallestSize = area[i];
                    }
                    if (perimeter[i] < smallestPerimeter) {
                        smallestPerimeter = perimeter[i];
                    }
                }
                totalSize += 2 * (area[0] + area[1] + area[2]) + smallestSize;
                totalLengthOfRibbon += smallestPerimeter + dimInts[0] * dimInts[1] * dimInts[2];
            }
            System.out.println("Total Wrapping paper: " + totalSize);
            System.out.println("Total Ribbon: " + totalLengthOfRibbon);
        }
        catch (IOException ioe) {
            System.out.println(ioe);
        }
    }
    
}
