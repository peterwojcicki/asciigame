package com.freedom.sound;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import java.io.File;
import java.io.IOException;

public class Audio {
    public void playIndefinitely(String filename) {
        while (true) {
            playOnce(filename);
        }
    }

    private void playOnce(String filename) {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource(filename).getFile());

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);

            int BUFFER_SIZE = 128000;
            AudioFormat audioFormat = null;
            SourceDataLine sourceLine = null;

            audioFormat = audioStream.getFormat();

            sourceLine = AudioSystem.getSourceDataLine(audioFormat);
            sourceLine.open(audioFormat);
            sourceLine.start();

            int nBytesRead = 0;
            byte[] abData = new byte[BUFFER_SIZE];
            while (nBytesRead != -1) {
                try {
                    nBytesRead =
                            audioStream.read(abData, 0, abData.length);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (nBytesRead >= 0) {
                    int nBytesWritten = sourceLine.write(abData, 0, nBytesRead);
                }
            }

            sourceLine.drain();
            sourceLine.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
