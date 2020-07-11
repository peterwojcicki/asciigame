package com.freedom.model.weapons;

import com.freedom.display.Pencil;
import com.freedom.model.common.Direction;
import com.freedom.model.common.Point;
import com.freedom.sound.Audio;
import com.googlecode.lanterna.TextColor;

public class Venom extends Projectile {

    public Venom(Point initialPosition, Direction direction) {
        super(initialPosition, direction, true);
    }

    @Override
    protected void drawMovingLeft(Pencil pencil) {
        int x = getPosition().getX();
        int y = getPosition().getY();

        pencil.setForegroundColor(TextColor.ANSI.YELLOW);
        pencil.moveTo(x, y);
        pencil.print("<<~~~~");
    }

    @Override
    protected void drawMovingRight(Pencil pencil) {
        int x = getPosition().getX();
        int y = getPosition().getY();

        pencil.setForegroundColor(TextColor.ANSI.YELLOW);
        pencil.moveTo(x, y);
        pencil.print("~~~>>");
    }
}
