package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.shoot.RingShootStrategy;

public class BossEnemyFactory implements EnemyFactory {

    @Override
    public AbstractEnemy createEnemy() {
        int x = Main.WINDOW_WIDTH / 2;
        int y = ImageManager.BOSS_ENEMY_IMAGE.getHeight();
        int speedX = 3;
        int speedY = 0;
        int hp = 300;
        BossEnemy boss = new BossEnemy(x, y, speedX, speedY, hp);
        boss.setShootStrategy(new RingShootStrategy());
        return boss;
    }
}

