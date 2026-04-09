package edu.hitsz.shoot;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.ElitePlusEnemy;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import java.util.LinkedList;
import java.util.List;

public class DoubleDirectShootStrategy implements ShootStrategy {
    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft) {
        if (aircraft instanceof ElitePlusEnemy) {
            ElitePlusEnemy enemy = (ElitePlusEnemy) aircraft;
            List<BaseBullet> res = new LinkedList<>();
            int baseX = enemy.getLocationX();
            int y = enemy.getLocationY() + enemy.getHeight() / 2;
            int offsetX = 15;
            int speedY = enemy.getSpeedY() + 5;
            int power = 20;
            res.add(new EnemyBullet(baseX - offsetX, y, 0, speedY, power));
            res.add(new EnemyBullet(baseX + offsetX, y, 0, speedY, power));
            return res;
        }
        return new LinkedList<>();
    }
}

