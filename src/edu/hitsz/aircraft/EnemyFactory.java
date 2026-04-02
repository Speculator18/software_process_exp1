package edu.hitsz.aircraft;

/**
 * 敌机工厂接口（工厂方法模式中的 Creator），
 * 由不同具体工厂实现 createEnemy 创建不同类型敌机。
 */
public interface EnemyFactory {

    AbstractEnemy createEnemy();
}
