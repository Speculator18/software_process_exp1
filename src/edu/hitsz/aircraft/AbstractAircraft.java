package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.shoot.ShootStrategy;
import java.util.List;

/**
 * 所有种类飞机的抽象父类（Context）：
 * 负责维护生命值等通用状态，并组合一个 ShootStrategy，
 * 将具体的弹道算法委托给策略对象实现。
 */
public abstract class AbstractAircraft extends AbstractFlyingObject {

    protected int maxHp;
    protected int hp;
    private ShootStrategy shootStrategy;

    public AbstractAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
    }

    public void decreaseHp(int decrease) {
        hp -= decrease;
        if (hp <= 0) {
            hp = 0;
            vanish();
        }
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void increaseHp(int increase) {
        hp += increase;
        if (hp > maxHp) {
            hp = maxHp;
        }
    }

    /**
     * 设置当前使用的弹道策略。
     *
     * @param shootStrategy 具体的弹道策略实现
     */
    public void setShootStrategy(ShootStrategy shootStrategy) {
        this.shootStrategy = shootStrategy;
    }

    /**
     * 统一的射击方法：
     * 由当前绑定的 ShootStrategy 决定如何生成子弹。
     *
     * @return 本次射击产生的子弹列表，若未设置策略则返回空列表
     */
    public List<BaseBullet> shoot() {
        if (shootStrategy == null) {
            return java.util.Collections.emptyList();
        }
        return shootStrategy.shoot(this);
    }

    /**
     * 飞机类默认的碰撞箱缩放系数。
     * 相比图片尺寸适当缩小判定范围，用于提升弹幕躲避的操作手感。
     */
    @Override
    protected double getHitboxWidthScale() {
        return 0.65;
    }

    /**
     * 飞机类默认的碰撞箱缩放系数（y 方向）。
     */
    @Override
    protected double getHitboxHeightScale() {
        return 0.45;
    }

}
