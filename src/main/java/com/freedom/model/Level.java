package com.freedom.model;

import com.freedom.display.Pencil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Level {

    private List<Drawable> drawables;
    private List<Collidible> collidibles;
    private List<Graviteable> graviteables;

    public Level(String name) {
        drawables = new ArrayList<>();
        collidibles = new ArrayList<>();
        graviteables = new ArrayList<>();
    }

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


    public void render(Pencil pencil) {

        for (Graviteable graviteable : graviteables) {
            Collidible nearestCollidible = getNearestCollidible(graviteable);
            if (nearestCollidible != null) {
                if (!nearestCollidible.isTouching(graviteable)
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

    private Collidible getNearestCollidible(Graviteable graviteable) {
        Collidible nearestCollidible = null;

        for (Collidible collidible : collidibles) {

            // ignore collidibles which are not underneath the falling item
            if (collidible.getUpperLeft().getX() > graviteable.getPosition().getX()
                    || collidible.getLowerRight().getX() < graviteable.getPosition().getX()) {
                continue;
            }

            if (collidible.isTouching(graviteable)) {
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
