package com.cwagnello.aoc2016.day08;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Rectangle implements Instruction {
    private int width;
    private int height;

    @Override
    public void apply(Screen screen) {
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                screen.setPixel(row, column);
            }
        }
    }
}
