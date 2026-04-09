package edu.hitsz.aircraft;

import edu.hitsz.application.Main;

/**
 * Boss 敌机：
 * 悬浮于界面上方，只做左右移动，不向下移动。
 */
public class BossEnemy extends AbstractEnemy {

    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public void forward() {
        // 到达左右边界后反向，实现水平往返运动
        if (locationX <= getWidth() / 2 || locationX >= Main.WINDOW_WIDTH - getWidth() / 2) {
            speedX = -speedX;
        }
        // 只在 x 方向移动，不调用父类的 y 方向运动逻辑
        locationX += speedX;
    }
}
