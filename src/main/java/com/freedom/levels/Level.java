package com.freedom.levels;

import com.freedom.display.Pencil;
import com.freedom.model.*;
import com.freedom.sound.SoundManager;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Level implements DrawableRegister {

    private List<Drawable> drawables;
    private List<Collidible> collidibles;
    private List<Graviteable> graviteables;
    private List<Enemy> enemies;
    private List<Projectile> projectiles;
    private SoundManager soundManager;

    private Point finish;
    private final Player player;
    final Pencil pencil;

    public Level(String name, Point finish) {
        this.finish = finish;
        drawables = new ArrayList<>();
        collidibles = new ArrayList<>();
        graviteables = new ArrayList<>();
        enemies = new ArrayList<>();
        projectiles = new ArrayList<>();

        soundManager = new SoundManager();

        player = new Player(new Point(0, -4), this);
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
                if (keyStroke.getKeyType().equals(KeyType.Character)) {
                    if (keyStroke.getCharacter().equals(' ')) {
                        player.shoot();
                    }
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

    @Override
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

        if (drawable instanceof Projectile) {
            projectiles.add((Projectile) drawable);
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

        removeInactiveProjectiles();

        for (Enemy enemy : enemies) {
            enemy.move();
        }

        for (Projectile projectile : projectiles.stream().filter(p -> p.isActive()).collect(Collectors.toList())) {
            projectile.move(getNearestCollidibleLeft(projectile), getNearestCollidibleRight(projectile));
        }

        draw(pencil);
        pencil.flush();
    }

    private void removeInactiveProjectiles() {
        projectiles.removeIf(p -> !p.isActive());
        collidibles.removeIf(p -> p instanceof Projectile && !((Projectile) p).isActive());

        // we don't want to be blocked by dead enemies
        collidibles.removeIf(p -> p instanceof Enemy && ((Enemy) p).isDead());

        drawables.removeIf(p -> p instanceof Projectile && !((Projectile) p).isActive());
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

    private Collidible getNearestCollidibleAbove(Collidible movingObject) {
        Collidible nearestCollidible = null;

        for (Collidible collidible : collidibles) {

            if (collidible.getUpperLeft().getX() > movingObject.getPosition().getX()
                    || collidible.getLowerRight().getX() < movingObject.getPosition().getX()) {
                continue;
            }

            if (collidible.isTouchingVerticallyFromAbove(movingObject)) {
                return collidible;
            } else if (collidible.getLowerRight().isHigherThan(movingObject.getUpperLeft())) {
                if (nearestCollidible == null || nearestCollidible.getLowerRight().isHigherThan(collidible.getLowerRight())) {
                    nearestCollidible = collidible;
                }
            }
        }

        return nearestCollidible;
    }

    private Collidible getNearestCollidibleBelow(Collidible movingObject) {
        Collidible nearestCollidible = null;

        for (Collidible collidible : collidibles) {

            if (collidible.getUpperLeft().getX() > movingObject.getPosition().getX()
                    || collidible.getLowerRight().getX() < movingObject.getPosition().getX()) {
                continue;
            }

            if (collidible.isTouchingVerticallyFromBelow(movingObject)) {
                return collidible;
            } else if (collidible.getUpperLeft().isLowerThan(movingObject.getLowerRight())) {
                if (nearestCollidible == null || nearestCollidible.getUpperLeft().isLowerThan(collidible.getUpperLeft())) {
                    nearestCollidible = collidible;
                }
            }
        }

        return nearestCollidible;
    }

    private Collidible getNearestCollidibleLeft(Collidible movingObject) {
        Collidible nearestCollidible = null;

        for (Collidible collidible : collidibles) {

            if (collidible.getUpperLeft().getY() > movingObject.getPosition().getY()
                    || collidible.getLowerRight().getY() < movingObject.getPosition().getY()) {
                continue;
            }

            if (collidible.isTouchingHorizontallyFromLeft(movingObject)) {
                return collidible;
            } else if (collidible.getLowerRight().isLeftOf(movingObject.getUpperLeft())) {
                if (nearestCollidible == null || nearestCollidible.getLowerRight().isLeftOf(collidible.getLowerRight())) {
                    nearestCollidible = collidible;
                }
            }
        }

        return nearestCollidible;
    }

    private Collidible getNearestCollidibleRight(Collidible movingObject) {
        Collidible nearestCollidible = null;

        for (Collidible collidible : collidibles) {

            if (collidible.getUpperLeft().getY() > movingObject.getPosition().getY()
                    || collidible.getLowerRight().getY() < movingObject.getPosition().getY()) {
                continue;
            }

            if (collidible.isTouchingHorizontallyFromRight(movingObject)) {
                return collidible;
            } else if (collidible.getUpperLeft().isRightOf(movingObject.getLowerRight())) {
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
