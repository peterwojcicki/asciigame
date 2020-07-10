package com.freedom.model;

import com.freedom.display.Pencil;
import com.freedom.model.common.*;
import com.freedom.model.weapons.*;
import com.freedom.sound.Audio;
import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TextColor;

import java.util.ArrayList;
import java.util.List;

public class Player extends Drawable implements Collidible, Graviteable {

    long globalFrame = 0;
    long localFrame = 0;
    Direction direction;
    Action action;
    final int height = 3;
    private DrawableRegister drawableRegister;

    private int health = 100;

    private Weapon currentWeapon;
    private int currentWeaponIndex = 0;
    private List<Weapon> weapons = new ArrayList<>();

    private Audio hurtSound;

    public Player(Point initialPosition, DrawableRegister drawableRegister) {
        super(Integer.MAX_VALUE);
        this.drawableRegister = drawableRegister;
        this.position = initialPosition;

        this.direction = Direction.RIGHT;
        this.action = Action.NONE;

        weapons.add(new Bow());
        weapons.add(new FireballLauncher());
        weapons.add(new SonicShockwaveBlaster());
        currentWeapon = weapons.get(currentWeaponIndex);

        hurtSound = new Audio("sounds/player_hurt.wav");
    }

    @Override
    public void draw(Pencil pencil) {
        globalFrame++;

        if (action == Action.NONE) {
            if (direction == Direction.LEFT) {
                drawStandingLeft(pencil);
            }
            if (direction == Direction.RIGHT) {
                drawStandingRight(pencil);
            }
        } else if (action == Action.CRAWLING) {
            if (direction == Direction.LEFT) {
                drawCrawlingLeft(pencil);
            }
            if (direction == Direction.RIGHT) {
                drawCrawlingRight(pencil);
            }
        } else {
            if (globalFrame % 2 == 0) {
                localFrame++;
            }

            if (direction == Direction.LEFT) {
                if (localFrame == 1) {
                    drawWalkingLeft(pencil);
                } else if (localFrame == 2) {
                    drawStandingLeft(pencil);
                } else if (localFrame > 2) {
                    localFrame = 0;
                    action = Action.NONE;
                    drawStandingLeft(pencil);
                }
            }
            if (direction == Direction.RIGHT) {
                if (localFrame == 1) {
                    drawWalkingRight(pencil);
                } else if (localFrame == 2) {
                    drawStandingRight(pencil);
                } else if (localFrame > 2) {
                    localFrame = 0;
                    action = Action.NONE;
                    drawStandingRight(pencil);
                }
            }
        }
    }

    private void drawStandingLeft(Pencil pencil) {
        int x = pencil.getTerminalSize().getColumns() / 2;
        int y = pencil.getTerminalSize().getRows() / 2;

        pencil.setForegroundColor(TextColor.ANSI.RED);

        pencil.moveToAbsolute(x + 1, y);
        pencil.print(Symbols.TRIANGLE_LEFT_POINTING_BLACK);

        pencil.setForegroundColor(TextColor.ANSI.BLACK);

        pencil.moveToAbsolute(x, y);
        pencil.print('O');
        pencil.moveToAbsolute(x, y + 1);
        pencil.print('J');

        pencil.moveToAbsolute(x, y + 2);
        pencil.print('|');
    }

    private void drawCrawlingLeft(Pencil pencil) {
        int x = pencil.getTerminalSize().getColumns() / 2;
        int y = pencil.getTerminalSize().getRows() / 2;

        pencil.setForegroundColor(TextColor.ANSI.RED);
        pencil.moveToAbsolute(x, y + 1);
        pencil.print(Symbols.TRIANGLE_DOWN_POINTING_BLACK);

        pencil.setForegroundColor(TextColor.ANSI.BLACK);
        pencil.moveToAbsolute(x, y + 2);
        pencil.print("O" + Symbols.SINGLE_LINE_T_DOWN + Symbols.SINGLE_LINE_TOP_RIGHT_CORNER);
    }

    private void drawCrawlingRight(Pencil pencil) {
        int x = pencil.getTerminalSize().getColumns() / 2;
        int y = pencil.getTerminalSize().getRows() / 2;

        pencil.setForegroundColor(TextColor.ANSI.RED);
        pencil.moveToAbsolute(x + 2, y + 1);
        pencil.print(Symbols.TRIANGLE_DOWN_POINTING_BLACK);

        pencil.setForegroundColor(TextColor.ANSI.BLACK);
        pencil.moveToAbsolute(x, y + 2);
        pencil.print("" + Symbols.SINGLE_LINE_TOP_LEFT_CORNER + Symbols.SINGLE_LINE_T_DOWN + "O");
    }

