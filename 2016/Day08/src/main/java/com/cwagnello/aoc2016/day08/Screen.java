package com.cwagnello.aoc2016.day08;

import lombok.Getter;

@Getter
public class Screen {
    private static final int ON = 1;

    private final int width;
    private final int height;
    private final int[][] screen;

    public Screen(int width, int height) {
        this.width = width;
        this.height = height;
        this.screen = new int[this.height][this.width];
    }

    public void setPixel(int row, int column) {
        this.screen[row][column] = ON;
    }

    public int getPixel(int row, int column) {
        return this.screen[row][column];
    }

    public void updateRow(int row, int[] updated) {
        this.screen[row] = updated;
    }

    public void updateColumn(int column, int[] updated) {
        for (int row = 0; row < height; row++) {
            screen[row][column] = updated[row];
        }
    }

    public int countOnPixels() {
        int count = 0;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                count += screen[row][col];
            }
        }
        return count;
    }

    public void print() {
        for (int row = 0; row < height; row++) {
            StringBuilder sb = new StringBuilder();
            for (int col = 0; col < width; col++) {
                sb.append( screen[row][col] == ON ? "#" : " ");
            }
            System.out.println(sb.toString());
        }
    }
}
