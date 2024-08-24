package com.cwagnello.aoc2016.day11;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution {
    private static final int GENERATORS_MASK = 0x2AA;

    public static void main (String[] args) {
        File file = new File("src/main/resources/input.txt");
        List<String> input = new ArrayList<>();
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                //assume input data is well formed
                input.add(line);
            }
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        List<Integer> floorState = parse(input);
        System.out.println("2016 day 11 part 1: " + part1(floorState));
        System.out.println("2016 day 11 part 2: " + part2(floorState));
    }

    private static int part1(List<Integer> bitmasks) {
        return bfs(bitmasks);
    }

    private static int part2(List<Integer> bitmasks) {
        //Add the 2 additional pairs of things on the first floor
        bitmasks.set(0, bitmasks.getFirst() + 0x3C0000);
        return bfs(bitmasks);
    }
    private static int bfs(List<Integer> bitmasks) {
        Set<String> visited = new HashSet<>();
        FloorState floorState = new FloorState(0, bitmasks);
        visited.add(floorState.hashCodeString());
        Queue<FloorState> queue = new LinkedList<>();
        queue.add(floorState);
        int steps = 0;
        while (!queue.isEmpty()) {
            //System.out.println("Step -> " + steps);
            int queueSize = queue.size();
            for (int i = 0; i < queueSize; i++) {
                FloorState current = queue.remove();
                //System.out.println(current);

                if (calculateFinishState(current.floorStates())) {
                    //System.out.println("Moved everything to the 4th floor!");
                    return steps;
                }
                List<List<Integer>> nextMoves = generateNextMoves(current.getFloorState(current.elevator()));
                for (int nextElevatorFloor: validNextFloors(current.elevator())) {
                    for (List<Integer> moves : nextMoves) {
                        int updateCurrentFloor = current.getFloorState(current.elevator());
                        int nextFloor = current.getFloorState(nextElevatorFloor);
                        for (int index : moves) {
                            updateCurrentFloor -= (int) Math.pow(2, index);
                            nextFloor += (int) Math.pow(2, index);
                        }
                        if (isMoveValid(updateCurrentFloor) && isMoveValid(nextFloor)) {
                            List<Integer> currentFloorState = new ArrayList<>(current.floorStates());
                            currentFloorState.set(current.elevator(), updateCurrentFloor);
                            currentFloorState.set(nextElevatorFloor, nextFloor);
                            FloorState nextFloorState = new FloorState(nextElevatorFloor, currentFloorState);
                            if (!visited.contains(nextFloorState.hashCodeString())) {
                                queue.add(nextFloorState);
                                visited.add(nextFloorState.hashCodeString());
                            }
                        }
                    }
                }
            }
            steps++;
        }
        return -1;

    }

    private static boolean calculateFinishState(List<Integer> bitmasks) {
        for (int i = 0; i < bitmasks.size() - 1; i++) {
            if (bitmasks.get(i) != 0) {
                return false;
            }
        }
        return true;
    }

    private static List<Integer> validNextFloors(int currentFloor) {
        List<Integer> nextFloors = new ArrayList<>();
        if (currentFloor == 0) {
            nextFloors.add(1);
        }
        else if (currentFloor == 3) {
            nextFloors.add(2);
        }
        else {
            nextFloors.add(currentFloor + 1);
            nextFloors.add(currentFloor - 1);
        }
        return nextFloors;
    }
    /**
     * Each floor will be represented by a number. Each microchip and generator represents a specific bit in each number.
     * The generator and associated microchip will be next to each other in the bit representation.
     * Curium Generator -> bit 1
     * Curium Microchip -> bit 0
     *
     */

    private static List<Integer> parse(List<String> input) {
        Pattern MICROCHIP = Pattern.compile("([a-z]+-compatible\\s+microchip)");
        Pattern GENERATOR = Pattern.compile("([a-z]+\\s+generator)");
        List<Integer> bitmasks = new ArrayList<>();

        // Map item to floor
        Map<String, Integer> map = new HashMap<>();
        for (int floor = 0; floor < input.size(); floor++) {
            bitmasks.add(0);

            Matcher matcherMicrochips = MICROCHIP.matcher(input.get(floor));
            Matcher matcherGenerators = GENERATOR.matcher(input.get(floor));
            while (matcherGenerators.find()) {
                map.put(matcherGenerators.group(1), floor);
            }
            while (matcherMicrochips.find()) {
                map.put(matcherMicrochips.group(1), floor);
            }
        }
        List<String> equipment = new ArrayList<>(map.keySet());
        equipment.sort(String::compareTo);
        System.out.println(equipment);

        //Assemble initial bitmasks based on the index within the list of all generators/microchips
        for (String key: map.keySet()) {
            int index = equipment.indexOf(key);
            int floor = map.get(key);
            bitmasks.set(floor, bitmasks.get(floor) + (int)Math.pow(2, equipment.size() - index - 1));
        }
        System.out.println(bitmasks.stream().map(Integer::toBinaryString).map(s -> String.format("%010d", Long.parseLong(s))).toList());
        return bitmasks;
    }

    private static List<List<Integer>> generateNextMoves(Integer floor) {
        List<Integer> bits = new ArrayList<>();
        int index = 0;
        while (floor > 0) {
            if ((floor & 1) == 1) {
                bits.add(index);
            }
            index++;
            floor /= 2;
        }
        List<List<Integer>> nextMoves = new ArrayList<>();

        //build all the 1 item next moves
        for (Integer bit: bits) {
            List<Integer> nextSingle = new ArrayList<>();
            nextSingle.add(bit);
            nextMoves.add(nextSingle);
        }

        //build all the 2 item next moves
        for (int i = 0; i < bits.size(); i++) {
            for (int j = i + 1; j < bits.size(); j++) {
                List<Integer> nextPair = new ArrayList<>();
                nextPair.add(bits.get(i));
                nextPair.add(bits.get(j));
                nextMoves.add(nextPair);
            }
        }
        return nextMoves;
    }

    private static boolean isMoveValid(int floor) {
        if ((floor & GENERATORS_MASK) == 0) {
            return true;
        }
        while (floor > 0) {
            if ((floor & 3) == 1) {
                return false;
            }
            floor /= 4;
        }
        return true;
    }
}
