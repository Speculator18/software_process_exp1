package edu.hitsz.observer;

import edu.hitsz.bullet.BaseBullet;

import java.util.List;

public class EnemyBulletObserver implements SupplyObserver {

    private final List<BaseBullet> enemyBullets;

    public EnemyBulletObserver(List<BaseBullet> enemyBullets) {
        this.enemyBullets = enemyBullets;
    }

    @Override
    public void update(SupplyEvent event) {
        if (event != SupplyEvent.BOMB) {
            return;
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.vanish();
        }
    }
}

