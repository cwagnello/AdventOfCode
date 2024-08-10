package com.cwagnello;

public class Keypad2 {
    private final static char[][] keypad = {
            {'.', '.', '1', '.', '.'},
            {'.', '2', '3', '4', '.'},
            {'5', '6', '7', '8', '9'},
            {'.', 'A', 'B', 'C', '.'},
            {'.', '.', 'D', '.', '.'}
    };
    private final Pair currentKey;
    private static Keypad2 INSTANCE;

    private Keypad2() {
        /* no public constructer */
        this.currentKey = new Pair(3, 0);
    }

    public static Keypad2 getKeypad() {
        if (INSTANCE == null) {
            INSTANCE = new Keypad2();
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
        if (dRow >= 0 && dRow < keypad.length && dCol >= 0 && dCol < keypad[0].length && keypad[dRow][dCol] != '.') {
            currentKey.setRow(dRow);
            currentKey.setCol(dCol);
        }
    }

    public char getValue() {
        return keypad[currentKey.getRow()][currentKey.getCol()];
    }
}
