package edu.hitsz.rank;

import java.util.List;

public interface ScoreDao {
    List<ScoreRecord> getAll();

    void add(ScoreRecord record);

    void delete(ScoreRecord record);
}

