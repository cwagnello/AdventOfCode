package com.cwagnello.aoc2016.day11;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FloorState {
    private final int elevator;
    private List<Integer> bitmasks;
    private String hashCodeString;

    public FloorState(int elevator, List<Integer> bitmasks) {
        this.elevator = elevator;
        this.bitmasks = bitmasks;
        this.hashCodeString = hashCodeString();
    }

    public FloorState copy(FloorState state) {
        FloorState newFloorState = new FloorState(state.elevator, state.bitmasks);
        newFloorState.bitmasks = new ArrayList<>(state.bitmasks);
        return newFloorState;
    }

    public int elevator() {
        return this.elevator;
    }

    public int getFloorState(int floor) {
        return bitmasks.get(floor);
    }

    public List<Integer> floorStates() {
        return bitmasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FloorState that = (FloorState) o;
        return elevator == that.elevator && Objects.equals(bitmasks, that.bitmasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(elevator, bitmasks);
    }

    public String hashCodeString() {
        if (hashCodeString != null && !hashCodeString.isBlank()) {
            return this.hashCodeString;
        }
        StringBuilder sb = new StringBuilder();
        for (Integer bitmask : bitmasks) {
            sb.append(bitmask.toString());
            sb.append("_");
        }
        sb.append(elevator);
        this.hashCodeString = sb.toString();
        return hashCodeString;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Elevator: ");
        sb.append(elevator + 1);
        for (int i = bitmasks.size() - 1; i >= 0; i--) {
            sb.append("\n");
            sb.append(String.format("%010d", Long.parseLong(Integer.toBinaryString(bitmasks.get(i)))));
        }
        sb.append("\n");
        return sb.toString();
    }
}
