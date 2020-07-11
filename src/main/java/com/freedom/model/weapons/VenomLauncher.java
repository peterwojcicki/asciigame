package com.freedom.model.weapons;

import com.freedom.model.common.Direction;
import com.freedom.model.common.Point;
import com.freedom.sound.Audio;

import java.util.Arrays;
import java.util.List;

public class VenomLauncher extends Weapon {

    private Audio swooshSound;

    public VenomLauncher(){
        swooshSound = new Audio("sounds/swoosh.wav");
    }

    @Override
    protected List<Projectile> createNewProjectiles(Point initialPosition, Direction direction) {
        new Thread(() -> swooshSound.playOnce()).start();
        return Arrays.asList(new Venom(initialPosition, direction));
    }

    @Override
    public String getSymbol() {
        return "@";
    }
}
