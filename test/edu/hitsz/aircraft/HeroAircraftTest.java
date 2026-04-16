package edu.hitsz.aircraft;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * HeroAircraftTest
 *
 * 对英雄机类 HeroAircraft 进行单元测试，主要覆盖三方面功能：
 * 1）单例获取：验证 getInstance() 始终返回同一个英雄机对象；
 * 2）生命值管理：验证父类 AbstractAircraft 中 increaseHp / decreaseHp / getHp 的行为；
 * 3）碰撞检测：验证父类 AbstractFlyingObject 中 crash() 的碰撞判定逻辑。
 *
 * 其中 2）、3）都测试到了父类的方法，满足“至少选择 3 个方法（含父类方法）”的实验要求。
 */
class HeroAircraftTest {

    /**
     * 在每个测试用例执行前使用的 HeroAircraft 实例。
     * 由于 HeroAircraft 采用单例模式，这里每次从 getInstance() 获取同一个对象，
     * 并在 setUp() 中重置其坐标和生命值，保证各个测试之间互不影响。
     */
    private HeroAircraft hero;

    /**
     * 测试前置步骤：
     * 1）通过单例方法 HeroAircraft.getInstance() 获取英雄机对象；
     * 2）将英雄机位置设置为固定坐标 (100, 100)，方便之后的碰撞测试；
     * 3）先扣掉当前全部生命值，再加回 maxHp，确保每次测试开始时英雄机处于满血状态。
     */
    @BeforeEach
    void setUp() {
        hero = HeroAircraft.getInstance();
        hero.setLocation(100, 100);
        int maxHp = hero.getMaxHp();
        hero.decreaseHp(maxHp);
        hero.increaseHp(maxHp);
    }

    /**
     * 测试目标 1：单例模式
     *
     * 调用两次 HeroAircraft.getInstance()，预期得到的是同一个对象引用。
     * 通过 assertSame(hero, another) 来验证单例模式是否正确实现。
     */
    @Test
    @DisplayName("getInstance 返回同一对象")
    void getInstanceSingleton() {
        HeroAircraft another = HeroAircraft.getInstance();
        assertSame(hero, another);
    }

    /**
     * 测试目标 2：生命值增加与减少（父类 AbstractAircraft 的方法）
     *
     * 用例设计：
     * 1）初始状态下，hp 应等于 maxHp；
     * 2）调用 decreaseHp(30) 后，hp 应减少 30；
     * 3）再调用 increaseHp(10) 后，hp 相当于只比满血少 20；
     * 4）调用 increaseHp(1000) 这类大数时，hp 不得超过 maxHp，上限保护应该生效。
     */
    @Test
    @DisplayName("increaseHp 与 decreaseHp 正常改变生命值")
    void increaseAndDecreaseHp() {
        int maxHp = hero.getMaxHp();
        assertEquals(maxHp, hero.getHp());

        hero.decreaseHp(30);
        assertEquals(maxHp - 30, hero.getHp());

        hero.increaseHp(10);
        assertEquals(maxHp - 20, hero.getHp());

        hero.increaseHp(1000);
        assertEquals(maxHp, hero.getHp());
    }

    /**
     * 测试目标 3：生命值扣到 0 时对象应被标记为无效
     *
     * 用例设计：
     * 1）取出当前 hp；
     * 2）一次性减少 currentHp 点生命值，使英雄机 hp 变为 0；
     * 3）期望 getHp() 返回 0，并且 notValid() 为 true，
     * 说明在 hp 减到 0 的过程中正确调用了 vanish() 完成失效标记。
     */
    @Test
    @DisplayName("生命值扣至 0 时对象失效")
    void decreaseHpToZeroMakesInvalid() {
        int currentHp = hero.getHp();
        hero.decreaseHp(currentHp);
        assertEquals(0, hero.getHp());
        assertTrue(hero.notValid());
    }

    /**
     * 测试目标 4：碰撞检测（父类 AbstractFlyingObject 的 crash 方法）
     *
     * 用例设计：
     * 1）将英雄机位置设置为 (200, 200)；
     * 2）创建一架 MobEnemy 敌机，同样放在 (200, 200)；
     * 3）分别调用 hero.crash(enemy) 和 enemy.crash(hero)，
     * 期望两个方向的碰撞检测结果都为 true。
     *
     * 这个测试验证了当两个飞行物体中心坐标重合时，碰撞检测逻辑能够正确识别撞击。
     */
    @Test
    @DisplayName("HeroAircraft 与 MobEnemy 碰撞检测")
    void crashWithMobEnemy() {
        hero.setLocation(200, 200);
        MobEnemy enemy = new MobEnemy(200, 200, 0, 0, 50);
        boolean result1 = hero.crash(enemy);
        boolean result2 = enemy.crash(hero);
        assertTrue(result1);
        assertTrue(result2);
    }
}
