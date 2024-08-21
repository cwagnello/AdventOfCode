package com.cwagnello.aoc2016.day10;

public class BotDistributeChip extends Instruction {
    private final int source;
    private final Receiver low;
    private final Receiver high;

    public BotDistributeChip(int source, Receiver low, Receiver high) {
        this.source = source;
        this.low = low;
        this.high = high;
    }

    public int source() {
        return this.source;
    }

    public Receiver lower() {
        return this.low;
    }

    public Receiver higher() {
        return this.high;
    }
}
