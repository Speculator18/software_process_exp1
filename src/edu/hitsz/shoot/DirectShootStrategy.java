package edu.hitsz.shoot;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;
import java.util.LinkedList;
import java.util.List;

/**
 * 单排直射弹道策略：
 * 英雄机默认使用的直射弹道，同时用于精英敌机的单发直射攻击。
 */
public class DirectShootStrategy implements ShootStrategy {

    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft) {
        if (aircraft instanceof HeroAircraft) {
            HeroAircraft hero = (HeroAircraft) aircraft;
            List<BaseBullet> res = new LinkedList<>();
            int x = hero.getLocationX();
            int y = hero.getLocationY() - 2;
            int speedX = 0;
            int speedY = hero.getSpeedY() - 5;
            int shootNum = 1;
            int power = 30;
            for (int i = 0; i < shootNum; i++) {
                BaseBullet bullet = new HeroBullet(x + (i * 2 - shootNum + 1) * 10, y, speedX, speedY, power);
                res.add(bullet);
            }
            return res;
        }
        if (aircraft instanceof EliteEnemy) {
            EliteEnemy enemy = (EliteEnemy) aircraft;
            List<BaseBullet> res = new LinkedList<>();
            int x = enemy.getLocationX();
            int y = enemy.getLocationY() + enemy.getHeight() / 2;
            int speedX = 0;
            int speedY = enemy.getSpeedY() + 5;
            int power = 20;
 
        }
        return new LinkedList<>();
    }
}
