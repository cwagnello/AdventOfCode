package com.cwagnello.aoc2016.day10;

import java.util.TreeSet;

public class Bot {
    private int bot;
    private final TreeSet<Integer> chips;
    public Bot(int bot) {
        this.bot = bot;
        this.chips = new TreeSet<>();
    }

    public Integer giveLowest() {
        return this.chips.pollFirst();
    }

    public Integer giveHighest() {
        return this.chips.pollLast();
    }

    public void add(int chip) {
        this.chips.add(chip);
    }

    public boolean hasTwoChips() {
        return chips.size() >= 2;
    }

    public boolean contains(int chip) {
        return chips.contains(chip);
    }
}
