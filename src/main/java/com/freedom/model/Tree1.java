package com.freedom.model;

import com.freedom.display.Pencil;
import com.googlecode.lanterna.TextColor;

public class Tree1 extends Drawable {

    public Tree1(Point initialPosition) {
        super(100);
        this.position = initialPosition;
    }

    @Override
    public void draw(Pencil pencil) {

        int x = position.getX();
        int y = position.getY();

        pencil.setForegroundColor(TextColor.ANSI.BLACK);

        pencil.moveTo(x, y);

        pencil.println("                   ");
        pencil.println("                   ");
        pencil.println("                   ");
        pencil.println("         \\/       ");
        pencil.println("          --        ");
        pencil.println("          ||      ");
        pencil.println("          ||    O  ");
        pencil.println("          ||   /   ");
        pencil.println("          ||  /    ");
        pencil.println("          || /\\     ");
        pencil.println("          ||/  O       ");
        pencil.println("          ||        ");
        pencil.println("          ||        ");
        pencil.println("          ||        ");
        pencil.println("          ||        ");
        pencil.println("          ||        ");

    }

    public Point getPosition() {
        return position;
    }
}
