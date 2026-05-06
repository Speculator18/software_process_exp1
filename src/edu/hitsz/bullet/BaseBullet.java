package edu.hitsz.bullet;

import edu.hitsz.application.Main;
import edu.hitsz.basic.AbstractFlyingObject;

/**
 * 子弹基类
 * @author hitsz
 */
public abstract class BaseBullet extends AbstractFlyingObject {

    private int power = 0;

    public BaseBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        super(locationX, locationY, speedX, speedY);
        this.power = power;
    }

    @Override
    public void forward() {
        super.forward();

        // 判定 x 轴出界
        if (locationX <= 0 || locationX >= Main.WINDOW_WIDTH) {
            vanish();
        }

        // 判定 y 轴出界
        if (speedY > 0 && locationY >= Main.WINDOW_HEIGHT ) {
            // 向下飞行出界
            vanish();
        }else if (locationY <= 0){
            // 向上飞行出界
            vanish();
        }
    }

    public int getPower() {
        return power;
    }

    /**
     * 子弹类默认的碰撞箱缩放系数。
     * 子弹图片通常存在透明留白，缩小判定可避免“擦边即中”的不适感。
     */
    @Override
    protected double getHitboxWidthScale() {
        return 0.7;
    }

    /**
     * 子弹类默认的碰撞箱缩放系数（y 方向）。
     */
    @Override
    protected double getHitboxHeightScale() {
        return 0.7;
    }
}
