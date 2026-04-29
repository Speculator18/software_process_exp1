package edu.hitsz.application;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.aircraft.BossEnemyFactory;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.aircraft.EliteEnemyFactory;
import edu.hitsz.aircraft.ElitePlusEnemy;
import edu.hitsz.aircraft.ElitePlusEnemyFactory;
import edu.hitsz.aircraft.EliteProEnemy;
import edu.hitsz.aircraft.EliteProEnemyFactory;
import edu.hitsz.aircraft.EnemyFactory;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.aircraft.MobEnemyFactory;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.PropFactory;
import edu.hitsz.rank.FileScoreDao;
import edu.hitsz.rank.GameDifficulty;
import edu.hitsz.rank.ScoreDao;
import edu.hitsz.rank.ScoreService;
import edu.hitsz.observer.EnemyAircraftObserver;
import edu.hitsz.observer.EnemyBulletObserver;
import edu.hitsz.observer.SupplyObserver;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 游戏主面板，游戏启动
 * 
 * @author hitsz
 */
public abstract class Game extends JPanel {

    private int backGroundTop = 0;

    // 调度器, 用于定时任务调度
    private final Timer timer;
    // 时间间隔(ms)，控制刷新频率
    private final int timeInterval = 40;

    private final HeroAircraft heroAircraft;
    private final List<AbstractAircraft> enemyAircrafts;
    private final List<BaseBullet> heroBullets;
    private final List<BaseBullet> enemyBullets;
    private final List<AbstractProp> props;
    private final List<AbstractProp> pendingPropsToAdd;

    // 敌机工厂：通过工厂方法模式创建不同类型敌机，
    // 并在各工厂中为敌机绑定对应的弹道策略。
    private final EnemyFactory mobEnemyFactory = new MobEnemyFactory();
    private final EnemyFactory eliteEnemyFactory = new EliteEnemyFactory();
    private final EnemyFactory elitePlusEnemyFactory = new ElitePlusEnemyFactory();
    private final EnemyFactory eliteProEnemyFactory = new EliteProEnemyFactory();
    private final BossEnemyFactory bossEnemyFactory = new BossEnemyFactory();

    // 屏幕中出现的敌机最大数量
    protected int enemyMaxNumber = 5;

    // 敌机生成周期（单位：帧计数）
    protected double enemySpawnCycle = 20;
    private int enemySpawnCounter = 0;

    // 英雄机射击周期（单位：帧计数）
    protected double shootCycle = 20;
    private int shootCounter = 0;

    // 敌机射击周期（单位：帧计数）
    protected double enemyShootCycle = 20;
    private int enemyShootCounter = 0;

    // 当前玩家分数
    protected int score = 0;

    // Boss 生成控制：分数达到阈值且当前没有存活 Boss 时，触发一次 Boss 出现
    protected int bossScoreThreshold = 200;
    private int nextBossScoreThreshold = bossScoreThreshold;
    private int bossSpawnCount = 0;

    // 冰冻控制：冻结期间敌机暂停移动与射击
    private long freezeUntilMillis = 0;

    private final List<SupplyObserver> supplyObservers;

    // E6 用于实现随分数递增的难度曲线
    protected int enemySpeedBonus = 0;
    protected int lastDifficultyIncreaseScore = 0;

    // 游戏结束标志
    private boolean gameOverFlag = false;

    /**
     * 排行榜业务服务。
     * 负责在游戏结束时保存得分并打印排行榜，具体持久化细节由 DAO 层实现。
     */
    private final ScoreService scoreService;

    protected final GameDifficulty gameDifficulty;

