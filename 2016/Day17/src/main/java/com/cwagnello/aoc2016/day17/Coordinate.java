package com.cwagnello.aoc2016.day17;

public record Coordinate(int row, int column) {

    public Coordinate(Coordinate coordinate) {
        this(coordinate.row(), coordinate.column());
    }
}
