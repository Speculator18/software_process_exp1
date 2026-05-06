package edu.hitsz.basic;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

import java.awt.image.BufferedImage;

/**
 * 可飞行对象的基类
 * 
 * @author hitsz
 */
public abstract class AbstractFlyingObject {

    // locationX,locationY为图片中心位置坐标
    protected int locationX;
    protected int locationY;

    // x,y轴移动速度
    protected int speedX;
    protected int speedY;

    // 图片, null 表示未设置
    protected BufferedImage image = null;

    // x 轴长度，根据图片尺寸获得, -1 表示未设置
    protected int width = -1;

    // y 轴长度，根据图片尺寸获得, -1 表示未设置
    protected int height = -1;

    // 有效（生存）标记，标记为 false的对象会在下次刷新时清除
    protected boolean isValid = true;

    public AbstractFlyingObject() {
    }

    public AbstractFlyingObject(int locationX, int locationY, int speedX, int speedY) {
        this.locationX = locationX;
        this.locationY = locationY;
        this.speedX = speedX;
        this.speedY = speedY;
    }

    /**
     * 可飞行对象根据速度移动
     * 若飞行对象触碰到横向边界，横向速度反向
     */
    public void forward() {
        locationX += speedX;
        locationY += speedY;
        if (locationX <= 0 || locationX >= Main.WINDOW_WIDTH) {
            // 横向超出边界后反向
            speedX = -speedX;
        }
    }

    /**
     * 碰撞检测，当对方坐标进入我方范围，判定我方击中<br>
     * 对方与我方覆盖区域有交叉即判定撞击。
     * <br>
     * 为提升操作手感，碰撞判定使用可缩放的碰撞箱（Hitbox），而不是直接使用图片原始宽高。<br>
     * 碰撞箱宽高 = 图片宽高 * 对应缩放系数，缩放系数由 getHitboxWidthScale/getHitboxHeightScale 提供。<br>
     * 默认缩放系数为 1.0，子类可覆盖以缩小或放大判定范围。
     * 
     * @param flyingObject 撞击对方
     * @return true: 我方被击中; false 我方未被击中
     */
    public boolean crash(AbstractFlyingObject flyingObject) {
        int x = flyingObject.getLocationX();
        int y = flyingObject.getLocationY();

        double thisHalfWidth = getWidth() * getHitboxWidthScale() / 2.0;
        double thisHalfHeight = getHeight() * getHitboxHeightScale() / 2.0;
        double otherHalfWidth = flyingObject.getWidth() * flyingObject.getHitboxWidthScale() / 2.0;
        double otherHalfHeight = flyingObject.getHeight() * flyingObject.getHitboxHeightScale() / 2.0;

        return x + otherHalfWidth > locationX - thisHalfWidth
                && x - otherHalfWidth < locationX + thisHalfWidth
                && y + otherHalfHeight > locationY - thisHalfHeight
                && y - otherHalfHeight < locationY + thisHalfHeight;
    }

    /**
     * 碰撞箱在 x 方向的缩放系数。
     * 默认返回 1.0，子类可通过覆盖该方法缩小横向碰撞体积。
     */
    protected double getHitboxWidthScale() {
        return 1.0;
    }

    /**
     * 碰撞箱在 y 方向的缩放系数。
     * 默认返回 1.0，子类可通过覆盖该方法缩小纵向碰撞体积。
     */
    protected double getHitboxHeightScale() {
        return 1.0;
    }

    public int getLocationX() {
        return locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public void setLocation(double locationX, double locationY) {
        this.locationX = (int) locationX;
        this.locationY = (int) locationY;
    }

    public int getSpeedY() {
        return speedY;
    }

    public int getSpeedX() {
        return speedX;
    }

    public void setSpeed(int speedX, int speedY) {
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public BufferedImage getImage() {
        if (image == null) {
            image = ImageManager.get(this);
        }
        return image;
    }

    public int getWidth() {
        if (width == -1) {
            // 若未设置，则查询图片宽度并设置
            width = ImageManager.get(this).getWidth();
        }
        return width;
    }

    public int getHeight() {
        if (height == -1) {
            // 若未设置，则查询图片高度并设置
            height = ImageManager.get(this).getHeight();
        }
        return height;
    }

    /**
     * 标记消失
     * isValid = false.
     * notValid() => true.
     */
    public void vanish() {
        isValid = false;
    }

    public boolean notValid() {
        return !this.isValid;
    }

}
