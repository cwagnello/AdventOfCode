package com.cwagnello.aoc2016.day08;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RotateRow implements Instruction {
    private final int row;
    private final int shift;

    @Override
    public void apply(Screen screen) {
        int width = screen.getWidth();
        int[] updatedRow = new int[width];
        for (int column = 0; column < width; column++) {
            updatedRow[(column + shift) % width] = screen.getPixel(row, column);
        }
        screen.updateRow(row, updatedRow);
    }
}
