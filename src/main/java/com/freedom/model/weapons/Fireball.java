package com.freedom.model.weapons;

import com.freedom.display.Pencil;
import com.freedom.model.common.Direction;
import com.freedom.model.common.Point;
import com.freedom.sound.Audio;
import com.googlecode.lanterna.TextColor;

public class Fireball extends Projectile {

    private Audio explosionSound;

    public Fireball(Point initialPosition, Direction direction) {
        super(initialPosition, direction, true);

        explosionSound = new Audio("sounds/explosion1.wav");
    }

    @Override
    protected void drawMovingLeft(Pencil pencil) {
        int x = getPosition().getX();
        int y = getPosition().getY();

        pencil.setForegroundColor(TextColor.ANSI.RED);
        pencil.moveTo(x, y);
        pencil.print('@');

        pencil.setForegroundColor(TextColor.ANSI.YELLOW);
        pencil.moveTo(x + 1, y);
        pencil.print("<<<<");
    }

    @Override
    protected void drawMovingRight(Pencil pencil) {
        int x = getPosition().getX();
        int y = getPosition().getY();

        pencil.setForegroundColor(TextColor.ANSI.YELLOW);
        pencil.moveTo(x, y);
        pencil.print(">>>>");

        pencil.setForegroundColor(TextColor.ANSI.RED);
        pencil.moveTo(x + 4, y);
        pencil.print('@');

    }

    @Override
    protected void playSoundOnHit() {
        new Thread(() -> explosionSound.playOnce()).start();
    }

}
