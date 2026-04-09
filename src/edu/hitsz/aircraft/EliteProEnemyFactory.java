package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

/**
 * 王牌敌机工厂：负责创建 EliteProEnemy，
 * 提供高速度、高血量的高难度敌机。
 */
public class EliteProEnemyFactory implements EnemyFactory {

    @Override
    public AbstractEnemy createEnemy() {
        int x = (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth()));
        int y = (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05);
        int speedX = 4;
        int speedY = 6;
        int hp = 100;
        EliteProEnemy enemy = new EliteProEnemy(x, y, speedX, speedY, hp);
        enemy.setShootStrategy(new edu.hitsz.shoot.ScatterShootStrategy());
        return enemy;
    }
}
