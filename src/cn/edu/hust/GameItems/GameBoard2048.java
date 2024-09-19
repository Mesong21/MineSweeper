package cn.edu.hust.GameItems;

import java.util.ArrayList;
import java.util.Random;

public class GameBoard2048 {
    public int[][] board;      //记录棋盘上的方块

    public GameBoard2048() {
        board = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                board[i][j] = 0;      //代表为空槽
            }
        }
    }

    public boolean isFull() {
        //判断棋盘是否已经满了
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void spawnBlock() {
        Random r = new Random();
        ArrayList<int[]> okBlocks = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] == 0) {
                    int[] axis = new int[2];
                    axis[0] = i;
                    axis[1] = j;
                    okBlocks.add(axis);
                }
            }
        }
        int okNum = okBlocks.size();
        double dr = r.nextDouble();
        int spawnAxisN = (int) (dr * okNum);
        board[okBlocks.get(spawnAxisN)[0]][okBlocks.get(spawnAxisN)[1]] = 2;
    }

    public void showBoard() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int leftMove() {
        int moveScore=0;
        leftMoveWithoutMerge();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == board[i][j + 1] && board[i][j] != 0) {
                    moveScore+=board[i][j];
                    board[i][j] = board[i][j] + board[i][j + 1];
                    board[i][j + 1] = 0;
                }
            }
        }
        leftMoveWithoutMerge();
        return moveScore;
    }

    private void leftMoveWithoutMerge() {
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
                int l = j - 1;
                while (true) {
                    if (l == -1) {
                        break;
                    }
                    if (board[i][l] != 0) {
                        break;
                    }
                    l--;
                }
                if (l != j - 1) {
                    board[i][l + 1] = board[i][j];
                    board[i][j] = 0;
                }

            }
        }
    }

    public int rightMove() {
        int moveScore=0;
        rightMoveWithoutMerge();
        for (int i = 0; i < 4; i++) {
            for (int j = 3; j >0; j--) {
                if (board[i][j] == board[i][j - 1] && board[i][j] != 0) {
                    moveScore+=board[i][j];
                    board[i][j] = board[i][j] + board[i][j - 1];
                    board[i][j -1] = 0;
                }
            }
        }
        rightMoveWithoutMerge();
        return moveScore;
    }

    private void rightMoveWithoutMerge() {
        for (int i = 0; i < 4; i++) {
            for (int j = 2; j >=0; j--) {
                int l = j+1;
                while(true){
                    if(l == 4){
                        break;
                    }
                    if(board[i][l]!=0){
                        break;
                    }
                    l++;
                }
                if(l!=j+1){
                    board[i][l-1] = board[i][j];
                    board[i][j]=0;
                }
            }

        }
    }

    public int upMove() {
        int moveScore=0;
        upMoveWithoutMerge();
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 3; i++) {
                if(board[i][j] == board[i+1][j] && board[i][j]!=0){
                    moveScore+=board[i][j];
                    board[i][j] = board[i][j] + board[i+1][j];
                    board[i+1][j]=0;
                }
            }
        }
        upMoveWithoutMerge();
        return moveScore;
    }

    private void upMoveWithoutMerge() {
        for (int j = 0; j < 4; j++) {
            for (int i = 1; i < 4; i++) {
                int l = i-1;
                while(true){
                    if(l==-1){
                        break;
                    }
                    if(board[l][j]!=0){
                        break;
                    }
                    l--;
                }
                if(l!=i-1){
                    board[l+1][j] = board[i][j];
                    board[i][j]=0;
                }
            }
        }
    }



    public int downMove() {
        int moveScore=0;
        downMoveWithoutMerge();
        for (int j = 0; j < 4; j++) {
            for (int i = 3; i > 0; i--) {
                if(board[i][j] == board[i-1][j] && board[i][j]!=0){
                    board[i][j] = board[i][j] + board[i-1][j];
                    moveScore+=board[i][j];
                    board[i-1][j]=0;
                }
            }
        }
        downMoveWithoutMerge();
        return moveScore;
    }

    private void downMoveWithoutMerge() {
        for (int j = 0; j < 4; j++) {
            for (int i = 2; i >= 0; i--) {
                int l = i+1;
                while(true){
                    if(l==4){
                        break;
                    }
                    if(board[l][j]!=0){
                        break;
                    }
                    l++;
                }
                if(l!=i+1){
                    board[l-1][j] = board[i][j];
                    board[i][j]=0;
                }
            }
        }
    }
}
