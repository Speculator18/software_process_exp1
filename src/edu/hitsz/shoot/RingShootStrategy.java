package edu.hitsz.shoot;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;
import java.util.LinkedList;
import java.util.List;

public class RingShootStrategy implements ShootStrategy {
    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft) {
        List<BaseBullet> res = new LinkedList<>();
        int bulletCount = 20;
        double angleStep = 2 * Math.PI / bulletCount;
        int x = aircraft.getLocationX();
        int y = aircraft.getLocationY();
        int baseSpeed = 4;
        for (int i = 0; i < bulletCount; i++) {
            double angle = i * angleStep;
            int speedX = (int) Math.round(baseSpeed * Math.cos(angle));
            int speedY = (int) Math.round(baseSpeed * Math.sin(angle));
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

