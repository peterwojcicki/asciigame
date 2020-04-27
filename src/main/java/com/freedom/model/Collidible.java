package com.freedom.model;

public interface Collidible {

    Point getUpperLeft();

    Point getLowerRight();

    default boolean isTouching(Collidible other) {
        return other.getLowerRight().getY() + 1 == getUpperLeft().getY()
                ||  getLowerRight().getY() + 1 == other.getUpperLeft().getY();
    }
}
