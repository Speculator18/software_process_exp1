package edu.hitsz.application;

// E5 实验五：集中管理普通背景音乐和 Boss 背景音乐的启动与切换
public class MusicManager {

    private static MusicThread backgroundThread;
    private static MusicThread bossThread;

    // E5 启动普通背景音乐线程，若已在播放则直接返回
    public static void startBackgroundMusic() {
        if (backgroundThread != null && backgroundThread.isAlive()) {
            return;
        }
        backgroundThread = new MusicThread("src/videos/bgm.wav", true);
        backgroundThread.start();
    }

    // E5 停止普通背景音乐播放
    public static void stopBackgroundMusic() {
        if (backgroundThread != null) {
            backgroundThread.stopMusic();
            backgroundThread = null;
        }
    }

    // E5 启动 Boss 背景音乐前先关闭普通 BGM，实现无重叠切换
    public static void startBossMusic() {
        stopBackgroundMusic();
        if (bossThread != null && bossThread.isAlive()) {
            return;
        }
        bossThread = new MusicThread("src/videos/bgm_boss.wav", true);
        bossThread.start();
    }

    // E5 停止 Boss 背景音乐播放
    public static void stopBossMusic() {
        if (bossThread != null) {
            bossThread.stopMusic();
            bossThread = null;
        }
    }

    // E5 游戏结束时统一关闭所有背景音乐线程
    public static void stopAll() {
        stopBackgroundMusic();
        stopBossMusic();
    }
}
