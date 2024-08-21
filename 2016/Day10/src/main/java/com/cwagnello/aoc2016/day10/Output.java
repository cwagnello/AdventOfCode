package com.cwagnello.aoc2016.day10;

public class Output {
    private int id;
    private int chip;

    public Output(int id) {
        this.id = id;
        this.chip = -1;
    }

    public void add(Integer chip) {
        this.chip = chip;
    }

    public boolean isSet() {
        return this.chip != -1;
    }

    public int chip() {
        return this.chip;
    }
}
