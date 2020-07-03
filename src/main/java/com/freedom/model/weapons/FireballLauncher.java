package com.freedom.model.weapons;

import com.freedom.model.common.Direction;
import com.freedom.model.common.Point;

public class FireballLauncher extends Weapon {

    @Override
    protected Projectile createNewProjectile(Point initialPosition, Direction direction) {
        return new Fireball(initialPosition, direction);
    }
}
