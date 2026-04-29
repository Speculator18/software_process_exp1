package edu.hitsz.application;

import edu.hitsz.rank.GameDifficulty;

public class EasyGame extends Game {

    public EasyGame() {
        super(GameDifficulty.EASY);
    }

    @Override
    protected void initDifficultyParams() {
        enemyMaxNumber = 4;
        enemySpawnCycle = 25;
        shootCycle = 22;
        enemyShootCycle = 22;
        bossScoreThreshold = Integer.MAX_VALUE;
    }

    @Override
    protected boolean isBossEnabled() {
        return false;
    }
}
