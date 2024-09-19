package cn.edu.hust.GameItems;

public class Miner {
    public Miner(int y, int x){
        pos_y = y;
        pos_x = x;
    }
    public int pos_x;
    public int pos_y;
    public int health = 5;
    public int score = 0;
    public int ult = 3;  //能放3次大招
}
