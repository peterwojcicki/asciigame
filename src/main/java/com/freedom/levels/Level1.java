package com.freedom.levels;

import com.freedom.display.Sky;
import com.freedom.display.Style;
import com.freedom.model.*;
import com.freedom.sound.Audio;
import com.googlecode.lanterna.Symbols;

public class Level1 extends Level {

    private final static Style BRICK = new Style(Symbols.BLOCK_MIDDLE, "#E62600");
    private final static Style GRASS = new Style(Symbols.BLOCK_DENSE, "#00CC00");

    private Audio backgroundSound;

    public Level1() {
        super("Undead City", new Point(200, 5));
    }

    @Override
    void init() {
        backgroundSound = new Audio("sounds/forest.wav");
        new Thread(() -> backgroundSound.playIndefinitely(() -> this.isLevelCompleted())).start();

        add(new Flat2(new Point(29, -18)));
        add(new Tree1(new Point(8, -13)));
        add(new Sky());
//        add(new Grass());
        add(new Platform(new Point(-2, -6), 10, 1, GRASS));
        add(new Platform(new Point(-2, 1), 20, 1, BRICK));
        add(new Platform(new Point(15, 5), 40, 1, BRICK));

        Platform platformWithEnemies1 = new Platform(new Point(25, 15), 50, 1, BRICK);
        add(platformWithEnemies1);

        add(new Enemy(platformWithEnemies1));
        add(new Enemy(platformWithEnemies1));
        add(new Enemy(platformWithEnemies1));
        add(new Enemy(platformWithEnemies1));

        add(new Platform(new Point(25, 0), 1, 10, BRICK));


        add(new Collectible(new Point(50, 13), (Void) -> player.increaseHealth()));


        for (int i = 0; i < 10; i++) {
            add(new Clouds(new Point(-100 + 80 * i, -20 + 2 * (i % 2))));
        }
    }

    @Override
    void tearDown() {
        backgroundSound.stop();
    }

}
