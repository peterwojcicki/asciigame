package com.freedom.model.weapons;

import com.freedom.model.common.Direction;
import com.freedom.model.common.Point;
import com.freedom.sound.Audio;
import com.googlecode.lanterna.Symbols;

import java.util.Arrays;
import java.util.List;

public class Pistol extends Weapon {

    private Audio arrowSound;

    public Pistol() {
        arrowSound = new Audio("sounds/rifle.wav");
    }

    @Override
    protected List<Projectile> createNewProjectiles(Point initialPosition, Direction direction) {
        new Thread(() -> arrowSound.playOnce()).start();
        return Arrays.asList(new Bullet(initialPosition, direction));
    }

    @Override
    public String getSymbol() {
        return "" + Symbols.BULLET;
    }
}
