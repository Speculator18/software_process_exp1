package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.shoot.ScatterShootStrategy;

/**
 * 火力道具：
 * 客户端角色（Client），生效后将英雄机当前弹道切换为散射弹道。
 */
public class FireProp extends AbstractProp {

    public FireProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void activate(HeroAircraft heroAircraft) {
        heroAircraft.setShootStrategy(new ScatterShootStrategy());
        System.out.println("FireSupply active!");
    }
}
