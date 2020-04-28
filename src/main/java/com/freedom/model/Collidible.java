package com.freedom.model;

public interface Collidible {

    Point getUpperLeft();

    Point getLowerRight();

    default boolean isTouchingVertically(Collidible other) {
        return other.getLowerRight().getY() + 1 == getUpperLeft().getY()
                ||  getLowerRight().getY() + 1 == other.getUpperLeft().getY();
    }

    default boolean isTouchingHorizontally(Collidible other) {
        return other.getLowerRight().getX() + 1 == getUpperLeft().getX()
                ||  getLowerRight().getX() + 1 == other.getUpperLeft().getX();
    }
}
