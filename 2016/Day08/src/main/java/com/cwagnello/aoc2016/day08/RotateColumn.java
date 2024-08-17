package com.cwagnello.aoc2016.day08;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RotateColumn implements Instruction {
    private final int column;
    private final int shift;

    @Override
    public void apply(Screen screen) {
        int height = screen.getHeight();
        int[] updatedColumn = new int[height];
        for (int row = 0; row < height; row++) {
            updatedColumn[(row + shift) % height] = screen.getPixel(row, column);
        }
        screen.updateColumn(column, updatedColumn);
    }
}
