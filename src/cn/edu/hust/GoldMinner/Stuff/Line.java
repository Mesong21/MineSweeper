package cn.edu.hust.GoldMinner.Stuff;

import cn.edu.hust.GoldMinner.GMwin.GMGameWin;
import cn.edu.hust.GoldMinner.Stuff.Mineral.AllMineral;
import cn.edu.hust.GoldMinner.settings.gmConfig;

import java.awt.*;

/**
 * 抓取矿物的红线
 */
public class Line {

    /**
     * 线的终点坐标
     */
    private int endX;
    private int endY;
    /**
     * 线长
     */
    private double length = gmConfig.MIN_LINE_LENGTH;
    /**
     * 角度比例，范围是0~1
     */
    private double angle = 0;
    /**
     * 线的运动方向
     */
    private boolean direction = true; // true表示顺时针，false表示逆时针

    /**
     * 线的状态：0-摇摆，1-抓取，2-收回，3-有金块
     */
    public int state = 0;
    public boolean grab = false;

    GMGameWin gmWin;

    public Line(GMGameWin frame) {
        this.gmWin = frame;
    }
    
    public void addLength(int dlen) {
        length += dlen;
    }


    public Image hook = Toolkit.getDefaultToolkit().getImage("images/hook.png");

    /**
     * 线摇摆的状态变换
     */
    public void State_0() {
        if (angle > 0.9) {
            direction = false;
        } else if (angle < 0.1) {
            direction = true;
        }

        if (direction) {
            angle += 0.005;
        } else {
            angle -= 0.005;
        }
    }

    /**
     * 线抓取的状态变换
     */
    public void State_1() {
        length += gmConfig.D_LENGTH;
        if (endX > gmConfig.GM_GAME_WIDTH || endX < 0
                || endY > gmConfig.GM_GAME_HEIGHT)
            // 如果超出界限，即使不按鼠标也要收回
            state = 2;
    }

    /**
     * 线收回的状态变换
     */
    public void State_2() {
        length -= gmConfig.D_LENGTH;
        if (length <= gmConfig.MIN_LINE_LENGTH) {
            // 如果收回到最小长度，就变为摇摆状态
            length = gmConfig.MIN_LINE_LENGTH;
            state = 0;
        }
    }

    /**
     * 状态3 矿物跟随线返回
     */
    public void State_3(AllMineral m) {
        // State_2(); //调用状态2-线返回
        m.x = endX - m.width / 2;
        m.y = endY;
        if (length <= gmConfig.MIN_LINE_LENGTH) {
            // 收回到最小长度时矿物消失，得分增加
            m.disAppear();
            // this.gmWin.mineList.remove(m);
            this.gmWin.board.now += m.score;
            this.grab = false;
            System.out.println("得分：" + this.gmWin.board.now);
        }
        // 根据质量改变收线速度
        try {
            Thread.sleep(m.mass);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 抓取逻辑
     */
    public void grabLogic() {
        for (AllMineral m : gmWin.mineList) {
            if (m.grab) {
                State_3(m);
            }
            if (isGrab(m) && state == 1) {
                // 如果抓到了金块
                this.grab = true;
                m.grab = true;
                state = 2;
                State_3(m);
            }

        }
    }

    public boolean isGrab(AllMineral m) {
        return endX > m.x && endX < m.x + m.width && endY > m.y && endY < m.y + m.height;
    }

    /**
     * 绘制线
     */
    public void paintSelf(Graphics g) {
        // 先实现线的状态变换再paint
        grabLogic();
        switch (state) {
            case 0 -> State_0();
            case 1 -> State_1();
            case 2 -> State_2();
        }
        endX = (int) (gmConfig.START_X + length * Math.cos(angle * Math.PI));
        endY = (int) (gmConfig.START_Y + length * Math.sin(angle * Math.PI));
        g.setColor(Color.RED);
        // 增加线宽

        Graphics2D g2d = (Graphics2D) g;
        float strokeWidth = 2.0f; // 设置线条宽度为 2 像素
        g2d.setStroke(new BasicStroke(strokeWidth));
        g2d.drawLine(gmConfig.START_X, gmConfig.START_Y, endX, endY);

        // g.drawLine(Config.START_X, Config.START_Y, endX, endY);
        g.drawImage(hook, endX - 36, endY - 2, null);
    }

}
