package com.cwagnello;

public final class Keypad1 {
    private final static int[][] keypad = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    private final Pair currentKey;
    private static Keypad1 INSTANCE;

    private Keypad1() {
        /* no public constructer */
        this.currentKey = new Pair(1, 1);
    }

    public static Keypad1 getKeypad() {
        if (INSTANCE == null) {
            INSTANCE = new Keypad1();
        }
        return INSTANCE;
    }

    public void apply(Direction direction) {
        int dRow = currentKey.getRow();
        int dCol = currentKey.getCol();
        switch (direction) {
            case UP -> dRow -= 1;
            case DOWN -> dRow += 1;
            case LEFT -> dCol -= 1;
            case RIGHT -> dCol += 1;
        }
        if (dRow >= 0 && dRow < keypad.length && dCol >= 0 && dCol < keypad[0].length) {
            currentKey.setRow(dRow);
            currentKey.setCol(dCol);
        }
    }

    public int getValue() {
        return keypad[currentKey.getRow()][currentKey.getCol()];
    }
}
