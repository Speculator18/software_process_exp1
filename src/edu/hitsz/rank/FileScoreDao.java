package edu.hitsz.rank;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 使用本地文件实现的排行榜 DAO。
 * 一般情况下，不同 GameDifficulty 会绑定不同的文件路径，实现按难度分文件存储。
 */
public class FileScoreDao implements ScoreDao {
    /**
     * 当前 DAO 对应的游戏难度，仅用于标识和调试输出。
     */
    private final GameDifficulty gameDifficulty;
    /**
     * 存储得分记录的文件路径。
     */
    private final String filePath;
    /**
     * 用于在文件中格式化 / 解析时间字段的格式。
     */
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 构造函数。
     *
     * @param gameDifficulty 当前 DAO 所属的游戏难度
     * @param filePath       对应难度下的排行榜文件路径
     */
    public FileScoreDao(GameDifficulty gameDifficulty, String filePath) {
        this.gameDifficulty = gameDifficulty;
        this.filePath = filePath;
    }

    /**
     * 从文件中读取所有记录。
     * 文件格式为每行一条记录：playerName,score,yyyy-MM-dd HH:mm:ss
     */
    @Override
    public List<ScoreRecord> getAll() {
        List<ScoreRecord> records = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            return records;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 3) {
                    // 忽略格式不合法的行
                    continue;
                }
                String playerName = parts[0];
                int score = Integer.parseInt(parts[1]);
                LocalDateTime time = LocalDateTime.parse(parts[2], formatter);
                records.add(new ScoreRecord(playerName, score, time));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }

    /**
     * 将一条记录追加写入文件末尾。
     */
    @Override
    public void add(ScoreRecord record) {
        File file = new File(filePath);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            String line = record.getPlayerName() + "," + record.getScore() + "," + record.getRecordTime().format(formatter);
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 按「玩家名 + 得分 + 时间」精确匹配的方式删除一条记录。
     * 实现思路：
     * 1）先将文件中的所有记录读入内存；
     * 2）在内存列表中删除目标记录；
     * 3）再将剩余记录整体覆盖写回文件。
     */
    @Override
    public void delete(ScoreRecord record) {
        List<ScoreRecord> records = getAll();
        Iterator<ScoreRecord> iterator = records.iterator();
        while (iterator.hasNext()) {
            ScoreRecord r = iterator.next();
            if (r.getPlayerName().equals(record.getPlayerName())
                    && r.getScore() == record.getScore()
                    && r.getRecordTime().equals(record.getRecordTime())) {
                iterator.remove();
                break;
            }
        }
        File file = new File(filePath);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for (ScoreRecord r : records) {
                String line = r.getPlayerName() + "," + r.getScore() + "," + r.getRecordTime().format(formatter);
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
