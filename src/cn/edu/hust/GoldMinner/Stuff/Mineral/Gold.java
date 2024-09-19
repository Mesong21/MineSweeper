package cn.edu.hust.GoldMinner.Stuff.Mineral;

import cn.edu.hust.GoldMinner.settings.gmConfig;

import java.awt.*;

/**
 * 金块
 */
public class Gold extends AllMineral {

    public Gold(int size) {
        super();
        this.size = size;
        switch (this.size) {
            case 0 -> {
                this.width = 36;
                this.height = 36;
                this.mass = 10;
                this.score = 32;
                // 小金块的深度随意
                this.y = (int) (this.depth + Math.random() * (gmConfig.MINERAL_DEPTH - this.depth - this.height));
                // System.out.println(this.size + " " + this.y);
                this.img = Toolkit.getDefaultToolkit().getImage("images\\SmallGold.gif");

            }
            case 1 -> {
                this.width = 105;
                this.height = 105;
                this.mass = 50;
                this.score = 64;
                // 中金块在深处
                this.depth += 70;
                this.y = (int) (this.depth + Math.random() * (gmConfig.MINERAL_DEPTH - this.depth - this.height));
                // System.out.println(this.size + " " + this.y);
                this.img = Toolkit.getDefaultToolkit().getImage("images\\MediumGold.gif");
            }
            case 2 -> {
                this.width = 175;
                this.height = 175;
                this.mass = 75;
                this.score = 256;
                // 大金块在最深处
                this.depth += 400;
                this.y = (int) (this.depth + Math.random() * (gmConfig.MINERAL_DEPTH - this.depth - this.height));
                // System.out.println(this.size + " " + this.y);
                this.img = Toolkit.getDefaultToolkit().getImage("images\\LargeGold.gif");
            }
        }
        this.x = (int) (Math.random() * (gmConfig.GM_GAME_WIDTH - this.width));
    }

    public void paintSelf(Graphics g) {
        super.paintSelf(g);
    }
}
