package com.cwagnello.aoc2016.day13;

public class Favorite {
    private static int favorite;
    private static boolean isSet;

    private Favorite() {}

    public static int get() {
        if (!isSet) {
            throw new IllegalStateException("Favorite number is NOT set");
        }
        return favorite;
    }

    public static void set(int favoriteNumber) {
        if (!isSet) {
            isSet = true;
            favorite = favoriteNumber;
        }
        else {
            throw new IllegalStateException("Favorite number is already set");
        }
    }
}
