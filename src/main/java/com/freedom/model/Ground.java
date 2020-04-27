package com.freedom.model;

import com.freedom.display.Pencil;
import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TextColor;

public class Ground extends Drawable implements Collidible {

    private int width;
    private int height;

    public Ground(Point initialPosition, int width, int height) {
        super(200);
        this.width = width;
        this.height = height;
        this.position = initialPosition;
    }

    @Override
    public void draw(Pencil pencil) {

        int x = position.getX();
        int y = position.getY();

        pencil.setForegroundColor(TextColor.ANSI.BLACK);

        pencil.moveTo(x, y);

        String line = "";
        for (int j = 0; j < width; j++) {
            line += Symbols.TRIANGLE_DOWN_POINTING_BLACK;
        }

        for (int i = 0; i < height; i++) {
            pencil.println(line);
        }
    }

    public Point getPosition() {
        return position;
    }

    @Override
    public Point getUpperLeft() {
        return getPosition();
    }

    @Override
    public Point getLowerRight() {
        return new Point(getPosition().getX() + width, getPosition().getY() + height);
    }
}
