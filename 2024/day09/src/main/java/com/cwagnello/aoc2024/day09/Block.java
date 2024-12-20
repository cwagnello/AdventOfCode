package com.cwagnello.aoc2024.day09;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
abstract class Block {
    private int id;
    private int length;
}
