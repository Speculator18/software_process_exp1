package edu.hitsz.shoot;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;
import java.util.LinkedList;
import java.util.List;

/**
 * 不发射弹道策略：
 * 适用于普通敌机等完全不会开火的飞机。
 */
public class NoShootStrategy implements ShootStrategy {

    public List<BaseBullet> shoot(AbstractAircraft aircraft) {
        return new LinkedList<>();
    }
}
