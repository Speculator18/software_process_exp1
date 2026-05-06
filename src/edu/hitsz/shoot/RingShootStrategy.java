package edu.hitsz.shoot;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;
import java.util.LinkedList;
import java.util.List;

/**
 * 环射弹道策略：
 * 将多颗子弹均匀分布在 360 度上，形成环形攻击。
 * <br>
 * 为平衡弹幕密度：
 * Boss 敌机使用较少的子弹数量与较低的基础速度；
 * 吃到超级火力道具后的英雄机保留更强的环射表现。
 */
public class RingShootStrategy implements ShootStrategy {

    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft) {
        List<BaseBullet> res = new LinkedList<>();
        int bulletCount = aircraft instanceof BossEnemy ? 16 : 12;
        double angleStep = 2 * Math.PI / bulletCount;
        int x = aircraft.getLocationX();
        int y = aircraft.getLocationY();
        int baseSpeed = aircraft instanceof BossEnemy ? 4 : 4;
        for (int i = 0; i < bulletCount; i++) {
            double angle = i * angleStep;
            int speedX;
            int speedY;
            if (aircraft instanceof BossEnemy) {
                // Boss 椭圆环射：在 x/y 方向采用不同的速度幅值，形成椭圆形弹幕
                int speedA = 6;
                int speedB = 4;
                speedX = (int) Math.round(speedA * Math.cos(angle));
                speedY = (int) Math.round(speedB * Math.sin(angle));
            } else {
                speedX = (int) Math.round(baseSpeed * Math.cos(angle));
                speedY = (int) Math.round(baseSpeed * Math.sin(angle));
            }
            if (speedX == 0 && speedY == 0) {
                speedY = 1;
            }
            int power;
            if (aircraft instanceof HeroAircraft) {
                power = 30;
                res.add(new HeroBullet(x, y, speedX, speedY, power));
            } else if (aircraft instanceof BossEnemy) {
                power = 20;
                res.add(new EnemyBullet(x, y, speedX, speedY, power));
            }
        }
        return res;
    }
}
