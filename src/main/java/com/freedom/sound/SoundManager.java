package com.freedom.sound;

import java.util.ArrayList;
import java.util.List;

public class SoundManager {
    private List<Audio> sounds;

    public SoundManager() {
        sounds = new ArrayList<>();
    }

    public void addBackgroundSound(String file) {

    }

    public void stopAll() {
        sounds.forEach(s -> s.stop());
    }
}
