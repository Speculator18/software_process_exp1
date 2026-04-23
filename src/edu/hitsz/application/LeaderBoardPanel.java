package edu.hitsz.application;

import edu.hitsz.rank.ScoreRecord;
import edu.hitsz.rank.ScoreService;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

public class LeaderBoardPanel extends JPanel {

    private final ScoreService scoreService;
    private final DefaultTableModel model;
    private final JTable table;
    private List<ScoreRecord> records;

    public LeaderBoardPanel(ScoreService scoreService) {
        this.scoreService = scoreService;
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("得分排行榜 - 难度: " + scoreService.getGameDifficulty(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        add(titleLabel, BorderLayout.NORTH);

        String[] columnNames = {"名次", "玩家名", "得分", "记录时间"};
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        table.setRowHeight(24);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(480, 600));
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        JButton deleteButton = new JButton("删除所选记录");
        JButton backButton = new JButton("返回主菜单");
        bottomPanel.add(deleteButton);
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        deleteButton.addActionListener(e -> deleteSelectedRecord());
        backButton.addActionListener(e -> Main.CARD_LAYOUT.show(Main.MAIN_PANEL, "StartMenu"));

        refreshTable();
    }

    private void refreshTable() {
        records = scoreService.getSortedRecords();
        model.setRowCount(0);
        int rank = 1;
        for (ScoreRecord record : records) {
            Object[] row = {
                    rank,
                    record.getPlayerName(),
                    record.getScore(),
                    record.getRecordTime().toString().replace('T', ' ')
            };
            model.addRow(row);
            rank++;
        }
    }

    private void deleteSelectedRecord() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "请先选择要删除的记录", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int result = JOptionPane.showConfirmDialog(this, "确定要删除选中的记录吗？", "确认删除",
                JOptionPane.YES_NO_OPTION);
        if (result != JOptionPane.YES_OPTION) {
            return;
        }
        ScoreRecord record = records.get(row);
        scoreService.deleteRecord(record);
        refreshTable();
    }
}

