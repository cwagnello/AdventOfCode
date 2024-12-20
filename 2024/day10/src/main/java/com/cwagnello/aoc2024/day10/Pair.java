package com.cwagnello.aoc2024.day10;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pair {
    private int row;
    private int column;

    @Override
    public String toString() {
        return row + "_" + column;
    }
}
