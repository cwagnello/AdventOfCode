package com.cwagnello;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

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

        List<Brick> bricks = parseInput(input);

        bricks.sort (Comparator.comparingInt(Brick::lowestPoint));
//        for (Brick b: bricks) {
//            System.out.println("Brick: " + b + ", Volume: " + ((b.c2.x - b.c1.x) * (b.c2.y - b.c1.y) * (b.c2.z - b.c1.z + 1)));
//        }
        Map<Brick, List<Brick>> bricksBelow = new HashMap<>();
        Map<Brick, List<Brick>> bricksAbove = new HashMap<>();
        makeBricksFall(bricks);
        mapBricksAboveAndBelow(bricks, bricksBelow, bricksAbove);
        System.out.println("Day22 - Part1: " + part1(bricks, bricksBelow, bricksAbove));
        System.out.println("Day22 - Part2: " + part2(bricks));
    }


    private static List<Brick> cloneBricks(List<Brick> bricks) {
        List<Brick> copyOfBricks = new ArrayList<>();
        for (Brick b: bricks) {
            copyOfBricks.add(new Brick(new Coordinate(b.c1), new Coordinate(b.c2)));
        }
        return copyOfBricks;
    }

    private static int part2(List<Brick> bricks) {
        int count = 0;
        for (int i = 0; i < bricks.size(); i++) {
            List<Brick> copyOfBricks = cloneBricks(bricks);
            copyOfBricks.remove(i);
            count += makeBricksFall(copyOfBricks);
        }
        return count;
    }

    private static void mapBricksAboveAndBelow(List<Brick> bricks, Map<Brick, List<Brick>> bricksBelow, Map<Brick, List<Brick>> bricksAbove) {
        for (int i = 1; i < bricks.size(); i++) {
            for (int j = i - 1; j >= 0; j--) {
                Brick current = bricks.get(i);
                Brick next = bricks.get(j);
                if (current.c1.z == next.c2.z + 1 && current.overlaps(next)) {
                    if (!bricksBelow.containsKey(current)) {
                        bricksBelow.put(current, new ArrayList<>());
                    }
                    bricksBelow.get(current).add(next);

                    if (!bricksAbove.containsKey(next)) {
                        bricksAbove.put(next, new ArrayList<>());
                    }
                    bricksAbove.get(next).add(current);
                }
            }
        }
    }
    private static int makeBricksFall(List<Brick> bricks) {
        int count = 0;
        for (int i = 0; i < bricks.size(); i++) {
            //Skip the bricks already at the bottom
            Brick current = bricks.get(i);
            if (current.c1.z == 1) {
                continue;
            }
            List<Brick> allOverlapping = new ArrayList<>();
            for (int j = i - 1; j >= 0; j--) {
                if (current.overlaps(bricks.get(j))) {
                    allOverlapping.add(bricks.get(j));
                }
            }

            if (allOverlapping.size() > 0) {
                int maxZ = 0;
                for (Brick b : allOverlapping) {
                    if (b.c2.z > maxZ) {
                        maxZ = b.c2.z;
                    }
                }
                if (maxZ + 1 != current.c1.z) {
                    count++;
                }
                current.updateZ(maxZ + 1);
            }
            else {
                //No overlapping means we hit the bottom
                current.updateZ(1);
                count++;
            }
        }
        return count;
    }

    private static int part1(List<Brick> bricks, Map<Brick, List<Brick>> bricksBelow, Map<Brick, List<Brick>> bricksAbove) {

        Set<Brick> disintegratable = new HashSet<>();
        for (Brick brick: bricks) {
            boolean isBreakable = true;
            if (bricksAbove.containsKey(brick)) {
                for (Brick b : bricksAbove.get(brick)) {
                    if (bricksBelow.get(b).size() < 2) {
                        isBreakable = false;
                        break;
                    }
                }
                if (isBreakable) {
                    disintegratable.add(brick);
                }
            }
            else {
                disintegratable.add(brick);
            }
        }
        return disintegratable.size();
    }
    private static List<Brick> parseInput(List<String> input) {
        List<Brick> bricks = new ArrayList<>();
        for (String line: input) {
            String[] corners = line.split("~");
            String[] c1 = corners[0].split(",");
            String[] c2 = corners[1].split(",");
            //Add 1 to the x/y values for the 2nd coordinate to use the opposite corner of the square to give us a way to
            // calculate overlaps.
            bricks.add(new Brick(
                    new Coordinate(Integer.parseInt(c1[0]), Integer.parseInt(c1[1]), Integer.parseInt(c1[2])),
                    new Coordinate(Integer.parseInt(c2[0]) + 1, Integer.parseInt(c2[1]) + 1, Integer.parseInt(c2[2]))));
        }
        return bricks;
    }
}

class Coordinate {
    final int x;
    final int y;
    int z;
    public Coordinate(final int x, final int y, final int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Coordinate(Coordinate coordinate) {
        this.x = coordinate.x;
        this.y = coordinate.y;
        this.z = coordinate.z;
    }
    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }
}

class Brick {
    final Coordinate c1;
    final Coordinate c2;
    public Brick(final Coordinate c1, final Coordinate c2) {
        this.c1 = c1;
        this.c2 = c2;
    }
    public int lowestPoint() {
        return Math.min(c1.z, c2.z);
    }

    //Overlap calculation in the x-y plane. Requires the coordinate of the two opposing corners. The data doesn't give us that
    // but if we correct for it on the input parsing side this will work.
    public boolean overlaps (Brick other) {
        boolean xIsPositive = Math.min(this.c2.x, other.c2.x) > Math.max(this.c1.x, other.c1.x);
        boolean yIsPositive = Math.min(this.c2.y, other.c2.y) > Math.max(this.c1.y, other.c1.y);
        return xIsPositive && yIsPositive;
    }

    public void updateZ(int newZ) {
        int dz = this.c2.z - this.c1.z;
        this.c1.z = newZ;
        this.c2.z = newZ;
        if (dz != 0) {
            this.c2.z += dz;
        }
    }

    @Override
    public String toString() {
        return "" + this.c1 + ", " + this.c2;
    }
}
