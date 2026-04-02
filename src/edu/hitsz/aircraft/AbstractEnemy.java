package edu.hitsz.aircraft;

import edu.hitsz.application.Main;

public abstract class AbstractEnemy extends AbstractAircraft {

    public AbstractEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public void forward() {
        super.forward();
        // 敌机飞出屏幕下边界后标记为无效，释放名额以生成新的敌机
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }
}
