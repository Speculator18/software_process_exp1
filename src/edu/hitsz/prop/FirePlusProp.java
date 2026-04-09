package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.shoot.RingShootStrategy;

public class FirePlusProp extends AbstractProp {

    public FirePlusProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void activate(HeroAircraft heroAircraft) {
        heroAircraft.setShootStrategy(new RingShootStrategy());
        System.out.println("FirePlusSupply active!");
    }
}
