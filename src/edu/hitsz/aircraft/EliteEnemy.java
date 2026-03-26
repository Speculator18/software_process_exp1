package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;

import java.util.LinkedList;
import java.util.List;

public class EliteEnemy extends AbstractEnemy {

    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public void forward() {
        super.forward();
    }

    @Override
    public List<BaseBullet> shoot() {
        List<BaseBullet> res = new LinkedList<>();
        int x = getLocationX();
        int y = getLocationY() + getHeight() / 2;
        int speedX = 0;
        int speedY = getSpeedY() + 5;
        int power = 20;
        res.add(new EnemyBullet(x, y, speedX, speedY, power));
        return res;
    }
}
