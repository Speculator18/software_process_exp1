package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;

/**
 * 炸弹道具：
 * 本次实验中只需要在控制台输出提示信息，
 * 具体的全屏伤害效果将在后续迭代中通过观察者模式实现。
 */
public class BombProp extends AbstractProp {

    public BombProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void activate(HeroAircraft heroAircraft) {
        System.out.println("BombSupply active!");
    }
}
