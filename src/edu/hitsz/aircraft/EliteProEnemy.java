package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;

import java.util.LinkedList;
import java.util.List;

public class EliteProEnemy extends AbstractEnemy {

    public EliteProEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public void forward() {
        super.forward();
    }

    @Override
    public List<BaseBullet> shoot() {
        return new LinkedList<>();
    }
}

