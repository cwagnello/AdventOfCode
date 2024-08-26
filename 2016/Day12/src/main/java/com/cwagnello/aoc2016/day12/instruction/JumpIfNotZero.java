package com.cwagnello.aoc2016.day12.instruction;

public class JumpIfNotZero implements Instruction {
    private final int value;
    private final int offset;

    public JumpIfNotZero(final int value, final int offset) {
        this.value = value;
        this.offset = offset;
    }

    @Override
    public int evaluate() {
        return value == 0 ? 1 : offset;
    }
}
