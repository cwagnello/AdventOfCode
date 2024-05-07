package com.cwagnello;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Solution {
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

        Map<String, Wire> wireMap = new HashMap<>();

        List<Operator> operations = parse(input, wireMap);
        while (wireMap.get("a").getValue() == null) {
            for (Operator operation : operations) {
                //System.out.println("Operation: " + operation);
                operation.evaluate();
            }
        }
        System.out.println("2015 Day07 - part1: " + wireMap.get("a").getValue());

        int newValueB = wireMap.get("a").getValue();
        wireMap.clear();
        operations = parse(input, wireMap);
        while (wireMap.get("a").getValue() == null) {
            for (Operator operation : operations) {
                if (operation instanceof Assignment && ((Assignment) operation).getOutput().getName().equals("b")) {
                    ((Assignment) operation).getOutput().setValue(newValueB);
                    continue;
                }
                operation.evaluate();
            }
        }
        System.out.println("2015 Day07 - part2: " + wireMap.get("a").getValue());
    }

    private static List<Operator> parse(List<String> input, Map<String, Wire> wireMap) {
        List<Operator> operations = new ArrayList<>();
        for (String line: input) {
            String[] parts = line.split("\\s+->\\s+");
            Wire output = wireMap.containsKey(parts[1]) ? wireMap.get(parts[1]) : new Wire(parts[1]);
            wireMap.put(output.getName(), output);

            if (line.contains("AND")) {
                String[] leftSide = parts[0].split("\\s+");
                Wire input1 = wireMap.containsKey(leftSide[0]) ? wireMap.get(leftSide[0]) : new Wire(leftSide[0]);
                if (input1.getName().matches("\\d+")) {
                    input1.setValue(Integer.parseInt(leftSide[0]));
                }
                Wire input2 = wireMap.containsKey(leftSide[2]) ? wireMap.get(leftSide[2]) : new Wire(leftSide[2]);

                wireMap.put(input1.getName(), input1);
                wireMap.put(input2.getName(), input2);

                And and = And.builder().input1(input1).input2(input2).output(output).build();
                operations.add(and);
            }
            else if (line.contains("OR")) {
                String[] leftSide = parts[0].split("\\s+");
                Wire input1 = wireMap.containsKey(leftSide[0]) ? wireMap.get(leftSide[0]) : new Wire(leftSide[0]);
                if (input1.getName().matches("\\d+")) {
                    input1.setValue(Integer.parseInt(leftSide[0]));
                }
                Wire input2 = wireMap.containsKey(leftSide[2]) ? wireMap.get(leftSide[2]) : new Wire(leftSide[2]);
                wireMap.put(input1.getName(), input1);
                wireMap.put(input2.getName(), input2);
                Or or = Or.builder().input1(input1).input2(input2).output(output).build();
                operations.add(or);
            }
            else if (line.contains("LSHIFT")) {
                String[] leftSide = parts[0].split("\\s+");
                Wire input1 = wireMap.containsKey(leftSide[0]) ? wireMap.get(leftSide[0]) : new Wire(leftSide[0]);
                Wire input2 = wireMap.containsKey(leftSide[2]) ? wireMap.get(leftSide[2]) : new Wire(leftSide[2]);
                if (input2.getName().matches("\\d+")) {
                    input2.setValue(Integer.parseInt(leftSide[2]));
                }
                wireMap.put(input1.getName(), input1);
                wireMap.put(input2.getName(), input2);

                Lshift lshift = Lshift.builder().input1(input1).input2(input2).output(output).build();
                operations.add(lshift);
            }
            else if (line.contains("RSHIFT")) {
                String[] leftSide = parts[0].split("\\s+");
                Wire input1 = wireMap.containsKey(leftSide[0]) ? wireMap.get(leftSide[0]) : new Wire(leftSide[0]);
                Wire input2 = wireMap.containsKey(leftSide[2]) ? wireMap.get(leftSide[2]) : new Wire(leftSide[2]);
                if (input2.getName().matches("\\d+")) {
                    input2.setValue(Integer.parseInt(leftSide[2]));
                }
                wireMap.put(input1.getName(), input1);
                wireMap.put(input2.getName(), input2);
                Rshift rshift = Rshift.builder().input1(input1).input2(input2).output(output).build();
                operations.add(rshift);
            }
            else if (line.contains("NOT")) {
                String[] leftSide = parts[0].split("\\s+");
                Wire input1 = wireMap.containsKey(leftSide[1]) ? wireMap.get(leftSide[1]) : new Wire(leftSide[1]);
                wireMap.put(input1.getName(), input1);
                Not not = Not.builder().input(input1).output(output).build();
                operations.add(not);
            }
            else {
                //Assume it must be an assignment if it reaches here
                Wire input1 = wireMap.containsKey(parts[0]) ? wireMap.get(parts[0]) : new Wire(parts[0]);
                if (parts[0].matches("\\d+")) {
                    input1.setValue(Integer.parseInt(parts[0]));
                }
                wireMap.put(input1.getName(), input1);
                Assignment assignment = Assignment.builder().input(input1).output(output).build();
                operations.add(assignment);
            }
        }
        return operations;
    }
}