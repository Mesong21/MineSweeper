package cn.edu.hust.GoldMinner.GMwin;


import cn.edu.hust.GoldMinner.settings.gmConfig;

import java.awt.*;

/**
 * 游戏背景
 * 包括矿工、天空、地面
 */
public class BackGround {

    Image bg = Toolkit.getDefaultToolkit().getImage("images/bg03.jpg"); // 背景图片
    Image sky = Toolkit.getDefaultToolkit().getImage("images/Sky01.jpg"); 
    Image person = Toolkit.getDefaultToolkit().getImage("images/person01.png");

    /**
        绘制背景图片
     */
    void paintSelf(Graphics g, GMGameWin gameWin) {
        g.drawImage(sky, 0, 0, gameWin.getWidth(), gmConfig.SKY_HEIGHT, null);    // 绘制天空
        g.drawImage(bg, 0, gmConfig.SKY_HEIGHT, gameWin.getWidth(), gameWin.getHeight() - gmConfig.SKY_HEIGHT, null);   // 绘制地面
        g.drawImage(person, gmConfig.GM_GAME_WIDTH / 2 - 70, 50, null); // 绘制人物
    }
}
