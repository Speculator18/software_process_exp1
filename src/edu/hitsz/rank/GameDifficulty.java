package edu.hitsz.rank;

/**
 * 游戏难度枚举。
 * 不同难度对应不同的排行榜存储文件，实现「一难度一排行榜」的数据隔离。
 */
public enum GameDifficulty {
    /**
     * 简单模式
     */
    EASY,
    /**
     * 普通模式
     */
    MEDIUM,
    /**
     * 困难模式
     */
    HARD
}
