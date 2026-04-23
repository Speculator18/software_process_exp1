package edu.hitsz.application;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import java.io.File;

public class SoundManager {

    private static boolean enabled = true;

    public static boolean isEnabled() {
        return enabled;
    }

    public static void setEnabled(boolean value) {
        enabled = value;
    }

    private static void playInternal(String filename) {
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(new File(filename));
            AudioFormat format = stream.getFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = stream.read(buffer, 0, buffer.length)) != -1) {
                line.write(buffer, 0, bytesRead);
            }
            line.drain();
            line.close();
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void playEffect(String filename) {
        if (!enabled) {
            return;
        }
        new Thread(() -> playInternal(filename)).start();
    }

    public static void playBulletHit() {
        playEffect("src/videos/bullet_hit.wav");
    }

    public static void playBombExplosion() {
        playEffect("src/videos/bomb_explosion.wav");
    }

    public static void playGetSupply() {
        playEffect("src/videos/get_supply.wav");
    }

    public static void playGameOver() {
        playEffect("src/videos/game_over.wav");
    }
}
