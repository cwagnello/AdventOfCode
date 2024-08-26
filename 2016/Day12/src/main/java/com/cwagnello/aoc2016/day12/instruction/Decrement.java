package com.cwagnello.aoc2016.day12.instruction;

import com.cwagnello.aoc2016.day12.RegisterName;
import com.cwagnello.aoc2016.day12.Registers;

public class Decrement implements Instruction {
    private final RegisterName registerName;

    public Decrement(final RegisterName registerName) {
        this.registerName = registerName;
    }

    @Override
    public int evaluate() {
        Registers.getInstance().set(registerName, Registers.getInstance().get(registerName) - 1);
        return 1;
    }
}
