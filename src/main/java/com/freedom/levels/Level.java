package com.freedom.levels;

import com.freedom.display.Pencil;
import com.freedom.model.*;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Level {

    private List<Drawable> drawables;
    private List<Collidible> collidibles;
    private List<Graviteable> graviteables;
    private List<Enemy> enemies;

    private Point finish;
    private final Player player;
    final Pencil pencil;

    public Level(String name, Point finish) {
        this.finish = finish;
        drawables = new ArrayList<>();
        collidibles = new ArrayList<>();
        graviteables = new ArrayList<>();
        enemies = new ArrayList<>();

        player = new Player(new Point(0, -4));
        pencil = new Pencil(player);

        add(player);
    }

    public void play() throws Exception {
        init();

        while (!isLevelCompleted()) {
            render();
            Thread.sleep(10);

            KeyStroke keyStroke = pencil.pollInput();
            if (keyStroke != null) {
                if (keyStroke.getKeyType().equals(KeyType.ArrowLeft)) {
                    player.moveLeft(getNearestCollidibleLeft(player));
                }
                if (keyStroke.getKeyType().equals(KeyType.ArrowRight)) {
                    player.moveRight(getNearestCollidibleRight(player));
                }
                if (keyStroke.getKeyType().equals(KeyType.ArrowUp)) {
                    player.jump(getNearestCollidibleAbove(player));
                }
            }
        }

        tearDown();

        pencil.exitPrivateMode();
    }

    boolean isLevelCompleted() {
        return hasPlayerReachedFinish(player);
    }

    private boolean hasPlayerReachedFinish(Player player) {
        return player.getPosition().getX() >= finish.getX();
    }

    abstract void init();

    public void add(Drawable drawable) {
        drawables.add(drawable);

        Collections.sort(drawables);

        if (drawable instanceof Collidible) {
            collidibles.add((Collidible) drawable);
        }

        if (drawable instanceof Graviteable) {
            graviteables.add((Graviteable) drawable);
        }

        if (drawable instanceof Enemy) {
            enemies.add((Enemy) drawable);
        }
    }

    public void render() {
        for (Graviteable graviteable : graviteables) {
            Collidible nearestCollidible = getNearestCollidibleBelow(graviteable);
            if (nearestCollidible != null) {
                // bear in mind it's the graviteable that is falling - so the nearest collidible will be touching from below
                if (!nearestCollidible.isTouchingVerticallyFromBelow(graviteable)
                        && nearestCollidible.getUpperLeft().isLowerThan(graviteable.getLowerRight())) {
                    graviteable.fall();
                }
            } else {
                graviteable.fall();
            }
        }

        for (Enemy enemy : enemies) {
            enemy.move();
        }

        draw(pencil);
        pencil.flush();
    }

    private void draw(Pencil pencil) {
        drawables.forEach(drawable -> {
            try {
                drawable.draw(pencil);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private Collidible getNearestCollidibleAbove(Graviteable graviteable) {
        Collidible nearestCollidible = null;

        for (Collidible collidible : collidibles) {

            if (collidible.getUpperLeft().getX() > graviteable.getPosition().getX()
                    || collidible.getLowerRight().getX() < graviteable.getPosition().getX()) {
                continue;
            }

            if (collidible.isTouchingVerticallyFromAbove(graviteable)) {
                return collidible;
            } else if (collidible.getLowerRight().isHigherThan(graviteable.getUpperLeft())) {
                if (nearestCollidible == null || nearestCollidible.getLowerRight().isHigherThan(collidible.getLowerRight())) {
                    nearestCollidible = collidible;
                }
            }
        }

        return nearestCollidible;
    }

    private Collidible getNearestCollidibleBelow(Graviteable graviteable) {
        Collidible nearestCollidible = null;

        for (Collidible collidible : collidibles) {

            if (collidible.getUpperLeft().getX() > graviteable.getPosition().getX()
                    || collidible.getLowerRight().getX() < graviteable.getPosition().getX()) {
                continue;
            }

            if (collidible.isTouchingVerticallyFromBelow(graviteable)) {
                return collidible;
            } else if (collidible.getUpperLeft().isLowerThan(graviteable.getLowerRight())) {
                if (nearestCollidible == null || nearestCollidible.getUpperLeft().isLowerThan(collidible.getUpperLeft())) {
                    nearestCollidible = collidible;
                }
            }
        }

        return nearestCollidible;
    }

    private Collidible getNearestCollidibleLeft(Graviteable graviteable) {
        Collidible nearestCollidible = null;

        for (Collidible collidible : collidibles) {

            if (collidible.getUpperLeft().getY() > graviteable.getPosition().getY()
                    || collidible.getLowerRight().getY() < graviteable.getPosition().getY()) {
                continue;
            }

            if (collidible.isTouchingHorizontallyFromLeft(graviteable)) {
                return collidible;
            } else if (collidible.getLowerRight().isLeftOf(graviteable.getUpperLeft())) {
                if (nearestCollidible == null || nearestCollidible.getLowerRight().isLeftOf(collidible.getLowerRight())) {
                    nearestCollidible = collidible;
                }
            }
        }

        return nearestCollidible;
    }

    private Collidible getNearestCollidibleRight(Graviteable graviteable) {
        Collidible nearestCollidible = null;

        for (Collidible collidible : collidibles) {

            if (collidible.getUpperLeft().getY() > graviteable.getPosition().getY()
                    || collidible.getLowerRight().getY() < graviteable.getPosition().getY()) {
                continue;
            }

            if (collidible.isTouchingHorizontallyFromRight(graviteable)) {
                return collidible;
            } else if (collidible.getUpperLeft().isRightOf(graviteable.getLowerRight())) {
                if (nearestCollidible == null || nearestCollidible.getUpperLeft().isRightOf(collidible.getUpperLeft())) {
                    nearestCollidible = collidible;
                }
            }
        }

        return nearestCollidible;
    }

    void tearDown() {
    }

}
