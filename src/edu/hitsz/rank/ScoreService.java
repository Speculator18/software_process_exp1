package edu.hitsz.rank;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 排行榜业务服务类。
 * 封装与得分记录相关的业务逻辑，避免 Game 直接操作 DAO 或文件：
 * 1）负责保存一局游戏结束时的得分；
 * 2）负责按得分排序并生成排行榜数据；
 * 3）负责在控制台打印排行榜。
 */
public class ScoreService {
    /**
     * 底层数据访问对象，由构造函数注入，实现解耦。
     */
    private final ScoreDao scoreDao;
    /**
     * 当前游戏难度，仅用于输出时展示。
     */
    private final GameDifficulty gameDifficulty;
    /**
     * 默认玩家名，本次实验不要求输入玩家名称时使用。
     */
    private final String defaultPlayerName;

    public ScoreService(GameDifficulty gameDifficulty, ScoreDao scoreDao, String defaultPlayerName) {
        this.gameDifficulty = gameDifficulty;
        this.scoreDao = scoreDao;
        this.defaultPlayerName = defaultPlayerName;
    }

    /**
     * 保存一局游戏的得分。
     * 使用默认玩家名和当前系统时间生成 ScoreRecord，再交给 DAO 持久化。
     *
     * @param score 本局总得分
     */
    public void saveScore(int score) {
        LocalDateTime now = LocalDateTime.now();
        ScoreRecord record = new ScoreRecord(defaultPlayerName, score, now);
        scoreDao.add(record);
    }

    /**
     * 获取按「得分从高到低，若得分相同则时间从早到晚」排序后的记录列表。
     *
     * @return 排序后的记录列表
     */
    public List<ScoreRecord> getSortedRecords() {
        List<ScoreRecord> records = new ArrayList<>(scoreDao.getAll());
        records.sort(Comparator.comparingInt(ScoreRecord::getScore)
                .reversed()
                .thenComparing(ScoreRecord::getRecordTime));
        return records;
    }

    /**
     * 在控制台打印当前难度下的得分排行榜。
     * 输出内容包括：
     * 1）标题与分隔线；
     * 2）当前难度；
     * 3）每条记录的名次、玩家名、得分和记录时间。
     */
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
