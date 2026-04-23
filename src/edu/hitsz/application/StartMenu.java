package edu.hitsz.application;

import edu.hitsz.rank.GameDifficulty;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class StartMenu extends JPanel {

    public StartMenu() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(40, 40, 40, 40));

        JLabel titleLabel = new JLabel("选择游戏难度", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 32));

        JLabel subTitleLabel = new JLabel("请选择适合你的挑战强度", SwingConstants.CENTER);
        subTitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));

        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        subTitleLabel.setAlignmentX(CENTER_ALIGNMENT);
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(10));
        headerPanel.add(subTitleLabel);
        add(headerPanel, BorderLayout.NORTH);

        JButton easyButton = new JButton("轻松 · 简单模式");
        JButton mediumButton = new JButton("均衡 · 普通模式");
        JButton hardButton = new JButton("挑战 · 困难模式");

        Dimension buttonSize = new Dimension(220, 60);
        Font buttonFont = new Font("SansSerif", Font.BOLD, 18);
        easyButton.setPreferredSize(buttonSize);
        mediumButton.setPreferredSize(buttonSize);
        hardButton.setPreferredSize(buttonSize);
        easyButton.setFont(buttonFont);
        mediumButton.setFont(buttonFont);
        hardButton.setFont(buttonFont);

        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(Box.createVerticalGlue());
        easyButton.setAlignmentX(CENTER_ALIGNMENT);
        mediumButton.setAlignmentX(CENTER_ALIGNMENT);
        hardButton.setAlignmentX(CENTER_ALIGNMENT);
        centerPanel.add(easyButton);
        centerPanel.add(Box.createVerticalStrut(25));
        centerPanel.add(mediumButton);
        centerPanel.add(Box.createVerticalStrut(25));
        centerPanel.add(hardButton);
        centerPanel.add(Box.createVerticalGlue());
        add(centerPanel, BorderLayout.CENTER);

        easyButton.addActionListener(e -> Main.startGame(GameDifficulty.EASY));
        mediumButton.addActionListener(e -> Main.startGame(GameDifficulty.MEDIUM));
        hardButton.addActionListener(e -> Main.startGame(GameDifficulty.HARD));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage background = ImageManager.BACKGROUND_IMAGE;
        if (background != null) {
            Graphics g2 = g.create();
            if (g2 instanceof java.awt.Graphics2D) {
                ((java.awt.Graphics2D) g2).setRenderingHint(
                        RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            }
            g2.drawImage(background, 0, 0, getWidth(), getHeight(), null);
            g2.dispose();
        }
    }
}
