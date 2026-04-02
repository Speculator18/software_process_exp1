package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;

public class FireProp extends AbstractProp {

    public FireProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void activate(HeroAircraft heroAircraft) {
        // 火力道具：提示火力提升（具体策略在后续实验中实现）
        System.out.println("FireSupply active!");
    }
}
