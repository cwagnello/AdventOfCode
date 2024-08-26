package com.cwagnello.aoc2016.day12;

import com.cwagnello.aoc2016.day12.instruction.Instruction;
import com.cwagnello.aoc2016.day12.instruction.ParseInstructions;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.cwagnello.aoc2016.day12.RegisterName.A;

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
        List<Instruction> instructions = ParseInstructions.getParser().parse(input);
        System.out.println("2016 day 12 part 1: " + part1(instructions));
        System.out.println("2016 day 12 part 2: " + part2(instructions));
    }

    private static int part1(List<Instruction> instructions) {
        Registers registers = Registers.getInstance();
        int programCounter = 0;

        while (programCounter < instructions.size()) {
            Instruction instruction = instructions.get(programCounter);
            programCounter += instruction.evaluate();
        }
        return registers.get(A);
    }

    private static int part2(List<Instruction> instructions) {
        Registers registers = Registers.getInstance();
        registers.reset();
        registers.set(RegisterName.C, 1);
        int programCounter = 0;

        while (programCounter < instructions.size()) {
            Instruction instruction = instructions.get(programCounter);
            programCounter += instruction.evaluate();
        }
        return registers.get(A);
    }
}
