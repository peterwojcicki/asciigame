package com.freedom.model.weapons;

import com.freedom.model.common.Direction;
import com.freedom.model.common.Point;

import java.util.Optional;

public abstract class Weapon {

    private int ammo = 10;

    public Optional<Projectile> shoot(Point initialPosition, Direction direction) {
        if (ammo > 0) {
            ammo--;

            return Optional.of(createNewProjectile(initialPosition, direction));
        }
        return Optional.empty();
    }

    protected abstract Projectile createNewProjectile(Point initialPosition, Direction direction);

    public int getAmmo() {
        return ammo;
    }

    public abstract String getSymbol();

    public void increaseAmmo() {
        ammo += 20;
    }
}
