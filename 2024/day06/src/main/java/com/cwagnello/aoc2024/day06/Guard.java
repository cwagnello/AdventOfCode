package com.cwagnello.aoc2024.day06;

import lombok.Data;

@Data
public class Pair {
    private int row;
    private int column;
    private Direction direction;

    public Pair(int row, int column) {
        this.row = row;
        this.column = column;
        this.direction = Direction.UP;
    }
}
