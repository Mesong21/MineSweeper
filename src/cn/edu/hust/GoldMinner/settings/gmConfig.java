package cn.edu.hust.GoldMinner.settings;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * 保存游戏界面设置，比如天空和地面的占比像素
 */
public class gmConfig {
    public static int LEFT_EDGE = 50;


    /**
     * 关卡机制
     */
    public static int GAME_LEVEL = 7;   // 关卡数
    public static int LEVEL_TIME = 40;  // 每关初始秒数
    public static int MINERAL_NUM = 10; // 每关矿物数
    public static int MIN_MINERAL_NUM = 5; // 矿物最小数目限制
    /**
     * 奖励机制
     */
    public static int PAUSE_CHANCE = 2; // 暂停初始次数
    public static int POWER_CHANCE = 2; // 力量初始次数
    public static int BOMB_CHANCE = 2;  // 炸弹初始次数
    public static int FRESH_CHANCE = 1; // 刷新初始次数

    public static int PAUSE_REWARD = 16; // 暂停奖励触发分数
    public static int POWER_REWARD = 64; // 力量奖励触发分数
    public static int BOMB_REWARD = 32; // 炸弹奖励触发分数
    public static int LUCK_REWARD = 512; // 运气奖励触发分数

    public static int PAUSE_KEY = MouseEvent.BUTTON3; // 暂停快捷键
    public static int BOMB_KEY = KeyEvent.VK_E; // 炸弹快捷键
    public static int POWER_KEY = KeyEvent.VK_Q; // 力量快捷键
    public static int LUCK_KEY = KeyEvent.VK_X; // 运气快捷键x
    

    public static int PAUSE_MAX_TIME = 500000;   // 暂停时间
    /**
     * 游戏窗口大小
     */
    public static int GM_GAME_WIDTH = 900;
    public static int GM_GAME_HEIGHT = 900;

    public static int GAME_WIDTH_2048 = 400;
    // public static int GAME_HEIGHT_2048 = 400;
    /**
     * 天空高度占比像素
     */
    public static int SKY_HEIGHT = 200;

    /**
     * 线的初始坐标
     */
    public static int START_X = gmConfig.GM_GAME_WIDTH / 2;
    public static int START_Y = 180;

    /**
     * 最短线长
     */
    public static int MIN_LINE_LENGTH = 100;
    /**
     * 线的变化程度
     */
    public static int D_LENGTH = 10;

    /**
     * 矿物深度占比
     */
    public static int MINERAL_DEPTH = GM_GAME_HEIGHT - SKY_HEIGHT - MIN_LINE_LENGTH;   // 600

    public static Color TXT_COLOR = new Color(0x00, 0xff, 0x00);
}
