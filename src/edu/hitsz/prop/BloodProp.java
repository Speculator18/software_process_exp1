package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;

public class BloodProp extends AbstractProp {

    public BloodProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void activate(HeroAircraft heroAircraft) {
        if (heroAircraft == null) {
            return;
        }
        // 加血道具：为英雄机恢复一定生命值（不超过最大值）
        heroAircraft.increaseHp(30);
    }
}
