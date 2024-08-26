package com.cwagnello.aoc2016.day12.instruction;

import com.cwagnello.aoc2016.day12.RegisterName;
import com.cwagnello.aoc2016.day12.Registers;

public class CopyValue implements Instruction {
    private final int value;
    private final RegisterName registerName;

    public CopyValue(final int value, final RegisterName registerName) {
        this.value = value;
        this.registerName = registerName;
    }

    @Override
    public int evaluate() {
        Registers.getInstance().set(this.registerName, this.value);
        return 1;
    }
}
