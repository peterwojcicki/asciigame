package com.freedom.model.common;

public interface Graviteable extends Collidible {
    Point getPosition();
    void fall();
}
