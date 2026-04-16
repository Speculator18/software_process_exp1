package edu.hitsz.rank;

import java.time.LocalDateTime;

public class ScoreRecord {
    private String playerName;
    private int score;
    private LocalDateTime recordTime;

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

