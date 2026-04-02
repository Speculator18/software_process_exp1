package edu.hitsz.prop;

public class PropFactory {

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

