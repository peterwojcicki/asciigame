package com.freedom.model;

import com.freedom.display.Pencil;
import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TextColor;

public class Soldier extends Enemy {

    public Soldier(Collidible assignedArea) {
        super(assignedArea);
    }

    @Override
    protected void drawStandingLeft(Pencil pencil) {
        int x = getPosition().getX();
        int y = getPosition().getY();

        pencil.setForegroundColor(TextColor.ANSI.RED);

        pencil.moveTo(x, y);
        pencil.print(Symbols.BULLET);
        pencil.moveTo(x - 1, y + 1);
        pencil.print("" + Symbols.DOUBLE_LINE_BOTTOM_LEFT_CORNER + Symbols.TRIANGLE_DOWN_POINTING_BLACK);

        pencil.moveTo(x, y + 2);
        pencil.print('|');
    }

    @Override
    protected void drawWalkingLeft(Pencil pencil) {
        int x = getPosition().getX();
        int y = getPosition().getY();

        pencil.setForegroundColor(TextColor.ANSI.RED);

        pencil.moveTo(x, y);
        pencil.print(Symbols.BULLET);
        pencil.moveTo(x - 1, y + 1);
        pencil.print("" + Symbols.DOUBLE_LINE_BOTTOM_LEFT_CORNER +  Symbols.TRIANGLE_DOWN_POINTING_BLACK);

        pencil.moveTo(x, y + 2);
        pencil.print('A');
    }

    @Override
    protected void drawStandingRight(Pencil pencil) {
        int x = getPosition().getX();
        int y = getPosition().getY();

        pencil.setForegroundColor(TextColor.ANSI.RED);

        pencil.moveTo(x, y);
        pencil.print(Symbols.BULLET);
        pencil.moveTo(x, y + 1);
        pencil.print("" + Symbols.TRIANGLE_DOWN_POINTING_BLACK + Symbols.DOUBLE_LINE_BOTTOM_RIGHT_CORNER);
        pencil.moveTo(x, y + 2);
        pencil.print('|');
    }

    @Override
    protected void drawDead(Pencil pencil) {
        int x = getPosition().getX();
        int y = getPosition().getY();

        pencil.setForegroundColor(TextColor.ANSI.RED);

        if (direction == Direction.RIGHT) {
            pencil.moveTo(x, y + 2);
            pencil.print("x-" + Symbols.SINGLE_LINE_BOTTOM_RIGHT_CORNER + "-" + Symbols.SINGLE_LINE_BOTTOM_RIGHT_CORNER);
        } else if (direction == Direction.LEFT) {
            pencil.moveTo(x, y + 2);
            pencil.print(Symbols.SINGLE_LINE_BOTTOM_LEFT_CORNER + "-" + Symbols.SINGLE_LINE_BOTTOM_LEFT_CORNER + "-x");
        }
    }

    @Override
    protected void drawWalkingRight(Pencil pencil) {
        int x = getPosition().getX();
        int y = getPosition().getY();

        pencil.setForegroundColor(TextColor.ANSI.RED);

        pencil.moveTo(x, y);
        pencil.print(Symbols.BULLET);
        pencil.moveTo(x, y + 1);
        pencil.print("" + Symbols.TRIANGLE_DOWN_POINTING_BLACK + Symbols.DOUBLE_LINE_BOTTOM_RIGHT_CORNER);

        pencil.moveTo(x, y + 2);
        pencil.print('A');
    }

}
