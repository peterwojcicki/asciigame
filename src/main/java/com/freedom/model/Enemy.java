package com.freedom.model;

import com.freedom.display.Pencil;
import com.freedom.sound.Audio;
import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TextColor;

import java.util.Random;

public class Enemy extends Drawable implements Collidible, DamageInflicting {

    private final int speed;
    long globalFrame = 0;
    long localFrame = 0;
    Direction direction;
    Movement movement;
    final int height = 3;
    private Collidible assignedArea;
    private int life = 100;
    private boolean isDead = false;
    private Audio dyingSound;

    public Enemy(Collidible assignedArea) {
        super(Integer.MAX_VALUE - 10);
        this.assignedArea = assignedArea;

        // middle of the platform
        this.position = new Point(getRandomPosition(assignedArea), assignedArea.getUpperLeft().getY() - height);

        this.direction = getRandomDirection();
        this.movement = Movement.MOVING;
        this.speed = getRandomSpeed();

        dyingSound = new Audio("sounds/dying.wav");
    }

    private int getRandomPosition(Collidible assignedArea) {
        return Math.abs(new Random().nextInt()) % (assignedArea.getWidth() - 5) + assignedArea.getUpperLeft().getX();
    }

    private int getRandomSpeed() {
        return Math.abs(new Random().nextInt()) % 2 + 3;
    }

    private Direction getRandomDirection() {
        boolean goingRight = new Random().nextBoolean();
        return goingRight ? Direction.RIGHT : Direction.LEFT;
    }

    @Override
    public void draw(Pencil pencil) {
        globalFrame++;

        if (isDead) {
            drawDead(pencil);
        } else {

            if (movement == Movement.NONE) {
                if (direction == Direction.LEFT) {
                    drawStandingLeft(pencil);
                }
                if (direction == Direction.RIGHT) {
                    drawStandingRight(pencil);
                }
            } else {
                if (globalFrame % speed == 0) {
                    int numberOfFrames = 3;
                    localFrame = (localFrame % numberOfFrames) + 1;
                }

                if (direction == Direction.LEFT) {
                    if (localFrame == 1) {
                        drawWalkingLeft(pencil);
                    } else if (localFrame == 2) {
                        drawStandingLeft(pencil);
                    } else if (localFrame == 3) {
                        drawStandingLeft(pencil);
                    }
                }
                if (direction == Direction.RIGHT) {
                    if (localFrame == 1) {
                        drawWalkingRight(pencil);
                    } else if (localFrame == 2) {
                        drawStandingRight(pencil);
                    } else if (localFrame == 3) {
                        drawStandingRight(pencil);
                    }
                }
            }
        }
    }

    private void drawStandingLeft(Pencil pencil) {
        int x = getPosition().getX();
        int y = getPosition().getY();

        pencil.setForegroundColor(TextColor.ANSI.RED);

        pencil.moveTo(x, y);
        pencil.print('x');
        pencil.moveTo(x, y + 1);
        pencil.print('J');

        pencil.moveTo(x, y + 2);
        pencil.print('|');
    }

    private void drawWalkingLeft(Pencil pencil) {
        int x = getPosition().getX();
        int y = getPosition().getY();

        pencil.setForegroundColor(TextColor.ANSI.RED);

        pencil.moveTo(x, y);
        pencil.print('x');
        pencil.moveTo(x, y + 1);
        pencil.print('J');

        pencil.moveTo(x, y + 2);
        pencil.print('A');
    }

    private void drawStandingRight(Pencil pencil) {
        int x = getPosition().getX();
        int y = getPosition().getY();

        pencil.setForegroundColor(TextColor.ANSI.RED);

        pencil.moveTo(x, y);
        pencil.print('x');
        pencil.moveTo(x, y + 1);
        pencil.print('L');

        pencil.moveTo(x, y + 2);
        pencil.print('|');
    }

    private void drawDead(Pencil pencil) {
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

    private void drawWalkingRight(Pencil pencil) {
        int x = getPosition().getX();
        int y = getPosition().getY();

        pencil.setForegroundColor(TextColor.ANSI.RED);

        pencil.moveTo(x, y);
        pencil.print('x');
        pencil.moveTo(x, y + 1);
        pencil.print('L');

        pencil.moveTo(x, y + 2);
        pencil.print('A');
    }

    public void move() {
        int steps = 1;
        if (!isDead && globalFrame % speed == 0) {

            if (direction == Direction.LEFT) {
                int spaceToTheLeft = position.getX() - assignedArea.getUpperLeft().getX() - 1;
                if (spaceToTheLeft <= steps) {
                    direction = Direction.RIGHT;
                } else {
                    for (int i = 0; i < steps; i++) {
                        position = position.left();
                    }
                }
            } else if (direction == Direction.RIGHT) {
                int spaceToTheRight = assignedArea.getLowerRight().getX() - position.getX() - 1;
                if (spaceToTheRight <= steps) {
                    direction = Direction.LEFT;
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


    @Override
    public void hitByProjectile(Projectile projectile) {
        life = life - 34;

        if (life <= 0) {
            isDead = true;
            movement = Movement.NONE;

            new Thread(() -> dyingSound.playOnce()).start();
        }
    }

    public boolean isDead() {
        return isDead;
    }
}
