package edu.hitsz.aircraft;

import edu.hitsz.application.Main;

public class ElitePlusEnemy extends AbstractEnemy {

    public ElitePlusEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public void forward() {
        super.forward();
        if (locationX <= getWidth() / 2 || locationX >= Main.WINDOW_WIDTH - getWidth() / 2) {
            speedX = -speedX;
        }
    }
}
