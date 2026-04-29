package edu.hitsz.application;

import edu.hitsz.rank.GameDifficulty;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * 程序入口
 * @author hitsz
 */
public class Main {

    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;

    // E5 使用 CardLayout 管理开始菜单、游戏界面和排行榜等多个界面
    public static final CardLayout CARD_LAYOUT = new CardLayout();
    public static final JPanel MAIN_PANEL = new JPanel(CARD_LAYOUT);

    public static void main(String[] args) {

        System.out.println("Hello Aircraft War");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame("Aircraft War");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false);
        frame.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
                WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // E5 将主面板加入窗口，后续通过 CARD_LAYOUT 切换不同界面
        frame.add(MAIN_PANEL);
        StartMenu startMenu = new StartMenu();
        MAIN_PANEL.add(startMenu, "StartMenu");

        frame.setVisible(true);
    }

    // E5 由开始菜单调用，根据难度创建游戏面板并切换到游戏界面
    public static void startGame(GameDifficulty difficulty) {
        Game game;
        switch (difficulty) {
            case EASY:
                game = new EasyGame();
                break;
            case MEDIUM:
                game = new NormalGame();
                break;
            case HARD:
            default:
                game = new HardGame();
                break;
        }
        MAIN_PANEL.add(game, "Game");
        CARD_LAYOUT.show(MAIN_PANEL, "Game");
        game.action();
    }
}
