package edu.hitsz.rank;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// E5 实验五：为排行榜界面提供排序、保存和删除分数记录的业务服务
public class ScoreService {

    private final ScoreDao scoreDao;
    private final GameDifficulty gameDifficulty;
    private final String defaultPlayerName;

    public ScoreService(GameDifficulty gameDifficulty, ScoreDao scoreDao, String defaultPlayerName) {
        this.gameDifficulty = gameDifficulty;
        this.scoreDao = scoreDao;
        this.defaultPlayerName = defaultPlayerName;
    }

    // E5 供排行榜界面显示当前难度用
    public GameDifficulty getGameDifficulty() {
        return gameDifficulty;
    }

    // E5 使用默认玩家名保存成绩（例如未填写姓名时）
    public void saveScore(int score) {
        saveScore(defaultPlayerName, score);
    }

    // E5 使用指定玩家名与当前时间构造记录并交给 DAO 持久化
    public void saveScore(String playerName, int score) {
        LocalDateTime now = LocalDateTime.now();
        ScoreRecord record = new ScoreRecord(playerName, score, now);
        scoreDao.add(record);
    }

    // E5 返回按得分从高到低排序后的记录列表
    public List<ScoreRecord> getSortedRecords() {
        List<ScoreRecord> records = new ArrayList<>(scoreDao.getAll());
        records.sort(Comparator.comparingInt(ScoreRecord::getScore)
                .reversed()
                .thenComparing(ScoreRecord::getRecordTime));
        return records;
    }

    // E5 删除指定记录，供排行榜界面调用
    public void deleteRecord(ScoreRecord record) {
        scoreDao.delete(record);
    }
}
