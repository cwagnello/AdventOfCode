package com.cwagnello.aoc2016.day10;

public abstract class Instruction {
    private boolean isProcessed;
    public void setProcessed(boolean processed) {
        this.isProcessed = processed;
    }

    public boolean isProcessed() {
        return this.isProcessed;
    }
}
