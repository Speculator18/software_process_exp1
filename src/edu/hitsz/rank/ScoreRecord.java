package edu.hitsz.rank;

import java.time.LocalDateTime;

/**
 * 排行榜中的一条得分记录。
 * 用作 DAO 层的值对象，用来在内存中承载「玩家名 + 得分 + 记录时间」三项信息。
 */
public class ScoreRecord {
    /**
     * 玩家名称
     */
    private String playerName;
    /**
     * 玩家得分
     */
    private int score;
    /**
     * 记录生成时间
     */
    private LocalDateTime recordTime;

    /**
     * 构造一条得分记录
     *
     * @param playerName  玩家名称
     * @param score       玩家得分
     * @param recordTime  记录时间
     */
    public ScoreRecord(String playerName, int score, LocalDateTime recordTime) {
        this.playerName = playerName;
        this.score = score;
        this.recordTime = recordTime;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public LocalDateTime getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(LocalDateTime recordTime) {
        this.recordTime = recordTime;
    }
}
