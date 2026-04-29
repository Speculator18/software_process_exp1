package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.SoundManager;
import edu.hitsz.observer.SupplyEvent;
import edu.hitsz.observer.SupplyObserver;
import edu.hitsz.observer.SupplySubject;

import java.util.ArrayList;
import java.util.List;

public class BombProp extends AbstractProp implements SupplySubject {

    private final List<SupplyObserver> observers = new ArrayList<>();

    public BombProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    public BombProp(int locationX, int locationY, int speedX, int speedY, List<SupplyObserver> observers) {
        super(locationX, locationY, speedX, speedY);
        if (observers != null) {
            this.observers.addAll(observers);
        }
    }

    @Override
    public void activate(HeroAircraft heroAircraft) {
        System.out.println("BombSupply active!");
        // E5 炸弹道具生效时播放爆炸音效
        SoundManager.playBombExplosion();
        notifyObservers(SupplyEvent.BOMB);
    }

    @Override
    public void addObserver(SupplyObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(SupplyObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(SupplyEvent event) {
        for (SupplyObserver observer : observers) {
            observer.update(event);
        }
    }
}
