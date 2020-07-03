package com.freedom.model;

import com.freedom.display.Pencil;
import com.freedom.display.Style;
import com.freedom.model.common.Drawable;
import com.freedom.model.common.Point;
import com.googlecode.lanterna.TextColor;

public class Filler extends Drawable {

    int width;
    int height;
    private Style style;

    public Filler(Point initialPosition, int width, int height, Style style) {
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

}
