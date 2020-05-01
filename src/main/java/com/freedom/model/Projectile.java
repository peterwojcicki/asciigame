package com.freedom.model;

import com.freedom.display.Pencil;
import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TextColor;

public class Projectile extends Drawable implements Collidible, DamageInflicting {

    public static final int MAX_RANGE = 50;
    long globalFrame = 0;
    long localFrame = 0;
    Direction direction;
    Movement movement;
    final int height = 1;
    private boolean isActive;
    private Point initialPosition;

    public Projectile(Point initialPosition, Direction direction) {
        super(Integer.MAX_VALUE - 10);

        this.initialPosition = initialPosition;
        this.position = initialPosition;

        this.direction = direction;
        this.movement = Movement.MOVING;
        this.isActive = true;
    }

    @Override
    public void draw(Pencil pencil) {
        if (!isActive) {
            return;
        }

        globalFrame++;

        if (globalFrame % 4 == 0) {
            localFrame++;
        }

        if (direction == Direction.LEFT) {
            drawMovingLeft(pencil);
        }
        if (direction == Direction.RIGHT) {
            drawMovingRight(pencil);
        }
    }

    private void drawMovingLeft(Pencil pencil) {
        int x = getPosition().getX();
        int y = getPosition().getY();

        pencil.setForegroundColor(TextColor.ANSI.GREEN);

        pencil.moveTo(x, y);
        pencil.print(Symbols.ARROW_LEFT);
    }

    private void drawMovingRight(Pencil pencil) {
        int x = getPosition().getX();
        int y = getPosition().getY();

        pencil.setForegroundColor(TextColor.ANSI.GREEN);

        pencil.moveTo(x, y);
        pencil.print(Symbols.ARROW_RIGHT);
    }

    public void move(Collidible nearestCollidibleToLeft, Collidible nearestCollidibleToRight) {

        if (getTravelledDistance() > MAX_RANGE) {
            isActive = false;
            return;
        }

        int steps = 1;
        if (globalFrame % 1 == 0) {

            if (direction == Direction.LEFT) {
                if (nearestCollidibleToLeft != null) {
                    int spaceToTheLeft = position.getX() - nearestCollidibleToLeft.getLowerRight().getX() - 1;
                    if (spaceToTheLeft <= steps) {
                        nearestCollidibleToLeft.hitByProjectile(this);
                        isActive = false;
                    } else {
                        for (int i = 0; i < steps; i++) {
                            position = position.left();
                        }
                    }
                } else {
                    for (int i = 0; i < steps; i++) {
                        position = position.left();
                    }
                }
            } else if (direction == Direction.RIGHT) {
                if (nearestCollidibleToRight != null) {
                    int spaceToTheRight = nearestCollidibleToRight.getUpperLeft().getX() - position.getX() - 1;
                    if (spaceToTheRight <= steps) {
                        nearestCollidibleToRight.hitByProjectile(this);
                        isActive = false;
                    } else {
                        for (int i = 0; i < steps; i++) {
                            position = position.right();
                        }
                    }
                } else {
                    for (int i = 0; i < steps; i++) {
                        position = position.right();
                    }
                }
            }
        }
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public Point getUpperLeft() {
        return getPosition();
    }

    @Override
    public Point getLowerRight() {
        return new Point(getPosition().getX(), getPosition().getY() + height - 1);
    }

    public boolean isActive() {
        return isActive;
    }

    private int getTravelledDistance() {
        return Math.abs(initialPosition.getX() - position.getX());
    }
}
