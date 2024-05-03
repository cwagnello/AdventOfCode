package com.cwagnello;

/**
 *
 * @author cwagnello
 */
public class Point {
    private int x;
    private int y;

    Point() {
        this.x = 0;
        this.y = 0;
    }

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    public Point moveUp() {
        return new Point(this.x, this.y + 1);
    }

    public Point moveDown() {
        return new Point(this.x, this.y - 1);
    }

    public Point moveLeft() {
        return new Point(this.x - 1, this.y);
    }

    public Point moveRight() {
        return new Point(this.x + 1, this.y);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Point other = (Point) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = x * 997 + y;
        return 0 ^ hash;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
