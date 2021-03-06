package com.freedom.model.collectibles;

import com.freedom.display.Pencil;
import com.freedom.model.common.Collidible;
import com.freedom.model.common.Drawable;
import com.freedom.model.common.Point;
import com.freedom.sound.Audio;
import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TextColor;

import java.util.function.Consumer;

public abstract class Collectible extends Drawable {

    private boolean isCollected = false;
    private Consumer<Collectible> onCollectEvent;
    private Audio collectSound;

    public Collectible(Point initialPosition, Consumer<Collectible> onCollectEvent) {
        super(Integer.MAX_VALUE);

        this.onCollectEvent = onCollectEvent;
        this.position = initialPosition;
        this.collectSound = new Audio("sounds/collect.wav");
    }

    @Override
    public void draw(Pencil pencil) {
        int x = position.getX();
        int y = position.getY();

        pencil.setForegroundColor(TextColor.ANSI.RED);
        pencil.moveTo(x, y);

        pencil.print(getSymbol());
    }

    protected abstract String getSymbol();

    public Point getPosition() {
        return position;
    }

    public void collect() {
        if (!isCollected) {
            onCollectEvent.accept(this);
            isCollected = true;

            new Thread(() -> collectSound.playOnce()).start();
        }
    }

    public boolean isCollected() {
        return isCollected;
    }

    public boolean isWithinReach(Collidible collidible) {
        return Math.abs(collidible.getPosition().getX() - getPosition().getX()) < 2
                && Math.abs(collidible.getPosition().getY() - getPosition().getY()) < 2;
    }
}
