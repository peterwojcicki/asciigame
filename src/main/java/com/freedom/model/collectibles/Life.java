package com.freedom.model.collectibles;

import com.freedom.model.Player;
import com.freedom.model.common.Point;
import com.googlecode.lanterna.Symbols;

import java.util.function.Consumer;

public class Life extends Collectible {

    public Life(Point initialPosition, Player player) {
        super(initialPosition, (Void) -> player.increaseHealth());
    }

    @Override
    protected String getSymbol() {
        return "" + Symbols.HEART;
    }
}
