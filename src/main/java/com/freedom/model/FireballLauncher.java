package com.freedom.model;

public class FireballLauncher extends Weapon {

    @Override
    protected Projectile createNewProjectile(Point initialPosition, Direction direction) {
        return new Fireball(initialPosition, direction);
    }
}
