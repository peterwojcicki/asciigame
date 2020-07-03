package com.freedom.levels;

import com.freedom.display.Sky;
import com.freedom.display.Style;
import com.freedom.model.*;
import com.freedom.model.backgrounditems.*;
import com.freedom.model.collectibles.Ammo;
import com.freedom.model.collectibles.Collectible;
import com.freedom.model.collectibles.Life;
import com.freedom.model.common.Point;
import com.freedom.model.enemies.Zombie;
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
        // TODO move the finish point
        super("Undead City", new Point(1000, 5));
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
        Platform platformBeforeBridge = new Platform(new Point(-200, 3), 250, 1, GRASS);
        add(platformBeforeBridge);

        add(new Zombie(platformBeforeBridge));
        add(new Zombie(platformBeforeBridge));
        add(new Zombie(platformBeforeBridge));
        add(new Zombie(platformBeforeBridge));
        add(new Zombie(platformBeforeBridge));
        add(new Zombie(platformBeforeBridge));
        add(new Zombie(platformBeforeBridge));
        add(new Zombie(platformBeforeBridge));
        add(new Zombie(platformBeforeBridge));
        add(new Zombie(platformBeforeBridge));
        add(new Zombie(platformBeforeBridge));
        add(new Zombie(platformBeforeBridge));
        add(new Zombie(platformBeforeBridge));
        add(new Zombie(platformBeforeBridge));
        add(new Zombie(platformBeforeBridge));
        add(new Zombie(platformBeforeBridge));
        add(new Zombie(platformBeforeBridge));
        add(new Zombie(platformBeforeBridge));
        add(new Zombie(platformBeforeBridge));
        add(new Zombie(platformBeforeBridge));
        add(new Zombie(platformBeforeBridge));
//        add(new Spider(platformBeforeBridge));
//        add(new Spider(platformBeforeBridge));
//        add(new Spider(platformBeforeBridge));
//        add(new Spider(platformBeforeBridge));
//        add(new Spider(platformBeforeBridge));
//        add(new Spider(platformBeforeBridge));
//        add(new Spider(platformBeforeBridge));
//        add(new Spider(platformBeforeBridge));
//        add(new Soldier(platformBeforeBridge));

        // the wall to jump over
        add(new Platform(new Point(25, 1), 8, 2, BRICK));

        add(new Filler(new Point(-200, 4), 250, 50, EARTH));
        add(new TreeWithoutLeaves(new Point(-30, -9)));
        add(new TreeWithoutLeaves(new Point(0, -9)));

        add(new Ammo(new Point(45, 1), player));

        // bridge
        add(new Bridge(new Point(50, -6)));
        add(new Platform(new Point(50, 3), 68, 1, PAVEMENT));

        // water under the bridge
        add(new Filler(new Point(50, 8), 68, 100, WATER));

        // after the bridge
        Platform platformAfterBridge = new Platform(new Point(118, 3), 100, 1, GRASS);
        add(platformAfterBridge);
        add(new Filler(new Point(118, 4), 100, 50, EARTH));
        add(new Zombie(platformAfterBridge));
        add(new Zombie(platformAfterBridge));
        add(new Zombie(platformAfterBridge));
        add(new Zombie(platformAfterBridge));
        add(new TreeWithoutLeaves(new Point(130, -9)));

        add(new Ammo(new Point(137, 1), player));

        // the wall to jump over
        add(new Platform(new Point(150, 1), 8, 2, BRICK));
        add(new Life(new Point(154, 0), player));

        add(new Flat1(new Point(160, -6)));
        add(new Flat2(new Point(185, -18)));
        add(new Platform(new Point(181, 1), 4, 2, BRICK ));
        add(new Platform(new Point(175, -3), 4, 1, BRICK ));
        add(new Platform(new Point(181, -8), 4, 1, BRICK ));
        add(new Life(new Point(183, -10),  player));

    }

    @Override
    void tearDown() {
        backgroundSound.stop();
    }

}
