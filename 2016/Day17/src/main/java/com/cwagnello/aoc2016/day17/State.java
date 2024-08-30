package com.cwagnello.aoc2016.day17;

import java.util.ArrayList;
import java.util.List;

public class State {
    private Coordinate coordinate;
    private List<String> path;

    public State(Coordinate coordinate) {
        this.coordinate = coordinate;
        this.path = new ArrayList<>();
    }

    public State(Coordinate coordinate, List<String> path) {
        this.coordinate = new Coordinate(coordinate);
        this.path = new ArrayList<>(path);
    }

    public void addDirection(String direction) {
        this.path.add(direction);
    }

    public int row() {
        return this.coordinate.row();
    }

    public int column() {
        return this.coordinate.column();
    }

    public Coordinate coordinate() {
        return this.coordinate;
    }

    public List<String> path() {
        return this.path;
    }
}
