package com.freedom.model.weapons;

import com.freedom.model.common.Direction;
import com.freedom.model.common.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class Weapon {

    private int ammo = 100;

    public List<Projectile> shoot(Point initialPosition, Direction direction) {
        if (ammo > 0) {
            ammo--;
            return createNewProjectiles(initialPosition, direction);
        }
        return new ArrayList<>();
    }

    protected abstract List<Projectile> createNewProjectiles(Point initialPosition, Direction direction);

    public int getAmmo() {
        return ammo;
    }

    public abstract String getSymbol();

    public void increaseAmmo() {
        ammo += 100;
    }
}
