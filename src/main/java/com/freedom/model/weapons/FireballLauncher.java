package com.freedom.model.weapons;

import com.freedom.model.common.Direction;
import com.freedom.model.common.Point;
import com.freedom.sound.Audio;

public class FireballLauncher extends Weapon {

    private Audio swooshSound;

    public FireballLauncher(){
        swooshSound = new Audio("sounds/swoosh.wav");
    }

    @Override
    protected Projectile createNewProjectile(Point initialPosition, Direction direction) {
        new Thread(() -> swooshSound.playOnce()).start();
        return new Fireball(initialPosition, direction);
    }

    @Override
    public String getSymbol() {
        return "@";
    }
}
