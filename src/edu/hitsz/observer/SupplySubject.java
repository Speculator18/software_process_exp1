package edu.hitsz.observer;

public interface SupplySubject {
    void addObserver(SupplyObserver observer);

    void removeObserver(SupplyObserver observer);

    void notifyObservers(SupplyEvent event);
}

