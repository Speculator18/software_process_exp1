package edu.hitsz.application;

public class MusicManager {

    private static MusicThread backgroundThread;
    private static MusicThread bossThread;

    public static void startBackgroundMusic() {
        if (backgroundThread != null && backgroundThread.isAlive()) {
            return;
        }
        backgroundThread = new MusicThread("src/videos/bgm.wav", true);
        backgroundThread.start();
    }

    public static void stopBackgroundMusic() {
        if (backgroundThread != null) {
            backgroundThread.stopMusic();
            backgroundThread = null;
        }
    }

    public static void startBossMusic() {
        stopBackgroundMusic();
        if (bossThread != null && bossThread.isAlive()) {
            return;
        }
        bossThread = new MusicThread("src/videos/bgm_boss.wav", true);
        bossThread.start();
    }

    public static void stopBossMusic() {
        if (bossThread != null) {
            bossThread.stopMusic();
            bossThread = null;
        }
    }

    public static void stopAll() {
        stopBackgroundMusic();
        stopBossMusic();
    }
}

