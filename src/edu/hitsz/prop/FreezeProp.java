package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;

public class FreezeProp extends AbstractProp {

    public FreezeProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void activate(HeroAircraft heroAircraft) {
        // 冰冻道具：当前实验同样只需输出提示信息
        System.out.println("FreezeSupply active!");
    }
}
