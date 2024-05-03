package com.cwagnello;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Solution {
    public static void main (String[] args) {
        File file = new File("src/com/cwagnello/input.txt");
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
        System.out.println("Day15 - Part1: " + part1(input));
        System.out.println("Day15 - Part2: " + part2(input));
    }

    private static int part2(List<String> input) {
        String[] commands = input.get(0).split(",");
        int sum = 0;
        // hash -> list of lenses
        Map<Integer, List<Lens>> lenses = new HashMap<>();

        for (String command : commands) {
            if (command.contains("=")) {
                String[] parts = command.split("=");
                Lens lens = new Lens(parts[0], Integer.parseInt(parts[1]));
                if (lenses.containsKey(lens.hashCode())) {
                    int index = lenses.get(lens.hashCode()).indexOf(lens);
                    if (index != -1) {
                        lenses.get(lens.hashCode()).set(index, lens);
                    }
                    else {
                        lenses.get(lens.hashCode()).add(lens);
                    }
                }
                else {
                    List<Lens> newLens = new ArrayList<>();
                    newLens.add(lens);
                    lenses.put(lens.hashCode(), newLens);
                }
            }
            else {
                int index = command.indexOf("-");
                Lens lens = new Lens(command.substring(0, index), -1);
                if (lenses.containsKey(lens.hashCode())) {
                    lenses.get(lens.hashCode()).remove(lens);
                }
            }
        }
        for (int key: lenses.keySet()) {
            int slot = 1;
            for (Lens lens: lenses.get(key)) {
                sum += (key + 1) * slot * lens.getFocalLength();
                slot++;
            }
        }
        return sum;
    }

    private static long part1(List<String> input) {
        String[] commands = input.get(0).split(",");
        long sum = 0;
        for (String command : commands) {
            sum += Util.calculateHash(command);
        }
        return sum;
    }


}

class Util {
    public static int calculateHash(String command) {
        int hash = 0;

        for (char c : command.toCharArray()) {
            hash = ((hash + c) * 17) % 256;
        }
        return hash;
    }

}

class Lens {
    private int hashCode = Integer.MIN_VALUE;
    private String label;
    private int focalLength;

    public Lens(String label, int focalLength) {
        this.label = label;
        this.focalLength = focalLength;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getFocalLength() {
        return focalLength;
    }

    public void setFocalLength(int focalLength) {
        this.focalLength = focalLength;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lens lens = (Lens) o;
        return hashCode() == lens.hashCode() && label.equals(lens.label);
    }

    @Override
    public int hashCode() {
        if (this.hashCode == Integer.MIN_VALUE) {
            this.hashCode = Util.calculateHash(this.label);
        }
        return this.hashCode;
    }
}
