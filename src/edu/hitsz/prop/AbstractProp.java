package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.application.Main;

public abstract class AbstractProp extends AbstractFlyingObject {

    public AbstractProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void forward() {
        super.forward();
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }

    public abstract void activate(HeroAircraft heroAircraft);
}
