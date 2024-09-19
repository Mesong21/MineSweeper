package cn.edu.hust.GoldMinner.Stuff;

import cn.edu.hust.GoldMinner.GMwin.GMGameWin;
import cn.edu.hust.GoldMinner.Stuff.Mineral.AllMineral;
import cn.edu.hust.GoldMinner.Stuff.Mineral.Gold;
import cn.edu.hust.GoldMinner.settings.gmConfig;

import javax.swing.*;
import java.awt.*;

public class Reward {
    public int pause;
    public int power;
    public int bomb;
    public int luck;
    public GMGameWin gmWin;

    public Reward() {
        pause = gmConfig.PAUSE_CHANCE;
        power = gmConfig.POWER_CHANCE;
        bomb = gmConfig.BOMB_CHANCE;
        luck = gmConfig.FRESH_CHANCE;

    }

    public Reward(GMGameWin gmWin) {
        pause = gmConfig.PAUSE_CHANCE;
        power = gmConfig.POWER_CHANCE;
        bomb = gmConfig.BOMB_CHANCE;
        luck = gmConfig.FRESH_CHANCE;
        this.gmWin = gmWin;
    }

    /**
     * 显示道具
     */
    public void showReward(Graphics g) {
        g.drawString("单击右键以暂停: " + pause, 600, 95); // 剩余暂停次数
        g.drawString("按下 " + (char) gmConfig.POWER_KEY + " 使用超级力量. 剩余次数: " + power, 600, 110); // 剩余力量次数
        g.drawString("按下 " + (char) gmConfig.BOMB_KEY + " 使用炸弹, 剩余次数: " + bomb, 600, 125); // 剩余炸弹次数
        g.drawString("按下 " + (char) gmConfig.LUCK_KEY + " 使用运气, 剩余次数: " + luck, 600, 140);
    }

    /**
     * 获取奖励
     */
    public void getPause() {
        JOptionPane.showMessageDialog(null, "恭喜你获得了1次暂停机会", "提示", JOptionPane.WARNING_MESSAGE);
        pause++;
    }

    public void getPower() {
        JOptionPane.showMessageDialog(null, "恭喜你获得了1次超级力量", "提示", JOptionPane.WARNING_MESSAGE);
        power++;
    }

    public void getBomb() {
        JOptionPane.showMessageDialog(null, "恭喜你获得了1枚炸弹", "提示", JOptionPane.WARNING_MESSAGE);
        bomb++;
    }

    public void getLuck() {
        JOptionPane.showMessageDialog(null, "恭喜你获得了1次运气", "提示", JOptionPane.WARNING_MESSAGE);
        luck++;
    }

    /**
     * 使用奖励
     */
    public void usePause() {
        if (pause > 0) {
            pause--;
            gmWin.state = 2;
            gmWin.board.pauseTime = System.currentTimeMillis();
        } else {
            JOptionPane.showMessageDialog(null, "暂停机会已用完！", "提示", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void usePower() {
        if (power > 0) {
            power--;
            gmWin.line.addLength(-200);
            // line.power += 10;
        } else {
            JOptionPane.showMessageDialog(null, "超级力量已用完！", "提示", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void useBomb() {
        if (bomb > 0) {
            bomb--;
            for (AllMineral m : gmWin.mineList) {
                if (m.grab) {
                    m.disAppear();
                    // gmWin.mineList.remove(m);
                    gmWin.line.state = 2;
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "炸弹已用完！", "提示", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void useLuck() {
        if (luck > 0) {
            luck--;
            gmWin.mineList.clear();
            int i = 5;
            while (i-- != 0) {
                boolean isPlace = true;
                AllMineral m;
                m = new Gold(2);

                for (AllMineral med : gmWin.mineList) {
                    if (m.getRect().intersects(med.getRect())) {
                        // 不能放置
                        isPlace = false;
                        break;
                    }
                }
                if (isPlace) {
                    gmWin.mineList.add(m);
                } else {
                    i++; // 重新生成
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "运气已用完！", "提示", JOptionPane.WARNING_MESSAGE);
        }
    }
}