    protected Game(GameDifficulty gameDifficulty) {
        this.gameDifficulty = gameDifficulty;

        heroAircraft = HeroAircraft.getInstance();
        heroAircraft.reset();

        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        props = new LinkedList<>();
        pendingPropsToAdd = new LinkedList<>();

        initDifficultyParams();
        nextBossScoreThreshold = bossScoreThreshold;

        new HeroController(this, heroAircraft);

        this.timer = new Timer("game-action-timer", true);

        supplyObservers = new LinkedList<>();
        supplyObservers.add(new EnemyBulletObserver(enemyBullets));
        supplyObservers.add(new EnemyAircraftObserver(enemyAircrafts, this));

        try {
            // E5 根据难度切换背景图，使不同模式有不同地图效果
            ImageManager.setBackground(gameDifficulty);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        String filePath;
        switch (gameDifficulty) {
            case EASY:
                filePath = "scores_easy.txt";
                break;
            case MEDIUM:
                filePath = "scores_medium.txt";
                break;
            case HARD:
            default:
                filePath = "scores_hard.txt";
                break;
        }
        ScoreDao scoreDao = new FileScoreDao(gameDifficulty, filePath);
        scoreService = new ScoreService(gameDifficulty, scoreDao, "player");

        // E5 游戏开始时根据音效开关决定是否开启普通背景音乐
        if (SoundManager.isEnabled()) {
            MusicManager.startBackgroundMusic();
        }
    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public final void action() {

        // 定时任务：绘制、对象产生、碰撞判定、及结束判定
        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                // 按固定周期生成普通敌机
                if (!isEnemyFrozen()) {
                    spawnEnemies();
                    spawnBossIfNeeded();
                    increaseDifficultyIfNeeded();
                }

                // 飞机发射子弹
                shootAction();
                // 子弹移动
                bulletsMoveAction();
                // 飞机移动
                aircraftsMoveAction();
                // 道具移动
                propsMoveAction();
                // 撞击检测
                crashCheckAction();
                // 后处理
                postProcessAction();
                // 重绘界面
                repaint();
                // 游戏结束检查
                checkResultAction();
            }
        };
        // 以固定延迟时间进行执行：本次任务执行完成后，延迟 timeInterval 再执行下一次
        timer.schedule(task, 0, timeInterval);

    }

    // ***********************
    // Action 各部分
    // ***********************

    protected abstract void initDifficultyParams();

    protected boolean isBossEnabled() {
        return true;
    }

    protected int getBossHp(int spawnCount) {
        return 1200;
    }

    protected void increaseDifficultyIfNeeded() {
    }

    private void spawnEnemies() {
        enemySpawnCounter++;
        if (enemySpawnCounter < enemySpawnCycle) {
            return;
        }
        enemySpawnCounter = 0;
        if (enemyAircrafts.size() >= enemyMaxNumber) {
            return;
        }
        double random = Math.random();
        EnemyFactory enemyFactory;
        if (random < 0.4) {
            enemyFactory = mobEnemyFactory;
        } else if (random < 0.7) {
            enemyFactory = eliteEnemyFactory;
        } else if (random < 0.9) {
            enemyFactory = elitePlusEnemyFactory;
        } else {
            enemyFactory = eliteProEnemyFactory;
        }
        AbstractAircraft enemy = enemyFactory.createEnemy();
        enemy.setSpeed(enemy.getSpeedX(), enemy.getSpeedY() + enemySpeedBonus);
        enemyAircrafts.add(enemy);
    }

    private void spawnBossIfNeeded() {
        if (!isBossEnabled()) {
            return;
        }
        if (score < nextBossScoreThreshold) {
            return;
        }
        if (hasLivingBoss()) {
            return;
        }
        BossEnemy boss = bossEnemyFactory.createEnemy(getBossHp(bossSpawnCount));
        bossSpawnCount++;
        nextBossScoreThreshold += bossScoreThreshold;
        enemyAircrafts.add(boss);
        if (SoundManager.isEnabled()) {
            MusicManager.startBossMusic();
        }
    }

