package com.cwagnello.aoc2016.day12.instruction;

import com.cwagnello.aoc2016.day12.RegisterName;
import com.cwagnello.aoc2016.day12.Registers;

public class CopyRegister implements Instruction {
    private final RegisterName from;
    private final RegisterName to;

    public CopyRegister(final RegisterName from, final RegisterName to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public int evaluate() {
        Registers.getInstance().set(to, Registers.getInstance().get(from));
        return 1;
    }
}
