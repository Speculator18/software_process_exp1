package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.shoot.RingShootStrategy;

/**
 * Boss 敌机工厂：
 * 负责在屏幕上方生成 Boss，并为其绑定环射弹道策略。
 */
public class BossEnemyFactory implements EnemyFactory {

    @Override
    public AbstractEnemy createEnemy() {
        int x = Main.WINDOW_WIDTH / 2;
        int y = ImageManager.BOSS_ENEMY_IMAGE.getHeight();
        int speedX = 3;
        int speedY = 0;
        int hp = 1200;
        BossEnemy boss = new BossEnemy(x, y, speedX, speedY, hp);
        boss.setShootStrategy(new RingShootStrategy());
        return boss;
    }
}
