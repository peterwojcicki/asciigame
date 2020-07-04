package com.freedom.model.weapons;

import com.freedom.display.Pencil;
import com.freedom.model.common.Direction;
import com.freedom.model.common.Point;
import com.freedom.sound.Audio;
import com.googlecode.lanterna.TextColor;

public class SonicShockwave extends Projectile {

    private Audio explosionSound;

    public SonicShockwave(Point initialPosition, Direction direction) {
        super(initialPosition, direction, true);

        explosionSound = new Audio("sounds/explosion1.wav");
    }

    @Override
    protected void drawMovingLeft(Pencil pencil) {
        int x = getPosition().getX();
        int y = getPosition().getY();

        pencil.setForegroundColor(TextColor.Factory.fromString("#9933ff"));
        pencil.moveTo(x, y);
        pencil.print("(((((");
    }

    @Override
    protected void drawMovingRight(Pencil pencil) {
        int x = getPosition().getX();
        int y = getPosition().getY();

        pencil.setForegroundColor(TextColor.Factory.fromString("#9933ff"));
        pencil.moveTo(x - 5, y);
        pencil.print(")))))");

    }

    @Override
    protected void playSoundOnHit() {
        new Thread(() -> explosionSound.playOnce()).start();
    }

}
