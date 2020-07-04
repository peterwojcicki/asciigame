package com.freedom.model.weapons;

import com.freedom.model.common.Direction;
import com.freedom.model.common.Point;
import com.freedom.sound.Audio;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FireballLauncher extends Weapon {

    private Audio swooshSound;

    public FireballLauncher(){
        swooshSound = new Audio("sounds/swoosh.wav");
    }

    @Override
    protected List<Projectile> createNewProjectiles(Point initialPosition, Direction direction) {
        new Thread(() -> swooshSound.playOnce()).start();
        return Arrays.asList(new Fireball(initialPosition, direction));
    }

    @Override
    public String getSymbol() {
        return "@";
    }
}
