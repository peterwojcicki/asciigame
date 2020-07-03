package com.freedom.model.common;

public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point left() {
        return new Point(x - 1, y);
    }

    public Point right() {
        return new Point(x + 1, y);
    }

    public Point up() {
        return new Point(x, y - 1);
    }

    public Point down() {
        return new Point(x, y + 1);
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }

    public boolean isLowerThan(Point point) {
        return y > point.getY();
    }

    public boolean isHigherThan(Point point) {
        return y < point.getY();
    }

    public boolean isLeftOf(Point point) {
        return x < point.getX();
    }

    public boolean isRightOf(Point point) {
        return x > point.getX();
    }

}
