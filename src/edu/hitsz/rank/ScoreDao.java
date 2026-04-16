package edu.hitsz.rank;

import java.util.List;

/**
 * 排行榜数据访问对象接口。
 * 定义对 ScoreRecord 执行的标准数据操作，屏蔽具体存储方式（文件 / 数据库等）的差异。
 */
public interface ScoreDao {

    /**
     * 获取当前难度下的所有得分记录。
     *
     * @return 记录列表，若没有记录则返回空列表
     */
    List<ScoreRecord> getAll();

    /**
     * 新增一条记录。
     *
     * @param record 要持久化的得分记录
     */
    void add(ScoreRecord record);

    /**
     * 删除一条记录。
     * 具体匹配规则由实现类定义，本实验中按「玩家名 + 得分 + 时间」进行精确匹配。
     *
     * @param record 要删除的得分记录
     */
    void delete(ScoreRecord record);
}
