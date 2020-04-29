package com.freedom.levels;

import com.freedom.display.Sky;
import com.freedom.model.*;
import com.freedom.sound.Audio;

public class Level1 extends Level {

    private Thread backgroundSound;
    private Audio audio;

    public Level1() {
        super("Undead City", new Point(40, 5));
    }

    @Override
    void init() {
        audio = new Audio();
        backgroundSound = new Thread(() -> audio.playIndefinitely("sounds/forest.wav", () -> this.isLevelCompleted()));
        backgroundSound.start();

        add(new Flat2(new Point(29, -18)));
        add(new Tree1(new Point(8, -13)));
        add(new Sky());
//        add(new Grass());
        add(new Ground(new Point(-2, -6), 10, 1));
        add(new Ground(new Point(-2, 1), 20, 1));
        add(new Ground(new Point(15, 5), 40, 1));
        add(new Ground(new Point(25, 0), 1, 10));
        for (int i = 0; i < 10; i++) {
            add(new Clouds(new Point(-100 + 80 * i, -20 + 2 * (i % 2))));
        }
    }

    @Override
    void tearDown() {
        audio.stop();
    }

}
