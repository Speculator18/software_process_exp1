package edu.hitsz.observer;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.application.Game;
import edu.hitsz.application.MusicManager;
import edu.hitsz.application.SoundManager;

import java.util.List;

public class EnemyAircraftObserver implements SupplyObserver {

    private final List<AbstractAircraft> enemyAircrafts;
    private final Game game;

    public EnemyAircraftObserver(List<AbstractAircraft> enemyAircrafts, Game game) {
        this.enemyAircrafts = enemyAircrafts;
        this.game = game;
    }

    @Override
    public void update(SupplyEvent event) {
        if (event == SupplyEvent.BOMB) {
            handleBomb();
            return;
        }
        if (event == SupplyEvent.FREEZE) {
            game.freezeEnemies(5000);
        }
    }

    private void handleBomb() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            if (enemyAircraft.notValid()) {
                continue;
            }
            if (enemyAircraft instanceof BossEnemy) {
                enemyAircraft.decreaseHp(200);
                if (enemyAircraft.notValid()) {
                    MusicManager.stopBossMusic();
                    if (SoundManager.isEnabled()) {
                        MusicManager.startBackgroundMusic();
                    }
                    game.addScore(10);
                }
                continue;
            }
            enemyAircraft.vanish();
            game.addScore(10);
        }
    }
}

