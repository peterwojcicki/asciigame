package com.freedom.model.enemies;

import com.freedom.display.Pencil;
import com.freedom.levels.Level;
import com.freedom.model.common.Collidible;
import com.freedom.model.common.Direction;
import com.freedom.model.common.DrawableRegister;
import com.freedom.model.weapons.VenomLauncher;
import com.googlecode.lanterna.TextColor;

public class Spider extends Enemy {

    public Spider(Collidible assignedArea, DrawableRegister drawableRegister, Level level) {
        super(assignedArea, new VenomLauncher(), drawableRegister, level);
    }

    @Override
    protected void drawStandingLeft(Pencil pencil) {
        int x = getPosition().getX();
        int y = getPosition().getY();

        pencil.setForegroundColor(TextColor.ANSI.MAGENTA);

        pencil.moveTo(x, y);

        pencil.println("     @@@");
        pencil.println("##===@@@");
        pencil.println("  ||||");
    }

    @Override
    protected void drawWalkingLeft(Pencil pencil) {
        int x = getPosition().getX();
        int y = getPosition().getY();

        pencil.setForegroundColor(TextColor.ANSI.MAGENTA);

        pencil.moveTo(x, y);
        pencil.println("     @@@");
        pencil.println("##===@@@");
        pencil.println("  /\\/\\");
    }

    @Override
    protected void drawStandingRight(Pencil pencil) {
        int x = getPosition().getX();
        int y = getPosition().getY();

        pencil.setForegroundColor(TextColor.ANSI.MAGENTA);

        pencil.moveTo(x, y);
        pencil.println("@@@");
        pencil.println("@@@===##");
        pencil.println("  ||||");
    }

    @Override
    protected void drawDead(Pencil pencil) {
        int x = getPosition().getX();
        int y = getPosition().getY();

        pencil.setForegroundColor(TextColor.ANSI.MAGENTA);

        pencil.moveTo(x, y);
        if (direction == Direction.RIGHT) {
            pencil.println("  ||||");
            pencil.println("@@@===##");
            pencil.println("@@@");
        } else if (direction == Direction.LEFT) {
            pencil.println("  ||||");
            pencil.println("##===@@@");
            pencil.println("     @@@");
        }
    }

    @Override
    protected void drawWalkingRight(Pencil pencil) {
        int x = getPosition().getX();
        int y = getPosition().getY();

        pencil.setForegroundColor(TextColor.ANSI.MAGENTA);

        pencil.moveTo(x, y);
        pencil.println("@@@");
        pencil.println("@@@===##");
        pencil.println("  /\\/\\");
    }

}
