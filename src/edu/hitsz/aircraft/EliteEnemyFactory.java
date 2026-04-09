package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

/**
 * 精英敌机工厂：负责创建 EliteEnemy，
 * 与客户端解耦具体构造细节。
 */
public class EliteEnemyFactory implements EnemyFactory {

    @Override
    public AbstractEnemy createEnemy() {
        int x = (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth()));
        int y = (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05);
        int speedX = 0;
        int speedY = 8;
        int hp = 60;
        EliteEnemy enemy = new EliteEnemy(x, y, speedX, speedY, hp);
        enemy.setShootStrategy(new edu.hitsz.shoot.DirectShootStrategy());
        return enemy;
    }
}
