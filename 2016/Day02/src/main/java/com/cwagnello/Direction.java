package com.cwagnello;

public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    public static Direction value(String direction) {
        return switch(direction) {
            case "U" -> UP;
            case "D" -> DOWN;
            case "L" -> LEFT;
            case "R" -> RIGHT;
            default -> throw new IllegalArgumentException("Invalid direction: " + direction);
        };
    }
}
