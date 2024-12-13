package com.cwagnello.aoc2024.day06;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Guard {
    private int row;
    private int column;
    private Direction direction;

    public Guard(int row, int column) {
        this.row = row;
        this.column = column;
        this.direction = Direction.UP;
    }

    public Guard(Guard guard) {
        this(guard.row, guard.column);
        this.direction = guard.direction;
    }
}
