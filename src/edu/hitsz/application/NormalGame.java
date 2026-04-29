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
        enemyShootCycle = 20;
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
        enemyShootCycle = Math.max(10, enemyShootCycle - 1);
        System.out.printf("Normal difficulty increased: score=%d, enemySpeedBonus=%d, enemySpawnCycle=%.0f, enemyShootCycle=%.0f%n",
                score, enemySpeedBonus, enemySpawnCycle, enemyShootCycle);
    }
}
