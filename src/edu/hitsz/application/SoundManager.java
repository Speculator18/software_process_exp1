package edu.hitsz.application;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import java.io.File;

// E5 实验五：统一管理各种一次性音效的播放与开关控制
public class SoundManager {

    private static boolean enabled = true;

    // E5 供界面读取当前音效开关状态
    public static boolean isEnabled() {
        return enabled;
    }

    // E5 由开始菜单切换，控制是否播放任何音效
    public static void setEnabled(boolean value) {
        enabled = value;
    }

    // E5 具体的音频播放逻辑：同步读取 wav 并写入音频输出设备
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

    // E5 每次播放音效时启动一个短线程，以免阻塞游戏主循环
    private static void playEffect(String filename) {
        if (!enabled) {
            return;
        }
        new Thread(() -> playInternal(filename)).start();
    }

    // E5 敌机被子弹击毁时的命中音效
    public static void playBulletHit() {
        playEffect("src/videos/bullet_hit.wav");
    }

    // E5 炸弹道具触发时的全屏爆炸音效
    public static void playBombExplosion() {
        playEffect("src/videos/bomb_explosion.wav");
    }

    // E5 英雄机获得任意补给时的提示音效
    public static void playGetSupply() {
        playEffect("src/videos/get_supply.wav");
    }

    // E5 游戏结束时播放的结算音效
    public static void playGameOver() {
        playEffect("src/videos/game_over.wav");
    }
}
