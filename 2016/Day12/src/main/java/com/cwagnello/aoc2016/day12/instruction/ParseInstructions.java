package com.cwagnello.aoc2016.day12.instruction;

import com.cwagnello.aoc2016.day12.RegisterName;

import java.util.ArrayList;
import java.util.List;

public final class ParseInstructions {
    private static ParseInstructions parser;

    private ParseInstructions() {}

    public static ParseInstructions getParser() {
        if (parser == null) {
            parser = new ParseInstructions();
        }
        return parser;
    }

    public List<Instruction> parse(final List<String> input) {
        List<Instruction> instructions = new ArrayList<>();
        for (String line : input) {
            String[] parts = line.split("\\s+");
            switch(parts[0]) {
                case "cpy" -> {
                    if (parts[1].matches("\\d+")) {
                        instructions.add(new CopyValue(Integer.parseInt(parts[1]), RegisterName.valueOf(parts[2].toUpperCase())));
                    }
                    else {
                        instructions.add(new CopyRegister(RegisterName.valueOf(parts[1].toUpperCase()), RegisterName.valueOf(parts[2].toUpperCase())));
                    }
                }
                case "dec" -> instructions.add(new Decrement(RegisterName.valueOf(parts[1].toUpperCase())));
                case "inc" -> instructions.add(new Increment(RegisterName.valueOf(parts[1].toUpperCase())));
                case "jnz" -> {
                    if (parts[1].matches("\\d+")) {
                        instructions.add(new JumpIfNotZero(Integer.parseInt(parts[1]), Integer.parseInt(parts[2])));
                    }
                    else {
                        instructions.add(new JumpIfRegisterNotZero(RegisterName.valueOf(parts[1].toUpperCase()), Integer.parseInt(parts[2])));
                    }
                }
                default -> throw new IllegalArgumentException("Unknown instruction: " + line);
            }
        }
        return instructions;
    }
}
