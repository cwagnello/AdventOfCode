package com.cwagnello.aoc2016.day12.instruction;

import com.cwagnello.aoc2016.day12.RegisterName;
import com.cwagnello.aoc2016.day12.Registers;

public class JumpIfRegisterNotZero implements Instruction {
    private final RegisterName registerName;
    private final int offset;

    public JumpIfRegisterNotZero(final RegisterName registerName, final int offset) {
        this.registerName = registerName;
        this.offset = offset;
    }

    @Override
    public int evaluate() {
        return Registers.getInstance().get(registerName) != 0 ? offset : 1;
    }
}
