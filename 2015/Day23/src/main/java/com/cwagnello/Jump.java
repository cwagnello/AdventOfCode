package com.cwagnello;

public class Jump extends Instruction {
    private int offset;

    public Jump(int offset) {
        this.offset = offset;
    }
    public int evaluate() {
        return this.getOffset();
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
