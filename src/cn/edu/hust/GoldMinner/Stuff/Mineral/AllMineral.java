package cn.edu.hust.GoldMinner.Stuff.Mineral;

import cn.edu.hust.GoldMinner.settings.gmConfig;

import java.awt.*;

/**
 * 互动物体父类
 */
public class AllMineral {
    public int size; // 体积大小，0-small，1-medium，2-large
    public int score;
    // 质量
    public int mass;
    public int depth;
    public boolean grab = false;    //是否正在被抓取
    public boolean have = false;    //是否已经抓取完毕
    public int x;
    public int y;
    public int width;
    public int height;
    public Image img;

    public AllMineral() {
        score = 0;
        depth = gmConfig.SKY_HEIGHT + gmConfig.MIN_LINE_LENGTH;
    }

    public void paintSelf(Graphics g) {
        g.drawImage(img, x, y, null);
    }

    /**
     * 获取矿物的矩形
     * 
     * @return 矩形
     */
    public Rectangle getRect() {
        return new Rectangle(x, y, width, height);
    }

    public void disAppear() {
        x = -200;
        y = -200;
        grab = false;  
        have = true;
    }

}
