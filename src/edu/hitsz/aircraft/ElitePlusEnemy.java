package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;

import java.util.LinkedList;
import java.util.List;

public class ElitePlusEnemy extends AbstractEnemy {

    public ElitePlusEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public void forward() {
        super.forward();
        // 到达左右边界后反向，形成左右摆动的移动轨迹
        if (locationX <= getWidth() / 2 || locationX >= Main.WINDOW_WIDTH - getWidth() / 2) {
            speedX = -speedX;
        }
    }

    @Override
    public List<BaseBullet> shoot() {
        List<BaseBullet> res = new LinkedList<>();
        // 双排直射子弹：在机体左右两侧各发射一颗子弹
        int baseX = getLocationX();
        int y = getLocationY() + getHeight() / 2;
        int offsetX = 15;
        int speedY = getSpeedY() + 5;
        int power = 20;
        res.add(new EnemyBullet(baseX - offsetX, y, 0, speedY, power));
        res.add(new EnemyBullet(baseX + offsetX, y, 0, speedY, power));
        return res;
    }
}
