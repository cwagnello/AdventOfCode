package com.cwagnello.aoc2016.day13;

import lombok.Getter;

import java.util.Objects;

public class Coordinate {
    @Getter
    private final int y;
    @Getter
    private final int x;
    private int sum;

    public Coordinate(final int y, final int x) {
        this.y = y;
        this.x = x;
    }

    public boolean isOpen() {
        if (sum == 0) {
            sum = x * x + 3 * x + 2 * x * y + y + y * y + Favorite.get();
        }
        return Integer.bitCount(sum) % 2 == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(y, x);
    }
}