    private void drawWalkingLeft(Pencil pencil) {
        int x = pencil.getTerminalSize().getColumns() / 2;
        int y = pencil.getTerminalSize().getRows() / 2;

        pencil.setForegroundColor(TextColor.ANSI.RED);

        pencil.moveToAbsolute(x + 1, y);
        pencil.print(Symbols.TRIANGLE_LEFT_POINTING_BLACK);

        pencil.setForegroundColor(TextColor.ANSI.BLACK);

        pencil.moveToAbsolute(x, y);
        pencil.print('O');
        pencil.moveToAbsolute(x, y + 1);
        pencil.print('J');

        pencil.moveToAbsolute(x, y + 2);
        pencil.print('A');
    }

    private void drawStandingRight(Pencil pencil) {
        int x = pencil.getTerminalSize().getColumns() / 2;
        int y = pencil.getTerminalSize().getRows() / 2;

        pencil.setForegroundColor(TextColor.ANSI.RED);

        pencil.moveToAbsolute(x - 1, y);
        pencil.print(Symbols.TRIANGLE_RIGHT_POINTING_BLACK);

        pencil.setForegroundColor(TextColor.ANSI.BLACK);

        pencil.moveToAbsolute(x, y);
        pencil.print('O');
        pencil.moveToAbsolute(x, y + 1);
        pencil.print('L');

        pencil.moveToAbsolute(x, y + 2);
        pencil.print('|');
    }

    private void drawWalkingRight(Pencil pencil) {
        int x = pencil.getTerminalSize().getColumns() / 2;
        int y = pencil.getTerminalSize().getRows() / 2;

        pencil.setForegroundColor(TextColor.ANSI.RED);

        pencil.moveToAbsolute(x - 1, y);
        pencil.print(Symbols.TRIANGLE_RIGHT_POINTING_BLACK);

        pencil.setForegroundColor(TextColor.ANSI.BLACK);

        pencil.moveToAbsolute(x, y);
        pencil.print('O');
        pencil.moveToAbsolute(x, y + 1);
        pencil.print('L');

        pencil.moveToAbsolute(x, y + 2);
        pencil.print('A');
    }

    public void crawl() {
        action = Action.CRAWLING;
    }

    public void moveLeft(Collidible nearestCollidibleLeft) {
        if (action != Action.MOVING) {
            direction = Direction.LEFT;
            action = Action.MOVING;

            int steps = 2;
            if (nearestCollidibleLeft != null) {
                int spaceToTheLeft = position.getX() - nearestCollidibleLeft.getLowerRight().getX() - 1;
                if (spaceToTheLeft < steps) {
                    steps = spaceToTheLeft;
                }
            }
            for (int i = 0; i < steps; i++) {
                position = position.left();
            }
        }
    }

    public void moveRight(Collidible nearestCollidibleRight) {
        if (action != Action.MOVING) {
            direction = Direction.RIGHT;
            action = Action.MOVING;

            int steps = 2;
            if (nearestCollidibleRight != null) {
                int spaceToTheRight = nearestCollidibleRight.getUpperLeft().getX() - position.getX() - 1;
                if (spaceToTheRight < steps) {
                    steps = spaceToTheRight;
                }
            }
            for (int i = 0; i < steps; i++) {
                position = position.right();
            }
        }
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public Point getUpperLeft() {
        if (action == Action.CRAWLING) {
            return getPosition().down().down();
        } else {
            return getPosition();
        }
    }

    @Override
    public Point getLowerRight() {
        return new Point(getPosition().getX(), getPosition().getY() + height - 1);
    }

    @Override
    public void fall() {
        if (globalFrame % 3 == 0) {
            position = position.down();
        }
    }

    public void jump(Collidible nearestCollidibleAbove) {
        action = Action.NONE;

        int jumpHeight = 10;
        if (nearestCollidibleAbove != null) {
            int headroom = getUpperLeft().getY() - nearestCollidibleAbove.getLowerRight().getY();
            if (headroom < jumpHeight) {
                jumpHeight = headroom;
            }
        }
        for (int i = 0; i < jumpHeight; i++) {
            position = position.up();
        }
    }

    public void shoot() {
        currentWeapon.shoot(getPosition().down(), direction).stream().forEach(projectile -> drawableRegister.add(projectile));
    }

    public int getHealth() {
        return health;
    }

    public void injure() {
        if (health >= 10) {
            health = health - 10;
        } else {
            health = 0;
        }
    }

    public void increaseHealth() {
        if (health <= 90) {
            health += 10;
        } else {

            health = 100;
        }
    }

    public void switchWeapon() {
        currentWeaponIndex = (currentWeaponIndex + 1) % weapons.size();
        currentWeapon = weapons.get(currentWeaponIndex);
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }

    public Weapon getCurrentWeapon() {
        return currentWeapon;
    }

    public void increaseAmmo() {
        currentWeapon.increaseAmmo();
    }

    @Override
    public void hitByProjectile(Projectile projectile) {
        injure();
        new Thread(() -> hurtSound.playOnce()).start();
    }
}
