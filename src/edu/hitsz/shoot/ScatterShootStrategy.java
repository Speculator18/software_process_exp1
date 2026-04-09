package edu.hitsz.shoot;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.EliteProEnemy;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;
import java.util.LinkedList;
import java.util.List;

/**
 * 散射弹道策略：
 * 英雄机在获得火力道具后使用的扇形散射弹道，
 * 同时用于王牌敌机的三向散射攻击。
 */
public class ScatterShootStrategy implements ShootStrategy {

    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft) {
        if (aircraft instanceof HeroAircraft) {
            HeroAircraft hero = (HeroAircraft) aircraft;
            List<BaseBullet> res = new LinkedList<>();
            int x = hero.getLocationX();
            int y = hero.getLocationY() - 2;
            int speedY = hero.getSpeedY() - 5;
            int power = 30;
            int shootNum = 3;
            for (int i = 0; i < shootNum; i++) {
                int offsetX = (i * 2 - shootNum + 1) * 10;
                int speedX = i - 1;
                BaseBullet bullet = new HeroBullet(x + offsetX, y, speedX, speedY, power);
                res.add(bullet);
            }
            return res;
        }
        if (aircraft instanceof EliteProEnemy) {
            EliteProEnemy enemy = (EliteProEnemy) aircraft;
            List<BaseBullet> res = new LinkedList<>();
            int x = enemy.getLocationX();
            int y = enemy.getLocationY() + enemy.getHeight() / 2;
            int speedY = enemy.getSpeedY() + 5;
            int power = 20;
            res.add(new EnemyBullet(x - 15, y, -2, speedY, power));
            res.add(new EnemyBullet(x, y, 0, speedY, power));
            res.add(new EnemyBullet(x + 15, y, 2, speedY, power));
            return res;
        }
        return new LinkedList<>();
    }
}
