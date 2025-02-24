package com.cwagnello.aoc2024.day16;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
class Node {
    private int row;
    private int column;
    private Node step;
    private DIRECTION direction;
    private long score;

    public Node(final int row, final int col) {
        this(row, col, null, DIRECTION.EAST, Integer.MAX_VALUE);
    }

    public Node(final Node node) {
        this(node.row, node.column, node.step, node.direction, node.score);
    }

    @Override
    public String toString() {
        String format = "(%s,%s) score: %d, direction: %s";
        return String.format(format, row, column, score, direction);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof Node other)) {
            return false;
        }

        return this.getColumn() == other.getColumn() && this.getRow() == other.getRow();
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
