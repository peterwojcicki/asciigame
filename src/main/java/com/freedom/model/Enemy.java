package com.freedom.model;

import com.freedom.display.Pencil;
import com.freedom.sound.Audio;
import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TextColor;

import java.util.Random;

public abstract class Enemy extends Drawable implements Collidible, DamageInflicting {

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
    private Audio explosionSound;
    private long inflictedInjuryAtFrame;
    protected boolean diedFromExplosion;

    public Enemy(Collidible assignedArea) {
        super(Integer.MAX_VALUE - 10);
        this.assignedArea = assignedArea;

        // middle of the platform
        this.position = new Point(getRandomPosition(assignedArea), assignedArea.getUpperLeft().getY() - height);

        this.direction = getRandomDirection();
        this.movement = Movement.MOVING;
        this.speed = getRandomSpeed();

        dyingSound = new Audio("sounds/dying.wav");
        explosionSound = new Audio("sounds/explosion1.wav");
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
            } else if (movement == Movement.EXPLODING) {
                if (globalFrame % 5 == 0) {
                    int numberOfFrames = 5;
                    localFrame = (localFrame % numberOfFrames) + 1;
                }

                if (localFrame == 1) {
                    drawExplodingFrame1(pencil);
                } else if (localFrame == 2) {
                    drawExplodingFrame2(pencil);
                } else if (localFrame == 3) {
                    drawExplodingFrame3(pencil);
                } else if (localFrame == 4) {
                    drawExplodingFrame4(pencil);
                } else if (localFrame == 5) {
                    drawExplodingFrame5(pencil);
                    die();
                    diedFromExplosion = true;
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

    private void drawExplodingFrame1(Pencil pencil) {
        int x = getPosition().getX();
        int y = getPosition().getY();

        pencil.setForegroundColor(TextColor.ANSI.RED);

        pencil.moveTo(x, y - 2);
        pencil.print(Symbols.BULLET);
        pencil.moveTo(x - 1, y - 1);
        pencil.print("\\   /" + Symbols.TRIANGLE_DOWN_POINTING_BLACK);
        pencil.moveTo(x + 1, y);
        pencil.print(Symbols.TRIANGLE_DOWN_POINTING_BLACK);
        pencil.moveTo(x, y);
        pencil.print("| |");
    }

    private void drawExplodingFrame2(Pencil pencil) {
        int x = getPosition().getX();
        int y = getPosition().getY();

        pencil.setForegroundColor(TextColor.ANSI.RED);

        pencil.moveTo(x, y - 3);
        pencil.print(Symbols.BULLET);
        pencil.moveTo(x - 2, y - 2);
        pencil.print("\\    /" + Symbols.TRIANGLE_DOWN_POINTING_BLACK);
        pencil.moveTo(x + 1, y - 1);
        pencil.print(Symbols.TRIANGLE_DOWN_POINTING_BLACK);
        pencil.moveTo(x, y - 1);
        pencil.print("| |");
    }

    private void drawExplodingFrame3(Pencil pencil) {
        int x = getPosition().getX();
        int y = getPosition().getY();

        pencil.setForegroundColor(TextColor.ANSI.RED);

        pencil.moveTo(x, y - 2);
        pencil.print(Symbols.BULLET);
        pencil.moveTo(x - 2, y - 1);
        pencil.print("\\        /" + Symbols.TRIANGLE_DOWN_POINTING_BLACK);
        pencil.moveTo(x + 2, y);
        pencil.print(Symbols.TRIANGLE_DOWN_POINTING_BLACK);
        pencil.moveTo(x - 1, y );
        pencil.print("|  |");
    }

    private void drawExplodingFrame4(Pencil pencil) {
        int x = getPosition().getX();
        int y = getPosition().getY();

        pencil.setForegroundColor(TextColor.ANSI.RED);

        pencil.moveTo(x, y );
        pencil.print(Symbols.BULLET);
        pencil.moveTo(x - 3, y + 1);
        pencil.print("\\          /" + Symbols.TRIANGLE_DOWN_POINTING_BLACK);
        pencil.moveTo(x + 2, y + 1);
        pencil.print(Symbols.TRIANGLE_DOWN_POINTING_BLACK);
        pencil.moveTo(x - 2, y + 1);
        pencil.print("|    |");
    }

    private void drawExplodingFrame5(Pencil pencil) {
        int x = getPosition().getX();
        int y = getPosition().getY();

        pencil.setForegroundColor(TextColor.ANSI.RED);

        pencil.moveTo(x, y );
        pencil.print(Symbols.BULLET);
        pencil.moveTo(x - 4, y + 2);
        pencil.print("\\            /" + Symbols.TRIANGLE_DOWN_POINTING_BLACK);
        pencil.moveTo(x + 3, y + 2);
        pencil.print(Symbols.TRIANGLE_DOWN_POINTING_BLACK);
        pencil.moveTo(x - 2, y + 2);
        pencil.print("|    |");
    }

    protected abstract void drawStandingLeft(Pencil pencil);

    protected abstract void drawWalkingLeft(Pencil pencil);

    protected abstract void drawStandingRight(Pencil pencil);

    protected abstract void drawDead(Pencil pencil);

    protected abstract void drawWalkingRight(Pencil pencil);

    public void move(Player player) {
        int steps = 1;

        if (!isDead && isCloseEnoughToPlayer(player) && globalFrame - inflictedInjuryAtFrame > 500) {
            player.injure();
            inflictedInjuryAtFrame = globalFrame;
        }

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

    private boolean isCloseEnoughToPlayer(Player player) {
        return Math.abs(player.getPosition().getX() - getPosition().getX()) < 2
                && Math.abs(player.getPosition().getY() - getPosition().getY()) < 2;
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
        if (movement == Movement.EXPLODING) {
            return;
        }

        if (projectile.isCausesExplosion()) {
            movement = Movement.EXPLODING;
            localFrame = 1;

            new Thread(() -> explosionSound.playOnce()).start();
        } else {
            life = life - 34;

            if (life <= 0) {
                new Thread(() -> dyingSound.playOnce()).start();
            }
        }

        if (life <= 0) {
            die();
        }
    }

    private void die() {
        life = 0;
        isDead = true;
        movement = Movement.NONE;
    }

    public boolean isDead() {
        return isDead;
    }
}
