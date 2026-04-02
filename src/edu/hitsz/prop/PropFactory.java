package edu.hitsz.prop;

/**
 * 道具简单工厂：
 * 根据传入的类型编号统一创建不同具体道具对象，
 * 调用方只依赖抽象父类 AbstractProp。
 */
public class PropFactory {

    /**
     * 根据类型编号创建道具：
     * 0-血包，1-火力，2-超级火力，3-炸弹，4-冰冻。
     */
    public static AbstractProp createProp(int type, int x, int y, int speedX, int speedY) {
        switch (type) {
            case 0:
                return new BloodProp(x, y, speedX, speedY);
            case 1:
                return new FireProp(x, y, speedX, speedY);
            case 2:
                return new FirePlusProp(x, y, speedX, speedY);
            case 3:
                return new BombProp(x, y, speedX, speedY);
            case 4:
                return new FreezeProp(x, y, speedX, speedY);
            default:
                throw new IllegalArgumentException("Unknown prop type: " + type);
        }
    }
}
