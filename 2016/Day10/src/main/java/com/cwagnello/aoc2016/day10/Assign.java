package com.cwagnello.aoc2016.day10;

public class Assign extends Instruction {
    private final int bot;
    private final int chip;

    public Assign(int bot, int chip) {
        this.bot = bot;
        this.chip = chip;
    }

    public int botId() {
        return this.bot;
    }

    public int chip() {
        return this.chip;
    }
}
