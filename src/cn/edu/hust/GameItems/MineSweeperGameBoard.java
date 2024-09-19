package cn.edu.hust.GameItems;

import java.util.Random;

public class MineSweeperGameBoard {
    public char difficulty;        //游戏难度
    public int scale_x;      //棋盘大小
    public int scale_y;
    public int bombN;      //炸弹数量
    public int[][] board;        //棋盘  数字代表其附近的炸弹数量 -1表示炸弹
    public MineSweeperGameBoard(char difficulty){
        if(difficulty == '1'){
            scale_x = 9;
            scale_y = 9;
            bombN = 10;
        } else if (difficulty == '2') {
            scale_x = 16;
            scale_y = 16;
            bombN = 40;
        } else if (difficulty == '3') {
            scale_x = 22;
            scale_y = 22;
            bombN = 60;
        } else if (difficulty == '4') {
            scale_x = 34;
            scale_y = 13;
            bombN = 60;
        }
        this.difficulty = difficulty;
        initBoard();
    }

    private void initBoard(){
        board = new int[scale_y][scale_x];
        for (int i = 0; i < scale_y; i++) {
            for (int j = 0; j < scale_x; j++) {
                board[i][j]=0;
            }
        }
//        showBoard();
        int n = bombN;
        while(n>0){
            Random r = new Random();
            double ry = r.nextDouble();
            double rx = r.nextDouble();
            int y = (int)(ry*(scale_y));
            int x = (int)(rx*(scale_x));
            if(difficulty == '4'){
                if ((x == 0 && y == scale_y - 1) || (x == scale_x - 1 && y == 0)) continue;
            }
//            System.out.println("y:"+y+"  x:"+x);
            if(board[y][x] != -1) {
                board[y][x] = -1;
                n--;
                if(y>0){
                    if(board[y-1][x]!=-1){
                        board[y-1][x]=board[y-1][x]+1;
                    }
                    if(x<scale_x-1){
                        if(board[y-1][x+1]!=-1){
                            board[y-1][x+1]=board[y-1][x+1]+1;
                        }
                    }
                    if(x>0){
                        if(board[y-1][x-1]!=-1){
                            board[y-1][x-1]=board[y-1][x-1]+1;
                        }
                    }
                }
                if(y<scale_y-1){
                    if(board[y+1][x]!=-1){
                        board[y+1][x]=board[y+1][x]+1;
                    }
                    if(x<scale_x-1){
                        if(board[y+1][x+1]!=-1){
                            board[y+1][x+1]=board[y+1][x+1]+1;
                        }
                    }
                    if(x>0){
                        if(board[y+1][x-1]!=-1){
                            board[y+1][x-1]=board[y+1][x-1]+1;
                        }
                    }
                }
                if(x<scale_x-1){
                    if(board[y][x+1]!=-1){
                        board[y][x+1]=board[y][x+1]+1;
                    }
                }
                if(x>0){
                    if(board[y][x-1]!=-1){
                        board[y][x-1]=board[y][x-1]+1;
                    }
                }
            }
        }
        showBoard();
    }

    public void showBoard(){
        System.out.println("==扫雷游戏棋盘初始化：");
        for (int i = 0; i < scale_y; i++) {
            for (int j = 0; j < scale_x; j++) {
                System.out.print(board[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
