package com.freedom.model;

import com.freedom.display.Pencil;
import com.googlecode.lanterna.TextColor;

public class Fireball extends Projectile {

    public Fireball(Point initialPosition, Direction direction) {
        super(initialPosition, direction, true);
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

}
