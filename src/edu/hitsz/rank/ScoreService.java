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

    public void saveScore(int score) {
        LocalDateTime now = LocalDateTime.now();
        ScoreRecord record = new ScoreRecord(defaultPlayerName, score, now);
        scoreDao.add(record);
    }

    public List<ScoreRecord> getSortedRecords() {
        List<ScoreRecord> records = new ArrayList<>(scoreDao.getAll());
        records.sort(Comparator.comparingInt(ScoreRecord::getScore).reversed().thenComparing(ScoreRecord::getRecordTime));
        return records;
    }

    public void printRanking() {
        List<ScoreRecord> records = getSortedRecords();
        System.out.println("********************************");
        System.out.println("            得分排行榜");
        System.out.println("********************************");
        System.out.println("难度: " + gameDifficulty);
        System.out.println("名次    玩家名    得分    记录时间");
        int rank = 1;
        for (ScoreRecord record : records) {
            System.out.printf("%d    %s    %d    %s%n", rank, record.getPlayerName(), record.getScore(),
                    record.getRecordTime().toString().replace('T', ' '));
            rank++;
        }
    }
}

