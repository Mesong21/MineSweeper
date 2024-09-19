package cn.edu.hust.GoldMinner.Stuff.Mineral;

import cn.edu.hust.GoldMinner.settings.gmConfig;

import java.awt.*;

public class Rock extends AllMineral {
    public Rock(int size) {
        super();
        this.size = size;
        switch (this.size) {
            case 0 -> {
                this.width = 72;
                this.height = 72;
                this.mass = 10;
                this.score = 4;

                this.y = (int) (this.depth + Math.random() * (gmConfig.MINERAL_DEPTH - this.depth - this.height));
                // System.out.println(this.size + " " + this.y);
                this.img = Toolkit.getDefaultToolkit().getImage("images\\SmallRock.png");
                // System.out.println(img.getWidth(null));
            }
            case 1 -> {
                this.width = 105;
                this.height = 105;
                this.mass = 50;
                this.score = 16;
                this.depth += 70;

                this.y = (int) (this.depth + Math.random() * (gmConfig.MINERAL_DEPTH - this.depth - this.height));
                // System.out.println(this.size + " " + this.y);
                this.img = Toolkit.getDefaultToolkit().getImage("images\\MediumRock.png");
            }
            case 2 -> {
                this.width = 175;
                this.height = 175;
                this.mass = 75;
                this.score = 64;

                this.depth += 175;
                this.y = (int) (this.depth + Math.random() * (gmConfig.MINERAL_DEPTH - this.height - this.height));
                // System.out.println(this.size + " " + this.y);
                this.img = Toolkit.getDefaultToolkit().getImage("images\\LargeRock.png");
            }
        }
        this.x = (int) (Math.random() * (gmConfig.GM_GAME_WIDTH - this.width));
    }

    public void paintSelf(Graphics g) {
        super.paintSelf(g);
    }
}
