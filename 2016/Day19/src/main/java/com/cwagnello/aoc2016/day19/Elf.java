package com.cwagnello.aoc2016.day19;

import lombok.Data;

@Data
public class Elf {
    private int id;
    private Elf left;

    public Elf(int id, Elf left) {
        this.id = id;
        this.left = left;
    }
}
