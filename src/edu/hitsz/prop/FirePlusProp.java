package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.shoot.RingShootStrategy;

/**
 * 超级火力道具：
 * 客户端角色（Client），生效后将英雄机当前弹道切换为环射弹道。
 */
public class FirePlusProp extends AbstractProp {

    public FirePlusProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void activate(HeroAircraft heroAircraft) {
        // E5 超级火力道具：临时切换为环射弹道，持续 5 秒后恢复直射
        heroAircraft.applyShootStrategyForDuration(new RingShootStrategy(), 5000);
        System.out.println("FirePlusSupply active!");
    }
}
