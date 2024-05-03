package com.cwagnello;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        File input = new File("test.txt");
        System.out.println(partOne(input));
        System.out.println(partTwo(input));
    }

    static int partOne(File file) {
        int sum = 0;
        try {
            int min = 0;
            int max = 0;
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                min = Integer.MAX_VALUE;
                max = Integer.MIN_VALUE;
                String[] values = sc.nextLine().split("\\s+");
                for (String s : values) {
                    int integer = Integer.parseInt(s, 10);
                    if (integer > max) {
                        max = integer;
                    }
                    else if (integer < min) {
                        min = integer;
                    }
                }
                //System.out.println("Min: " + min + ", Max: " + max);
                sum += max - min;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return sum;
    }

    static int partTwo(File file) {
        int sum = 0;
        try {
            int left = 0;
            int right = 0;
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String[] values = sc.nextLine().split("\\s+");
                int[] integers = new int[values.length];
                int i = 0;
                for (String s : values) {
                    int integer = Integer.parseInt(s, 10);
                    integers[i++] = integer;
                }
                Arrays.sort(integers);
                for (int j = integers.length - 1; j > 0; j--) {
                    for (int k = 0; k < integers.length - 1; k++) {
                        if (j != k && integers[j] % integers[k] == 0) {
                            sum += integers[j] / integers[k];
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return sum;
    }
}
