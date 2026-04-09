package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;

/**
 * 冰冻道具：
 * 本次实验同样只需在控制台输出提示信息，
 * 实际的减速 / 冻结效果将在后续实验中补充。
 */
public class FreezeProp extends AbstractProp {

    public FreezeProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void activate(HeroAircraft heroAircraft) {
        System.out.println("FreezeSupply active!");
    }
}
