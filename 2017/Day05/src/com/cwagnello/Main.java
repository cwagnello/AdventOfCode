package com.cwagnello;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File("test.txt"));
        List<Integer> jumps = new ArrayList<>();
        while(sc.hasNext()) {
            jumps.add(sc.nextInt());
        }
        part2(jumps);
    }

    public static void part1(List<Integer> jumps) {
        int count = 0;
        //Get the first one and start the jumps from there.
        int curValue = jumps.get(0);
        int index = 0;
        while (true) {
            index = curValue;
            count++;
            if (index < 0 || index >= jumps.size()) {
                break;
            }
            curValue += jumps.get(index);
            //System.out.println("List: " + jumps);
            jumps.set(index, jumps.get(index) + 1);
        }
        System.out.println(count);

    }

    public static void part2(List<Integer> jumps) {
        int count = 0;
        //Get the first one and start the jumps from there.
        int curValue = jumps.get(0);
        int index = 0;
        while (true) {
            index = curValue;
            if (index < 0 || index >= jumps.size()) {
                break;
            }
            curValue += jumps.get(index);
            //System.out.println("List: " + jumps);
            int offset = 1;
            if (jumps.get(index) >= 3) {
                offset = -1;
            }
            jumps.set(index, jumps.get(index) + offset);
            count++;
        }
        System.out.println(count);

    }

}
