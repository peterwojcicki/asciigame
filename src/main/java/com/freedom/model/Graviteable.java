package com.freedom.model;

public interface Graviteable extends Collidible {
    Point getPosition();
    void fall();
}
