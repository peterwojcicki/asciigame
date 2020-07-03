package com.freedom.model.weapons;

import com.freedom.model.common.Direction;
import com.freedom.model.common.Point;
import com.freedom.sound.Audio;

public class Bow extends Weapon {

    private Audio arrowSound;

    public Bow(){
        arrowSound = new Audio("sounds/arrow.wav");
    }

    @Override
    protected Projectile createNewProjectile(Point initialPosition, Direction direction) {
        new Thread(() -> arrowSound.playOnce()).start();
        return new Arrow(initialPosition, direction);
    }
}
