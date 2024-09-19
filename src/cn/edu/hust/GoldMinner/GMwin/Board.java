package cn.edu.hust.GoldMinner.GMwin;

import cn.edu.hust.GoldMinner.settings.gmConfig;

import java.awt.*;

/**
 * 要显示在背景之上的面板
 */
public class Board {
    public int now;
    public int goal;
    public int level;
    public long startTime; // 开始时间
    public long endTime; // 结束时间
    public int t;
    public int tpass;
    public long pauseTime; // 开始暂停时的系统时间
    public static int dt; // 暂停期间多计时的时间
    public static int basedt; // 之前若有过暂停，已经累计的时间
    public GMGameWin gmWin;

    public Board() {
        t = gmConfig.LEVEL_TIME;
        now = 0;
        this.level = GMGameWin.level;
        goal = 0x10 << level;
        startTime = System.currentTimeMillis();
        tpass = 0;
        basedt = 0;
        dt = 0;
    }

    /**
     * 传入关卡数的初始化
     *
     */
    public Board(GMGameWin gmWin) {
        t = gmConfig.LEVEL_TIME;
        now = 0;
        this.level = GMGameWin.level;
        goal = 0x10 << level;
        this.gmWin = gmWin;
        startTime = System.currentTimeMillis();
        tpass = 0;
        Board.basedt = 0;
        Board.dt = 0;
    }

    /**
     * 绘制面板，同时得到新的剩余时间
     */
    public void paintSelf(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("微软雅黑", Font.BOLD, 14));
        g.drawString("关卡: " + this.level + "/" + gmConfig.GAME_LEVEL, 20, 50); // 关卡数
        g.drawString("目标: " + goal, 20, 65); // 目标分数
        g.drawString("得分: " + now, 20, 80); // 现在分数
        this.gmWin.rwd.showReward(g);

        if (gmWin.state == 1) {
            t = setT();
            g.drawString("倒计时: " + t, 700, 50); // 剩余时间
            g.drawString("单击左键发射勾爪, 钩爪可以自动回收", 300, 50);
        } else if (gmWin.state == 2) { // 暂停
            int leftPause = gmConfig.PAUSE_MAX_TIME - (int) ((System.currentTimeMillis() - pauseTime) / 1000); // 暂停剩余时间
            dt = basedt + (int) (System.currentTimeMillis() - pauseTime);
            if (leftPause <= 0) {
                gmWin.state = 1;
                basedt = dt;
            }
            g.drawString("倒计时: " + t, 500, 50); // 剩余时间
            g.drawString(leftPause + " 秒后继续游戏", 500, 65);
        }

    }

    /**
     * 加减分数
     */
    public void add(int score) {
        this.now += score;
    }

    /**
     * 设置倒计时
     */
    public int setT() {
        endTime = System.currentTimeMillis();
        tpass = (int) ((endTime - startTime - dt) / 1000); // 已经消耗的时间=当前时间-开始时间-暂停期间的时间
        t = gmConfig.LEVEL_TIME - tpass;
        return t;
    }
}
