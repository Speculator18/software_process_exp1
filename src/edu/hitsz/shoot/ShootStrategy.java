package edu.hitsz.shoot;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;
import java.util.List;

/**
 * 弹道策略接口（Strategy）：
 * 定义所有弹道算法的统一抽象，使不同算法可以在运行时自由切换。
 */
public interface ShootStrategy {

    /**
     * 执行具体弹道算法，根据传入的飞机状态生成子弹列表。
     *
     * @param aircraft 作为上下文的飞机对象（英雄机或各类敌机）
     * @return 本次射击产生的子弹列表
     */
    List<BaseBullet> shoot(AbstractAircraft aircraft);
}
