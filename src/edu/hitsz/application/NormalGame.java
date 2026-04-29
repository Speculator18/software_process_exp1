package edu.hitsz.application;

import edu.hitsz.rank.GameDifficulty;

public class NormalGame extends Game {

    public NormalGame() {
        super(GameDifficulty.MEDIUM);
    }

    @Override
    protected void initDifficultyParams() {
        enemyMaxNumber = 6;
        enemySpawnCycle = 20;
        shootCycle = 20;
        bossScoreThreshold = 200;
    }

    @Override
    protected int getBossHp(int spawnCount) {
        return 1200;
    }

    @Override
    protected void increaseDifficultyIfNeeded() {
        int step = 200;
        if (score - lastDifficultyIncreaseScore < step) {
            return;
        }
        lastDifficultyIncreaseScore = score;
        enemySpeedBonus += 1;
        enemySpawnCycle = Math.max(10, enemySpawnCycle - 1);
        shootCycle = Math.max(10, shootCycle - 1);
    }
}

