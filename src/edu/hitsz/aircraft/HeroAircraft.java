package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.shoot.DirectShootStrategy;
import edu.hitsz.shoot.ShootStrategy;

/**
 * 英雄飞机，游戏玩家操控。
 * 同时作为策略模式中的一个具体 Context：
 * 通过 setShootStrategy 切换不同的弹道策略。
 */
public class HeroAircraft extends AbstractAircraft {

    private static volatile HeroAircraft instance;

    private int shootNum = 1;
    private int power = 30;
    private int direction = -1;

    private int fireBuffVersion = 0;

    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    /**
     * 单例模式获取英雄机实例，并在创建时绑定默认直射弹道策略。
     */
    public static HeroAircraft getInstance() {
        if (instance == null) {
            synchronized (HeroAircraft.class) {
                if (instance == null) {
                    instance = new HeroAircraft(
                            Main.WINDOW_WIDTH / 2,
                            Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight(),
                            0,
                            0,
                            100);
                    instance.setShootStrategy(new DirectShootStrategy());
                }
            }
        }
        return instance;
    }

    public void applyShootStrategyForDuration(ShootStrategy strategy, long millis) {
        setShootStrategy(strategy);
        fireBuffVersion++;
        int currentVersion = fireBuffVersion;
        Runnable task = () -> {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException ignored) {
            }
            if (fireBuffVersion == currentVersion) {
                setShootStrategy(new DirectShootStrategy());
            }
        };
        new Thread(task, "hero-fire-buff-" + currentVersion).start();
    }

    @Override
    public void forward() {
    }

}
