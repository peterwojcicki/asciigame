package com.freedom.model.weapons;

import com.freedom.model.common.Direction;
import com.freedom.model.common.Point;
import com.freedom.sound.Audio;
import com.googlecode.lanterna.Symbols;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Bow extends Weapon {

    private Audio arrowSound;

    public Bow(){
        arrowSound = new Audio("sounds/arrow.wav");
    }

    @Override
    protected List<Projectile> createNewProjectiles(Point initialPosition, Direction direction) {
        new Thread(() -> arrowSound.playOnce()).start();
        return Arrays.asList(new Arrow(initialPosition, direction));
    }

    @Override
    public String getSymbol() {
        return "" + Symbols.ARROW_UP;
    }
}
