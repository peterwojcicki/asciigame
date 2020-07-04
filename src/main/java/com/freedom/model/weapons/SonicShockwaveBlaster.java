package com.freedom.model.weapons;

import com.freedom.model.common.Direction;
import com.freedom.model.common.Point;
import com.freedom.sound.Audio;
import com.googlecode.lanterna.Symbols;

import java.util.Arrays;
import java.util.List;

public class SonicShockwaveBlaster extends Weapon {

    private Audio swooshSound;

    public SonicShockwaveBlaster() {
        swooshSound = new Audio("sounds/swoosh.wav");
    }

    @Override
    protected List<Projectile> createNewProjectiles(Point initialPosition, Direction direction) {
        new Thread(() -> swooshSound.playOnce()).start();

        Direction oppositeDirection;
        if (direction == Direction.LEFT) {
            oppositeDirection = Direction.RIGHT;
        } else {
            oppositeDirection = Direction.LEFT;
        }

        return Arrays.asList(new SonicShockwave(initialPosition, direction), new SonicShockwave(initialPosition, oppositeDirection));
    }

    @Override
    public String getSymbol() {
        return "((" + Symbols.BULLET + "))";
    }
}
