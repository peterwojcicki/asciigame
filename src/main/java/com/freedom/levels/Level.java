package com.freedom.levels;

import com.freedom.display.Pencil;
import com.freedom.model.*;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Level {

    private boolean isFinished = false;

    private List<Drawable> drawables;
    private List<Collidible> collidibles;
    private List<Graviteable> graviteables;
    private Point finish;
    private final Player player;
    final Pencil pencil;

    public Level(String name, Point finish) {
        this.finish = finish;
        drawables = new ArrayList<>();
        collidibles = new ArrayList<>();
        graviteables = new ArrayList<>();

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
                    player.moveLeft();
                }
                if (keyStroke.getKeyType().equals(KeyType.ArrowRight)) {
                    player.moveRight();
                }
                if (keyStroke.getKeyType().equals(KeyType.ArrowUp)) {
                    player.jump();
                }
            }
        }

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
    }


    public void render() {
        for (Graviteable graviteable : graviteables) {
            Collidible nearestCollidible = getNearestCollidibleBelow(graviteable);
            if (nearestCollidible != null) {
                if (!nearestCollidible.isTouchingVertically(graviteable)
                        && nearestCollidible.getUpperLeft().isLowerThan(graviteable.getLowerRight())) {
                    graviteable.fall();
                }
            } else {
                graviteable.fall();
            }
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

    private Collidible getNearestCollidibleBelow(Graviteable graviteable) {
        Collidible nearestCollidible = null;

        for (Collidible collidible : collidibles) {

            // ignore collidibles which are not underneath the falling item
            if (collidible.getUpperLeft().getX() > graviteable.getPosition().getX()
                    || collidible.getLowerRight().getX() < graviteable.getPosition().getX()) {
                continue;
            }

            if (collidible.isTouchingVertically(graviteable)) {
                return collidible;
            } else if (collidible.getUpperLeft().isLowerThan(graviteable.getLowerRight())) {
                if (nearestCollidible == null || nearestCollidible.getUpperLeft().isLowerThan(collidible.getUpperLeft())) {
                    nearestCollidible = collidible;
                }
            }
        }

        return nearestCollidible;
    }

}
