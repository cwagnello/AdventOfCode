package com.cwagnello.aoc2024.day14;

import lombok.Data;

@Data
public class Robot {
    private Coordinate position;
    private Velocity velocity;

    public Robot(Coordinate position, Velocity velocity) {
        this.position = position;
        this.velocity = velocity;
    }

    public long getPositionX() {
        return this.position.getX();
    }

    public long getPositionY() {
        return this.position.getY();
    }

    public long getVelocityX() {
        return this.velocity.x();
    }

    public long getVelocityY() {
        return this.velocity.y();
    }
}
