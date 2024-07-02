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
        List<Instruction> instructions = new ArrayList<>();
        Map<String, Register> registers = new HashMap<>();
        parse(input, instructions, registers);

        System.out.println("2015 Day 23 part 1: " + part1(instructions, registers));
        System.out.println("2015 Day 23 part 2: " + part2(instructions, registers));
    }

    private static void parse (List<String> input, List<Instruction> instructions, Map<String, Register> registers) {
        for (String line : input) {
            String cmd = line.substring(0, 3);
            switch(cmd) {
                case "hlf" -> {
                    String[] parts = line.split("\\s+");
                    if (!registers.containsKey(parts[1])) {
                        registers.put(parts[1], new Register(parts[1]));
                    }
                    instructions.add(new Half(registers.get(parts[1])));
                }
                case "tpl" -> {
                    String[] parts = line.split("\\s+");
                    if (!registers.containsKey(parts[1])) {
                        registers.put(parts[1], new Register(parts[1]));
                    }
                    instructions.add(new Triple(registers.get(parts[1])));
                }
                case "inc" -> {
                    String[] parts = line.split("\\s+");
                    if (!registers.containsKey(parts[1])) {
                        registers.put(parts[1], new Register(parts[1]));
                    }
                    instructions.add(new Increment(registers.get(parts[1])));
                }
                case "jmp" -> {
                    String[] parts = line.split("\\s+");
                    instructions.add(new Jump(Integer.parseInt(parts[1])));
                }
                case "jie" ->{
                    String[] parts = line.split(",*\\s+");
                    if (!registers.containsKey(parts[1])) {
                        registers.put(parts[1], new Register(parts[1]));
                    }
                    instructions.add(new JumpIfEven(registers.get(parts[1]), Integer.parseInt(parts[2])));
                }
                case "jio" -> {
                    String[] parts = line.split(",*\\s+");
                    if (!registers.containsKey(parts[1])) {
                        registers.put(parts[1], new Register(parts[1]));
                    }
                    instructions.add(new JumpIfOne(registers.get(parts[1]), Integer.parseInt(parts[2])));
                }
                default -> throw new IllegalStateException("Unknown instruction");
            }
        }
    }

    private static long process(List<Instruction> instructions, Map<String, Register> registers) {
        int index = 0;
        while (index < instructions.size() && index >= 0) {
            Instruction instruction = instructions.get(index);
            switch (instruction) {
                case Half h -> {
                    h.evaluate();
                    index++;
                }
                case Triple t -> {
                    t.evaluate();
                    index++;
                }
                case Increment i -> {
                    i.evaluate();
                    index++;
                }
                case Jump j -> index += j.evaluate();
                default -> throw new IllegalStateException("Unknown instruction");
            }
            //System.out.printf("A: %d, B: %d%n", registers.get("a").getValue(), registers.get("b").getValue());
        }
        return registers.get("b").getValue();
    }

    private static long part1(List<Instruction> instructions, Map<String, Register> registers) {
        return process(instructions, registers);
    }

    private static long part2(List<Instruction> instructions, Map<String, Register> registers) {
        registers.get("a").setValue(1);
        registers.get("b").setValue(0);
        return process(instructions, registers);
    }
}