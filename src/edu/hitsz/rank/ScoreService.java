package edu.hitsz.rank;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ScoreService {

    private final ScoreDao scoreDao;
    private final GameDifficulty gameDifficulty;
    private final String defaultPlayerName;

    public ScoreService(GameDifficulty gameDifficulty, ScoreDao scoreDao, String defaultPlayerName) {
        this.gameDifficulty = gameDifficulty;
        this.scoreDao = scoreDao;
        this.defaultPlayerName = defaultPlayerName;
    }

    public GameDifficulty getGameDifficulty() {
        return gameDifficulty;
    }

    public void saveScore(int score) {
        saveScore(defaultPlayerName, score);
    }

    public void saveScore(String playerName, int score) {
        LocalDateTime now = LocalDateTime.now();
        ScoreRecord record = new ScoreRecord(playerName, score, now);
        scoreDao.add(record);
    }

    public List<ScoreRecord> getSortedRecords() {
        List<ScoreRecord> records = new ArrayList<>(scoreDao.getAll());
        records.sort(Comparator.comparingInt(ScoreRecord::getScore)
                .reversed()
                .thenComparing(ScoreRecord::getRecordTime));
        return records;
    }

    public void deleteRecord(ScoreRecord record) {
        scoreDao.delete(record);
    }
}
