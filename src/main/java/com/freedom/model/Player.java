package com.freedom.model;

import com.freedom.display.Pencil;
import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TextColor;

public class Player extends Drawable implements Collidible, Graviteable {

    long globalFrame = 0;
    long localFrame = 0;
    Direction direction;
    Movement movement;
    final int height = 3;

    public Player(Point initialPosition) {
        super(Integer.MAX_VALUE);
        this.position = initialPosition;

        this.direction = Direction.RIGHT;
        this.movement = Movement.NONE;
    }

    @Override
    public void draw(Pencil pencil) {
        globalFrame++;

        if (movement == Movement.NONE) {
            if (direction == Direction.LEFT) {
                drawStandingLeft(pencil);
            }
            if (direction == Direction.RIGHT) {
                drawStandingRight(pencil);
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
                    movement = Movement.NONE;
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
                    movement = Movement.NONE;
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

    public void moveLeft() {
        if (movement == Movement.NONE) {
            direction = Direction.LEFT;
            movement = Movement.MOVING;
            position = position.left();
            position = position.left();
        }
    }

    public void moveRight() {
        if (movement == Movement.NONE) {
            direction = Direction.RIGHT;
            movement = Movement.MOVING;
            position = position.right();
            position = position.right();
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
    public void fall() {
        if (globalFrame % 3 == 0) {
            position = position.down();
        }
    }

    public void jump() {
        for (int i = 0; i < 10; i++) {
            position = position.up();
        }
    }
}
