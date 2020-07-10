package com.freedom.model.enemies;

import com.freedom.display.Pencil;
import com.freedom.model.Player;
import com.freedom.model.common.*;
import com.freedom.model.weapons.Projectile;
import com.freedom.model.weapons.Weapon;
import com.freedom.sound.Audio;

import java.util.Random;

public abstract class Enemy extends Drawable implements Collidible, DamageInflicting {

    private final int speed;
    long globalFrame = 0;
    long localFrame = 0;
    Direction direction;
    Action action;
    final int height = 3;
    private Collidible assignedArea;
    private int life = 100;
    private boolean isDead = false;
    private Audio dyingSound;
    private long inflictedInjuryAtFrame;
    private long weaponUsedAtFrame;
    protected boolean diedFromExplosion;
    protected Weapon weapon;
    private DrawableRegister drawableRegister;

    public Enemy(Collidible assignedArea) {
        this(assignedArea, null, null);
    }

    public Enemy(Collidible assignedArea, Weapon weapon, DrawableRegister drawableRegister) {
        super(Integer.MAX_VALUE - 10);
        this.assignedArea = assignedArea;
        this.weapon = weapon;
        this.drawableRegister = drawableRegister;

        // middle of the platform
        this.position = new Point(getRandomPosition(assignedArea), assignedArea.getUpperLeft().getY() - height);

        this.direction = getRandomDirection();
        this.action = Action.MOVING;
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

            if (action == Action.NONE) {
                if (direction == Direction.LEFT) {
                    drawStandingLeft(pencil);
                }
                if (direction == Direction.RIGHT) {
                    drawStandingRight(pencil);
                }
            } else if (action == Action.EXPLODING) {
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

    protected void drawExplodingFrame1(Pencil pencil) {
    }

    protected void drawExplodingFrame2(Pencil pencil) {
    }

    protected void drawExplodingFrame3(Pencil pencil) {
    }

    protected void drawExplodingFrame4(Pencil pencil) {
    }

    protected void drawExplodingFrame5(Pencil pencil) {
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

        if (weapon != null) {
            if (isFacingPlayer(player) && isCloseEnoughToPlayerToShoot(player) && !isDead && (globalFrame - weaponUsedAtFrame > 50)) {
                weapon.shoot(position.down(), direction).stream().forEach(projectile -> drawableRegister.add(projectile));
                weaponUsedAtFrame = globalFrame;
            }
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


    private boolean isFacingPlayer(Player player) {
        if (Direction.LEFT.equals(direction) && player.getPosition().isLeftOf(position)) {
            return true;
        } else if (Direction.RIGHT.equals(direction) && player.getPosition().isRightOf(position)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isCloseEnoughToPlayer(Player player) {
        return Math.abs(player.getPosition().getX() - getPosition().getX()) < 2
                && Math.abs(player.getPosition().getY() - getPosition().getY()) < 2;
    }

    private boolean isCloseEnoughToPlayerToShoot(Player player) {
        return Math.abs(player.getPosition().getX() - getPosition().getX()) < 50
                && Math.abs(player.getPosition().getY() - getPosition().getY()) < 50;
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
        if (action == Action.EXPLODING) {
            return;
        }

        if (projectile.isCausesExplosion()) {
            action = Action.EXPLODING;
            localFrame = 1;
        } else {
            life = life - 34;
        }

        if (life <= 0) {
            die();
            new Thread(() -> dyingSound.playOnce()).start();
        }
    }

    private void die() {
        life = 0;
        isDead = true;
        action = Action.NONE;
    }

    public boolean isDead() {
        return isDead;
    }
}
