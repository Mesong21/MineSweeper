package cn.edu.hust.ui;

import cn.edu.hust.GameItems.GameBoard2048;
import cn.edu.hust.GameItems.JButton2048;
import cn.edu.hust.GoldMinner.GMwin.GMGameWin;
import cn.edu.hust.GoldMinner.settings.gmConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;

public class Game2048JFrame extends JFrame implements ActionListener, KeyListener {

    boolean only2048 = true;
    int score; // 得分
    JMenuItem exitGameItem = new JMenuItem("回到大厅");
    JMenuItem replayItem = new JMenuItem("重新开始");
    GameBoard2048 gameBoard2048;
    ArrayList<ArrayList<JButton2048>> buttonList; // 按钮集合
    public GMGameWin gmWin;
    int max;

    public Game2048JFrame() {
        initFrame();
        initJMenuBar();
        initGameBoard();
        this.setVisible(true);
        this.requestFocusInWindow(); // 重新聚焦，使KeyListener生效
        this.score = 0;
        this.gmWin = null;
        only2048 = true;
        max = 0;
    }

    public Game2048JFrame(GMGameWin gmWin) {
        initFrame();
        initJMenuBar();
        initGameBoard();
        this.setVisible(true);
        this.requestFocusInWindow(); // 重新聚焦，使KeyListener生效
        this.score = 0;
        this.gmWin = gmWin;
        only2048 = false;

    }

    private void initFrame() { // 初始化JFrame
        this.setSize(420, 450);
        this.setTitle("2048");
        this.setAlwaysOnTop(true);
        this.setLocation(gmConfig.LEFT_EDGE + gmConfig.GM_GAME_WIDTH, 0);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.addKeyListener(this);
    }

    private void initJMenuBar() {
        // 菜单初始化
        JMenuBar jMenuBar = new JMenuBar();
        JMenu menu = new JMenu("菜单");
        exitGameItem.addActionListener(this);
        replayItem.addActionListener(this);
        menu.add(exitGameItem);
        menu.add(replayItem);
        jMenuBar.add(menu);
        this.setJMenuBar(jMenuBar);
    }

    public void initGameBoard() {
        gameBoard2048 = new GameBoard2048();
        gameBoard2048.spawnBlock();
        buttonList = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            ArrayList<JButton2048> buttonRow = new ArrayList<>(4);
            for (int j = 0; j < 4; j++) {
                JButton2048 button = new JButton2048(i, j);
                if (gameBoard2048.board[i][j] != 0) {
                    button.setText(String.valueOf(gameBoard2048.board[i][j]));
                }
                button.setBounds(j * 100, i * 100 + 20, 100, 100);
                button.setBackground(Color.white);
                button.setAlignmentX(Component.CENTER_ALIGNMENT);
                button.setBorder(BorderFactory.createRaisedBevelBorder());
                button.addActionListener(this);
                this.getLayeredPane().add(button, JLayeredPane.PALETTE_LAYER);
                buttonRow.add(button);
            }
            buttonList.add(buttonRow);
        }
    }

    public void refreshButtons() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (gameBoard2048.board[i][j] == 0) {
                    buttonList.get(i).get(j).setText(" ");
                } else {
                    buttonList.get(i).get(j).setText(String.valueOf(gameBoard2048.board[i][j]));
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == exitGameItem) { // 退出游戏
            if (only2048)
                this.dispose();
            else
                this.gmWin.dispose();
            try {
                LobbyJFrame lobbyJFrame = new LobbyJFrame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (source == replayItem) { // 重新开始
            if (only2048) {
                this.dispose();
                Game2048JFrame game2048JFrame = new Game2048JFrame();
            }
            else {
                this.gmWin.dispose();
                GMGameWin gmGameWin = new GMGameWin();
                new Thread(() -> gmGameWin.launch()).start();
            }
        } else {
            System.out.println("don't touch me");
            this.requestFocusInWindow(); // 重新聚焦
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (only2048 == true) {
            KeyReleasedHandler(e);
        } else {
            // 与黄金矿工一起运行
            if (this.getState() == 2) {
                KeyReleasedHandler(e);
            } else {
                // 提示
                JOptionPane.showMessageDialog(null, "正在采矿! ", "提示", JOptionPane.WARNING_MESSAGE);
            }
        }

        if (gameBoard2048.isFull()) { // 游戏结束
            System.out.println("fail");
            GameOver2048JFrame gameOver2048JFrame = new GameOver2048JFrame(this, score);
        } else {
            gameBoard2048.spawnBlock();
            this.refreshButtons();

        }
        gameBoard2048.showBoard(); // 打印当前棋盘

        System.out.println("score: " + score);
        if (only2048 == false)
            Reward(); // 获得奖励
        StringBuilder sb = new StringBuilder("score: ");
        sb.append(score);
        this.setTitle(sb.toString());
    }

    public void Reward() {
        for (int[] ints : gameBoard2048.board) {
            for (int anInt : ints) {
                if (anInt > max) {
                    max = anInt;
                }
            }
        }
        if (max == gmConfig.PAUSE_REWARD) {
            gmWin.rwd.getPause();
            max += 1;
        } else if (max == gmConfig.POWER_REWARD) {
            gmWin.rwd.getPower();
            max += 1;
        } else if (max == gmConfig.BOMB_REWARD) {
            gmWin.rwd.getBomb();
            max += 1;
        } else if (max == gmConfig.LUCK_REWARD) {
            gmWin.rwd.getLuck();
            max += 1;
        }
    }

    public int getState() {
        if (gmWin == null)
            return -1;
        return gmWin.state;
    }

    public int getScore() {
        return score;
    }

    public void KeyReleasedHandler(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == 37 || code == 65) {// left
            System.out.println("left");
            int moveScore = gameBoard2048.leftMove();
            score += moveScore;
            this.refreshButtons();
        } else if (code == 38 || code == 87) {// up
            System.out.println("up");
            int moveScore = gameBoard2048.upMove();
            score += moveScore;
            this.refreshButtons();
        } else if (code == 39 || code == 68) {// right
            System.out.println("right");
            int moveScore = gameBoard2048.rightMove();
            score += moveScore;
            this.refreshButtons();
        } else if (code == 40 || code == 83) {// down
            System.out.println("down");
            int moveScore = gameBoard2048.downMove();
            score += moveScore;
            this.refreshButtons();
        }
    }
}
