package com.cwagnello.aoc2024.day16;

public enum DIRECTION {
    NORTH (-1, 0),
    SOUTH (1, 0),
    WEST (0, -1),
    EAST (0, 1);

    DIRECTION(final int row, final int col) {
        this.dRow = row;
        this.dCol = col;
    }

    private final int dRow;
    private final int dCol;

    public int getDiffRow() {
        return this.dRow;
    }

    public int getDiffCol() {
        return this.dCol;
    }

}
