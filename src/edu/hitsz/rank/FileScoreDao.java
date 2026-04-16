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

public class FileScoreDao implements ScoreDao {
    private final GameDifficulty gameDifficulty;
    private final String filePath;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public FileScoreDao(GameDifficulty gameDifficulty, String filePath) {
        this.gameDifficulty = gameDifficulty;
        this.filePath = filePath;
    }

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

