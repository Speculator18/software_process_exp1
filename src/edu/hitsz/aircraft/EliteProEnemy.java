package edu.hitsz.aircraft;

import edu.hitsz.application.Main;

public class EliteProEnemy extends AbstractEnemy {

    public EliteProEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
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
