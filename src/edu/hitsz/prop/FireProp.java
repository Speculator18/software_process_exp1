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
        // E5 普通火力道具：将英雄机弹道切换为散射，并在 5 秒后自动恢复直射
        heroAircraft.applyShootStrategyForDuration(new ScatterShootStrategy(), 5000);
        System.out.println("FireSupply active!");
    }
}
