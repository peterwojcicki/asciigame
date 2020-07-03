package com.freedom.model.weapons;

import com.freedom.display.Pencil;
import com.freedom.model.common.Direction;
import com.freedom.model.common.Point;
import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TextColor;

public class Arrow extends Projectile {

    public Arrow(Point initialPosition, Direction direction) {
        super(initialPosition, direction, false);
    }

    @Override
    protected void drawMovingLeft(Pencil pencil) {
        int x = getPosition().getX();
        int y = getPosition().getY();

        pencil.setForegroundColor(TextColor.ANSI.GREEN);

        pencil.moveTo(x, y);
        pencil.print(Symbols.ARROW_LEFT);
    }

    @Override
    protected void drawMovingRight(Pencil pencil) {
        int x = getPosition().getX();
        int y = getPosition().getY();

        pencil.setForegroundColor(TextColor.ANSI.GREEN);

        pencil.moveTo(x, y);
        pencil.print(Symbols.ARROW_RIGHT);
    }

}