    private boolean hasLivingBoss() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            if (enemyAircraft instanceof BossEnemy && !enemyAircraft.notValid()) {
                return true;
            }
        }
        return false;
    }

    private boolean isEnemyFrozen() {
        return System.currentTimeMillis() < freezeUntilMillis;
    }

    public void freezeEnemies(long millis) {
        long until = System.currentTimeMillis() + Math.max(0, millis);
        if (until > freezeUntilMillis) {
            freezeUntilMillis = until;
        }
    }

    public void addScore(int delta) {
        score += delta;
    }

    private void shootAction() {
        shootCounter++;
        if (shootCounter >= shootCycle) {
            shootCounter = 0;
            heroBullets.addAll(heroAircraft.shoot());
        }

        if (isEnemyFrozen()) {
            return;
        }

        enemyShootCounter++;
        if (enemyShootCounter >= enemyShootCycle) {
            enemyShootCounter = 0;
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                enemyBullets.addAll(enemyAircraft.shoot());
            }
        }
    }

    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void aircraftsMoveAction() {
        if (isEnemyFrozen()) {
            return;
        }
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    private void propsMoveAction() {
        for (AbstractProp prop : props) {
            prop.forward();
        }
    }

    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        for (BaseBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }
            if (heroAircraft.crash(bullet) || bullet.crash(heroAircraft)) {
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }

        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        handleEnemyDestroyed(enemyAircraft);
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        for (AbstractProp prop : props) {
            if (prop.notValid()) {
                continue;
            }
            if (heroAircraft.crash(prop) || prop.crash(heroAircraft)) {
                // E5 英雄机获得任意补给时播放提示音效
                SoundManager.playGetSupply();
                prop.activate(heroAircraft);
                prop.vanish();
            }
        }

        if (!pendingPropsToAdd.isEmpty()) {
            props.addAll(pendingPropsToAdd);
            pendingPropsToAdd.clear();
        }
    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 删除无效的道具
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(AbstractFlyingObject::notValid);
    }

    public void onEnemyDestroyed(AbstractAircraft enemyAircraft) {
        if (enemyAircraft == null) {
            return;
        }
        handleEnemyDestroyed(enemyAircraft);
    }

    private void handleEnemyDestroyed(AbstractAircraft enemyAircraft) {
        SoundManager.playBulletHit();
        addScore(10);
        if (enemyAircraft instanceof BossEnemy) {
            int x = enemyAircraft.getLocationX();
            int y = enemyAircraft.getLocationY();
            int speedX = 0;
            int speedY = 3;
            for (int i = 0; i < 3; i++) {
                int type = (int) (Math.random() * 5);
                AbstractProp prop = PropFactory.createProp(type, x, y, speedX, speedY, supplyObservers);
                pendingPropsToAdd.add(prop);
            }
            MusicManager.stopBossMusic();
            if (SoundManager.isEnabled()) {
                MusicManager.startBackgroundMusic();
            }
            return;
        }
        double random = Math.random();
        if (random >= 0.5) {
            return;
        }
        int x = enemyAircraft.getLocationX();
        int y = enemyAircraft.getLocationY();
        int speedX = 0;
        int speedY = 3;
        int typeCount;
        if (enemyAircraft instanceof EliteEnemy) {
            typeCount = 3;
        } else if (enemyAircraft instanceof ElitePlusEnemy) {
            typeCount = 4;
        } else if (enemyAircraft instanceof EliteProEnemy) {
            typeCount = 5;
        } else {
            typeCount = 0;
        }
        if (typeCount <= 0) {
            return;
        }
        int type = (int) (Math.random() * typeCount);
        AbstractProp prop = PropFactory.createProp(type, x, y, speedX, speedY, supplyObservers);
        pendingPropsToAdd.add(prop);
    }

    // E5 检查游戏是否结束，并在结束时弹出姓名输入框并跳转到排行榜界面
    private void checkResultAction() {
        if (heroAircraft.getHp() <= 0) {
            timer.cancel();
            gameOverFlag = true;
            System.out.println("Game Over!");
            // E5 游戏结束时关闭所有背景音乐
            MusicManager.stopAll();
            if (SoundManager.isEnabled()) {
                SoundManager.playGameOver();
            }
            // E5 使用 SwingUtilities 将后续弹窗和界面切换安排到事件分发线程
            javax.swing.SwingUtilities.invokeLater(() -> {
                String playerName = javax.swing.JOptionPane.showInputDialog(
                        this,
                        "请输入玩家姓名：",
                        "记录成绩",
                        javax.swing.JOptionPane.PLAIN_MESSAGE);
                if (playerName != null && !playerName.trim().isEmpty()) {
                    scoreService.saveScore(playerName.trim(), score);
                } else {
                    scoreService.saveScore(score);
                }
                LeaderBoardPanel panel = new LeaderBoardPanel(scoreService);
                Main.MAIN_PANEL.add(panel, "LeaderBoard");
                Main.CARD_LAYOUT.show(Main.MAIN_PANEL, "LeaderBoard");
            });
        }
    }

    // ***********************
    // Paint 各部分
    // ***********************
    /**
     * 重写 paint方法
     * 通过重复调用paint方法，实现游戏动画
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 绘制背景,图片滚动
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);
        paintImageWithPositionRevised(g, enemyAircrafts);
        paintImageWithPositionRevised(g, props);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        // 绘制得分和生命值
        paintScoreAndLife(g);

    }

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.isEmpty()) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(Color.RED);
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE: " + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE: " + this.heroAircraft.getHp(), x, y);
    }

}
