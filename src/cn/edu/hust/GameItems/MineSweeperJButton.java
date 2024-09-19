package cn.edu.hust.GameItems;

import javax.swing.*;

public class MineSweeperJButton extends JButton {
    public int y;
    public int x;
    public int is_highlight = 0;
//    public int status;
    public MineSweeperJButton(int y,int x){
        this.y = y;
        this.x = x;
    }
    public int get_x(){
        return x;
    }
    public int get_y(){
        return y;
    }
}
