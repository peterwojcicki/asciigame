package com.freedom.levels;

import com.freedom.display.Sky;
import com.freedom.display.Style;
import com.freedom.model.*;
import com.freedom.sound.Audio;
import com.googlecode.lanterna.Symbols;

public class Level1 extends Level {

    private final static Style BRICK = new Style(Symbols.BLOCK_MIDDLE, "#E62600");
    private final static Style GRASS = new Style(Symbols.BLOCK_MIDDLE, "#338d30");
    private final static Style PAVEMENT = new Style(Symbols.BLOCK_DENSE, "#cccccc");
    private final static Style EARTH = new Style(Symbols.BLOCK_DENSE, "#d2c37b");
    private final static Style WATER = new Style(Symbols.BLOCK_DENSE, "#5675dc");

    private Audio backgroundSound;

    public Level1() {
        super("Undead City", new Point(200, 5));
    }

    @Override
    void init() {
        backgroundSound = new Audio("sounds/forest.wav");
        new Thread(() -> backgroundSound.playIndefinitely(() -> this.isLevelCompleted())).start();

        for (int i = 0; i < 10; i++) {
            add(new Clouds(new Point(-100 + 80 * i, -20 + 2 * (i % 2))));
        }
        add(new Sky());

        // before the bridge
        add(new Platform(new Point(-50, 3), 100, 1, GRASS));
        add(new Filler(new Point(-50, 4), 100, 50, EARTH));
        add(new TreeWithoutLeaves(new Point(-30, -9)));
        add(new TreeWithoutLeaves(new Point(0, -9)));
        add(new Collectible(new Point(15, 1), (Void) -> player.increaseHealth()));

        // bridge
        add(new Bridge(new Point(50, -6)));
        add(new Platform(new Point(50, 3), 68, 1, PAVEMENT));


        // water under the bridge
        add(new Filler(new Point(50, 8), 68, 100, WATER));

        // after the bridge
        Platform platformAfterBridge = new Platform(new Point(118, 3), 100, 1, GRASS);
        add(platformAfterBridge);
        add(new Filler(new Point(118, 4), 100, 50, EARTH));
        add(new Enemy(platformAfterBridge));
        add(new Enemy(platformAfterBridge));
        add(new TreeWithoutLeaves(new Point(130, -9)));

    }

    @Override
    void tearDown() {
        backgroundSound.stop();
    }

}
