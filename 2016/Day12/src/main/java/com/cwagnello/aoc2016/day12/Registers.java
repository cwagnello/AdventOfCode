package com.cwagnello.aoc2016.day12;

import java.util.HashMap;
import java.util.Map;

public final class Registers {
    private static Map<RegisterName, Integer> map;
    private static Registers registers;

    private Registers() {
        map = new HashMap<>();
        for (RegisterName name : RegisterName.values()) {
            map.put(name, 0);
        }
    }

    public static Registers getInstance() {
        if (registers == null) {
            registers = new Registers();
        }
        return registers;
    }

    public void set(RegisterName registerName, int value) {
        map.put(registerName, value);
    }

    public int get(RegisterName registerName) {
        return map.get(registerName);
    }

    public void reset() {
        for (RegisterName name : RegisterName.values()) {
            map.put(name, 0);
        }
    }
}
