package com.freedom.model.collectibles;

import com.freedom.model.Player;
import com.freedom.model.common.Point;
import com.googlecode.lanterna.Symbols;

public class Ammo extends Collectible {

    public Ammo(Point initialPosition, Player player) {
        super(initialPosition, (Void) -> player.increaseAmmo());
    }

    @Override
    protected String getSymbol() {
        return "" + Symbols.ARROW_UP + Symbols.ARROW_UP + Symbols.ARROW_UP;
    }
}
