package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;

import java.util.LinkedList;
import java.util.List;

public class EliteProEnemy extends AbstractEnemy {

    public EliteProEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public void forward() {
        super.forward();
        // 到达左右边界后反向，保证王牌敌机在屏幕内左右来回移动
        if (locationX <= getWidth() / 2 || locationX >= Main.WINDOW_WIDTH - getWidth() / 2) {
            speedX = -speedX;
        }
    }

    @Override
    public List<BaseBullet> shoot() {
        List<BaseBullet> res = new LinkedList<>();
        // 扇形散射：左、中、右三颗子弹，speedX 分别为 -2 / 0 / 2
        int x = getLocationX();
        int y = getLocationY() + getHeight() / 2;
        int speedY = getSpeedY() + 5;
        int power = 20;
        res.add(new EnemyBullet(x - 15, y, -2, speedY, power));
        res.add(new EnemyBullet(x, y, 0, speedY, power));
        res.add(new EnemyBullet(x + 15, y, 2, speedY, power));
        return res;
    }
}
