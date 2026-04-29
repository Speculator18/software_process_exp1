package edu.hitsz.application;

import edu.hitsz.rank.GameDifficulty;

public class HardGame extends Game {

    public HardGame() {
        super(GameDifficulty.HARD);
    }

    @Override
    protected void initDifficultyParams() {
        enemyMaxNumber = 7;
        enemySpawnCycle = 18;
        shootCycle = 18;
        bossScoreThreshold = 200;
    }

    @Override
    protected int getBossHp(int spawnCount) {
        return 1200 + spawnCount * 200;
    }

    @Override
    protected void increaseDifficultyIfNeeded() {
        int step = 150;
        if (score - lastDifficultyIncreaseScore < step) {
            return;
        }
        lastDifficultyIncreaseScore = score;
        enemySpeedBonus += 2;
        enemySpawnCycle = Math.max(8, enemySpawnCycle - 1);
        shootCycle = Math.max(8, shootCycle - 1);
    }
}

