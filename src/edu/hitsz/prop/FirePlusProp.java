package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;

public class FirePlusProp extends AbstractProp {

    public FirePlusProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void activate(HeroAircraft heroAircraft) {
        // 超级火力道具：提示更强火力（具体效果在后续实验中实现）
        System.out.println("FirePlusSupply active!");
    }
}
