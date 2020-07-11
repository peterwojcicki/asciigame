package com.freedom.levels;

import com.freedom.display.Sky;
import com.freedom.display.Style;
import com.freedom.model.Filler;
import com.freedom.model.Platform;
import com.freedom.model.backgrounditems.*;
import com.freedom.model.collectibles.Ammo;
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

        add(new Zombie(platformBeforeBridge, this, this));
        add(new Zombie(platformBeforeBridge, this, this));
        add(new Zombie(platformBeforeBridge, this, this));
        add(new Zombie(platformBeforeBridge, this, this));
        add(new Zombie(platformBeforeBridge, this, this));
        add(new Zombie(platformBeforeBridge, this, this));
        add(new Zombie(platformBeforeBridge, this, this));

        // the wall to jump over
        add(new Life(new Point(20, 1), player));
        add(new Platform(new Point(25, 1), 8, 2, BRICK));
        add(new Life(new Point(36, 1), player));

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
        Platform platformAfterBridge = new Platform(new Point(118, 3), 100, 1, PAVEMENT);
        add(platformAfterBridge);
        add(new Filler(new Point(118, 4), 100, 50, EARTH));
        add(new Zombie(platformAfterBridge, this, this));
        add(new Zombie(platformAfterBridge, this, this));
        add(new Zombie(platformAfterBridge, this, this));
        add(new Zombie(platformAfterBridge, this, this));
        add(new TreeWithoutLeaves(new Point(130, -9)));

        add(new Ammo(new Point(137, 1), player));

        // the wall to jump over
        add(new Platform(new Point(150, 1), 8, 2, BRICK));
        add(new Life(new Point(154, -1), player));

        add(new Flat1(new Point(160, -6)));
        add(new Flat2(new Point(180, -18)));
        add(new Flat2(new Point(200, -18)));

        // the 1st raised platform
        Platform firstRaisedPlatformAfterBridge = new Platform(new Point(platformAfterBridge.getPosition().getX() + platformAfterBridge.getWidth(), 1), 50, 1, PAVEMENT);
        add(firstRaisedPlatformAfterBridge);
        add(new Filler(new Point(firstRaisedPlatformAfterBridge.getPosition().getX(), 2), firstRaisedPlatformAfterBridge.getWidth(), 50, EARTH));
        add(new Zombie(firstRaisedPlatformAfterBridge, this, this));
        add(new Zombie(firstRaisedPlatformAfterBridge, this, this));
        add(new Zombie(firstRaisedPlatformAfterBridge, this, this));
        add(new Zombie(firstRaisedPlatformAfterBridge, this, this));
        add(new Zombie(firstRaisedPlatformAfterBridge, this, this));
        add(new Zombie(firstRaisedPlatformAfterBridge, this, this));
        add(new BlockOfFlats(new Point(firstRaisedPlatformAfterBridge.getUpperLeft().getX() + 5, firstRaisedPlatformAfterBridge.getUpperLeft().getY() - 13)));
        add(new Platform(new Point(firstRaisedPlatformAfterBridge.getLowerRight().getX() - 7, firstRaisedPlatformAfterBridge.getUpperLeft().getY() - 2), 4, 2, BRICK));
        add(new Life(new Point(firstRaisedPlatformAfterBridge.getLowerRight().getX() - 1, firstRaisedPlatformAfterBridge.getUpperLeft().getY() - 2), player));

        // the 2nd raised platform
        Platform secondRaisedPlatformAfterBridge = new Platform(new Point(firstRaisedPlatformAfterBridge.getPosition().getX() + firstRaisedPlatformAfterBridge.getWidth(), 0), 50, 1, PAVEMENT);
        add(secondRaisedPlatformAfterBridge);
        add(new Filler(new Point(secondRaisedPlatformAfterBridge.getPosition().getX(), 1), secondRaisedPlatformAfterBridge.getWidth(), 50, EARTH));
        add(new Zombie(secondRaisedPlatformAfterBridge, this, this));
        add(new Zombie(secondRaisedPlatformAfterBridge, this, this));
        add(new Zombie(secondRaisedPlatformAfterBridge, this, this));
        add(new Zombie(secondRaisedPlatformAfterBridge, this, this));
        add(new Zombie(secondRaisedPlatformAfterBridge, this, this));
        add(new Zombie(secondRaisedPlatformAfterBridge, this, this));
        add(new BlockOfFlats(new Point(secondRaisedPlatformAfterBridge.getUpperLeft().getX() + 4, secondRaisedPlatformAfterBridge.getUpperLeft().getY() - 13)));

        // the 3nd lowered platform
        Platform verticalPlatformBeforeLoweredPlatform = new Platform(new Point(secondRaisedPlatformAfterBridge.getLowerRight().getX(), secondRaisedPlatformAfterBridge.getLowerRight().getY()), 1, 6, PAVEMENT);
        add(verticalPlatformBeforeLoweredPlatform);
        Platform thirdLoweredPlatform = new Platform(new Point(secondRaisedPlatformAfterBridge.getLowerRight().getX() + 1, 5), 50, 1, PAVEMENT);
        add(thirdLoweredPlatform);
        add(new Filler(new Point(thirdLoweredPlatform.getPosition().getX(), thirdLoweredPlatform.getUpperLeft().getY() + 1), thirdLoweredPlatform.getWidth(), 50, EARTH));
        add(new Zombie(thirdLoweredPlatform, this, this));
        add(new Zombie(thirdLoweredPlatform, this, this));
        add(new Zombie(thirdLoweredPlatform, this, this));
        add(new Platform(new Point(thirdLoweredPlatform.getUpperLeft().getX() + 10, thirdLoweredPlatform.getUpperLeft().getY() - 2), 3, 2, BRICK));
        add(new Life(new Point(thirdLoweredPlatform.getUpperLeft().getX() + 18, thirdLoweredPlatform.getUpperLeft().getY() - 2), player));
    }

    @Override
    void tearDown() {
        backgroundSound.stop();
    }

}
