package com.freedom.model;

import com.freedom.display.Style;

public class Platform extends Filler implements Collidible {

    public Platform(Point initialPosition, int width, int height, Style style) {
        super(initialPosition, width, height, style);
    }

    @Override
    public Point getUpperLeft() {
        return getPosition();
    }

    @Override
    public Point getLowerRight() {
        return new Point(getPosition().getX() + width - 1, getPosition().getY() + height - 1);
    }
}
