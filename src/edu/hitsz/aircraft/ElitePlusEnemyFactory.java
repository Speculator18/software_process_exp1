package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class ElitePlusEnemyFactory implements EnemyFactory {

    @Override
    public AbstractEnemy createEnemy() {
        int x = (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth()));
        int y = (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05);
        int speedX = 3;
        int speedY = 7;
        int hp = 80;
        return new ElitePlusEnemy(x, y, speedX, speedY, hp);
    }
}

