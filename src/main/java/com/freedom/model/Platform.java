package com.freedom.model;

import com.freedom.display.Pencil;
import com.freedom.display.Style;
import com.googlecode.lanterna.TextColor;

public class Platform extends Drawable implements Collidible {

    private int width;
    private int height;
    private Style style;

    public Platform(Point initialPosition, int width, int height, Style style) {
        super(200);
        this.width = width;
        this.height = height;
        this.style = style;
        this.position = initialPosition;
    }

    @Override
    public void draw(Pencil pencil) {

        int x = position.getX();
        int y = position.getY();

        pencil.setForegroundColor(TextColor.Factory.fromString(style.getFrontColour()));

        pencil.moveTo(x, y);

        String line = "";
        for (int j = 0; j < width; j++) {
            line += style.getCharacter();
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
        return new Point(getPosition().getX() + width - 1, getPosition().getY() + height - 1);
    }
}
