package cn.edu.hust.ui;

import cn.edu.hust.FileUtils.RWCache;
import cn.edu.hust.GameItems.MineSweeperGameBoard;
import cn.edu.hust.GameItems.MineSweeperJButton;
import cn.edu.hust.GameItems.Miner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

public class MineSweeperJFrame extends JFrame implements ActionListener, MouseListener, KeyListener {

    JMenu changeDifficulty = new JMenu("难度");
    JMenuItem exitGameItem = new JMenuItem("回到大厅");
    JMenuItem easyItem = new JMenuItem("简单");
    JMenuItem mediumItem = new JMenuItem("普通");
    JMenuItem hardItem = new JMenuItem("困难");
    JMenuItem other = new JMenuItem("进阶");
    char dif;         //难度
    int scale_x;      //棋盘大小
    int scale_y;
    ArrayList<ArrayList<MineSweeperJButton>> buttonList;        //按钮集合
    int[][] board;      //棋盘
    int[][] boardStatus;        //棋盘按钮是否可按 0是可按 1是不可按 2是插旗子
    int clearButtonN = 0;       //记录累计点开的按钮数
    int bombN;      //记录本棋盘的炸弹数
    int buttonN;        //按钮总数
    int step;           //步数
    int flags;           //旗子数
    int rightF;          //正确旗子数
    int score;           //得分
    ImageIcon norm = new ImageIcon("src/Resource/icons/Mine_norm.png");
    ImageIcon onclick = new ImageIcon("src/Resource/icons/Mine_onclick.png");
    ImageIcon empty = new ImageIcon("src/Resource/icons/Mine_empty.png");
    ImageIcon boom = new ImageIcon("src/Resource/icons/Mine_boom.png");
    ImageIcon flag = new ImageIcon("src/Resource/icons/Mine_flag.png");
    ImageIcon highlight = new ImageIcon("src/Resource/icons/Mine_onclick.png");
    ImageIcon _1 = new ImageIcon("src/Resource/icons/Mine_1.png");
    ImageIcon _2 = new ImageIcon("src/Resource/icons/Mine_2.png");
    ImageIcon _3 = new ImageIcon("src/Resource/icons/Mine_3.png");
    ImageIcon _4 = new ImageIcon("src/Resource/icons/Mine_4.png");
    ImageIcon miner_img = new ImageIcon("src/Resource/icons/miner.png");
    Miner person;
    Font font = new Font("仿宋", Font.PLAIN, 24);  //字体的样式
    JLabel BombCount;
    JLabel ShowStep;
    JLabel FlagCount;
    JLabel PersonHealth;
    JLabel msg;  //当前踩住的格子
    JLabel ult;  //大招次数

    public MineSweeperJFrame() throws IOException {
        initJFrame();
        initJMenuBar();
        initGameBoard();
        this.setVisible(true);
    }

    private void Refresh() {
        BombCount.setText("炸弹数：" + bombN);
        ShowStep.setText("步数：" + step);
        FlagCount.setText("棋子数：" + flags);
        if(dif == '4'){
            PersonHealth.setText("血量：" + person.health);
            msg.setText("当前：" + board[person.pos_y][person.pos_x]);
            ;
            ult.setText("大招次数：" + person.ult);
            buttonList.get(person.pos_y).get(person.pos_x).setIcon(miner_img);
        }

    }

    private void RefreshBoard(int y, int x) {
        switch (board[y][x]) {
            case -1:
                buttonList.get(y).get(x).setIcon(boom);
                break;
            case 0:
                buttonList.get(y).get(x).setIcon(empty);
                break;
            case 1:
                buttonList.get(y).get(x).setIcon(_1);
                break;
            case 2:
                buttonList.get(y).get(x).setIcon(_2);
                break;
            case 3:
                buttonList.get(y).get(x).setIcon(_3);
                break;
            case 4:
                buttonList.get(y).get(x).setIcon(_4);
                break;
        }
        boardStatus[y][x] = 1;
    }

    private void initJFrame() {       //初始化JFrame
        this.step = 0;
        this.flags = 0;
        this.rightF = 0;
        this.score = 0;
        this.setSize(1920, 1080);
        this.setTitle("扫雷");
//        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setFocusable(true);

    }

    private void initJMenuBar() {
        //菜单初始化
        JMenuBar jMenuBar = new JMenuBar();

        JMenu menu = new JMenu("菜单");


        exitGameItem.addActionListener(this);
        easyItem.addActionListener(this);
        mediumItem.addActionListener(this);
        hardItem.addActionListener(this);
        other.addActionListener(this);

        menu.add(changeDifficulty);
        menu.add(exitGameItem);
        changeDifficulty.add(easyItem);
        changeDifficulty.add(mediumItem);
        changeDifficulty.add(hardItem);
        changeDifficulty.add(other);

        jMenuBar.add(menu);

        this.setJMenuBar(jMenuBar);
    }

    private void initGameBoard() throws IOException {
        dif = RWCache.readFromCache("cache_difficulty");
        MineSweeperGameBoard gameBoard = new MineSweeperGameBoard(dif);
        scale_x = gameBoard.scale_x;        //棋盘大小
        scale_y = gameBoard.scale_y;
        board = gameBoard.board;
        bombN = gameBoard.bombN;
        boardStatus = new int[scale_y][scale_x];
        buttonN = scale_x * scale_y;      //按钮数量
        for (int i = 0; i < scale_y; i++) {
            for (int j = 0; j < scale_x; j++) {
                boardStatus[i][j] = 0;        //0代表可左键点击（icon为norm），1代表左键点击完毕，2代表右键标记
            }
        }
        int size = 0;
        if (dif == '1') size = 90;
        else if (dif == '2') size = 75;
        else size = 60;

        initImage(norm, size);
        initImage(onclick, size);
        initImage(empty, size);
        initImage(boom, size);
        initImage(flag, size);
        initImage(highlight, size);
        initImage(miner_img, size);
        initImage(_1, size);
        initImage(_2, size);
        initImage(_3, size);
        initImage(_4, size);

        buttonList = new ArrayList<>(scale_y);
        for (int i = 0; i < scale_y; i++) {
            ArrayList<MineSweeperJButton> buttonRow = new ArrayList<>(scale_x);
            for (int j = 0; j < scale_x; j++) {
                MineSweeperJButton button = new MineSweeperJButton(i, j);
                if (dif == '1') {
                    button.setBounds(600 + 80 * j, 180 + 80 * i, 80, 80);
                } else if (dif == '2') {
                    button.setBounds(480 + 60 * j, 60 + 60 * i, 60, 60);
                } else if (dif == '3') {
                    button.setBounds(460 + 45 * j, 33 + 45 * i, 45, 45);
                } else if (dif == '4') {
                    button.setBounds(250 + 45 * j, 150 + 45 * i, 45, 45);
                }
//                button.setBackground(Color.white);
                button.setIcon(norm);
                button.setAlignmentX(Component.CENTER_ALIGNMENT);
//                button.addActionListener(this);
                button.addMouseListener(this);
                button.setBorder(BorderFactory.createRaisedBevelBorder());

                this.getLayeredPane().add(button, JLayeredPane.PALETTE_LAYER);
                buttonRow.add(button);
            }
            buttonList.add(buttonRow);
        }

        BombCount = new JLabel("炸弹数：" + bombN);
        BombCount.setBounds(50, 50, 150, 30);
        BombCount.setFont(font);
        this.getLayeredPane().add(BombCount);

        ShowStep = new JLabel("步数：" + step);
        ShowStep.setBounds(50, 100, 150, 30);
        ShowStep.setFont(font);
        this.getLayeredPane().add(ShowStep);

        FlagCount = new JLabel("棋子数：" + flags);
        FlagCount.setBounds(50, 150, 150, 30);
        FlagCount.setFont(font);
        this.getLayeredPane().add(FlagCount);

        if (dif == '4') {
            this.addKeyListener(this);
            person = new Miner(scale_y - 1, 0);
            PersonHealth = new JLabel("血量：" + person.health);
            PersonHealth.setBounds(50, 200, 150, 30);
            PersonHealth.setFont(font);
            this.getLayeredPane().add(PersonHealth);

            msg = new JLabel("当前：" + board[person.pos_y][person.pos_x]);
            msg.setBounds(50, 250, 300, 30);
            msg.setFont(font);
            this.getLayeredPane().add(msg);

            ult = new JLabel("大招次数：" + person.ult);
            ult.setBounds(50, 300, 300, 30);
            ult.setFont(font);
            this.getLayeredPane().add(ult);

            JLabel msg1 = new JLabel("指挥矿工走到右上角");
            msg1.setBounds(20, 600, 300, 30);
            msg1.setFont(font);
            this.getLayeredPane().add(msg1);
            JLabel msg2 = new JLabel("左键：排雷 ");
            msg2.setBounds(20, 650, 300, 30);
            msg2.setFont(font);
            this.getLayeredPane().add(msg2);
            JLabel msg3 = new JLabel("右键：插旗");
            msg3.setBounds(20, 700, 300, 30);
            msg3.setFont(font);
            this.getLayeredPane().add(msg3);
            JLabel msg4 = new JLabel("空格：大招");
            msg4.setBounds(20, 750, 300, 30);
            msg4.setFont(font);
            this.getLayeredPane().add(msg4);
            Refresh();
        }
    }

    private void initImage(ImageIcon img, int size) {
        img.setImage(img.getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == easyItem) {
            System.out.println("easy");
            try {
                RWCache.writeIntoCache('1', "cache_difficulty");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            this.dispose();
            try {
                MineSweeperJFrame mineSweeperJFrame = new MineSweeperJFrame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        } else if (source == mediumItem) {
            System.out.println("medium");
            try {
                RWCache.writeIntoCache('2', "cache_difficulty");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            this.dispose();
            try {
                MineSweeperJFrame mineSweeperJFrame = new MineSweeperJFrame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        } else if (source == hardItem) {
            System.out.println("hard");
            try {
                RWCache.writeIntoCache('3', "cache_difficulty");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            this.dispose();
            try {
                MineSweeperJFrame mineSweeperJFrame = new MineSweeperJFrame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        } else if (source == other) {
            try {
                RWCache.writeIntoCache('4', "cache_difficulty");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            this.dispose();
            try {
                MineSweeperJFrame mineSweeperJFrame = new MineSweeperJFrame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        } else if (source == exitGameItem) {
            System.out.println("退出扫雷游戏");
            this.dispose();
            try {
                LobbyJFrame lobbyJFrame = new LobbyJFrame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void Ult(int y, int x) {
        if (y < 0 || y > scale_y - 1 || x < 0 || x > scale_x - 1 || boardStatus[y][x] != 0) {
            return;
        }
        RefreshBoard(y, x);
        if (board[y][x] == 0) {
            LeftOnClickAButton(y - 1, x - 1);
            LeftOnClickAButton(y - 1, x);
            LeftOnClickAButton(y - 1, x + 1);
            LeftOnClickAButton(y, x - 1);
            LeftOnClickAButton(y + 1, x + 1);
            LeftOnClickAButton(y + 1, x - 1);
            LeftOnClickAButton(y + 1, x);
            LeftOnClickAButton(y + 1, x + 1);
        }
    }

    public void LeftOnClickAButton(int y, int x) {       //当点击棋盘某按钮后的事件
//        if (dif == '4' && y == person.pos_y && x == person.pos_x) return;
        if (y < 0 || y > scale_y - 1 || x < 0 || x > scale_x - 1 || boardStatus[y][x] != 0) {
            return;
        }
        boardStatus[y][x] = 1;
        RefreshBoard(y, x);
        if (board[y][x] == -1) {  //地雷
            if (dif != '4') {
                System.out.println("游戏失败");
                this.dispose();
                MSGameOverJFrame gameOverJFrame = new MSGameOverJFrame();
            } else {
                person.health--;
                TestVictory();
                Refresh();
                RefreshBoard(y, x);
            }

        } else if (board[y][x] == 0) {
            LeftOnClickAButton(y - 1, x - 1);
            LeftOnClickAButton(y - 1, x);
            LeftOnClickAButton(y - 1, x + 1);
            LeftOnClickAButton(y, x - 1);
            LeftOnClickAButton(y, x + 1);
            LeftOnClickAButton(y + 1, x - 1);
            LeftOnClickAButton(y + 1, x);
            LeftOnClickAButton(y + 1, x + 1);
        }

        clearButtonN++;
        if (clearButtonN == buttonN - bombN) {
//            this.dispose();
            VictoryJFrame victoryJFrame = new VictoryJFrame(0);
        }
    }

    public void RightOnClickAButton(int y, int x) {
        step++;
        if (boardStatus[y][x] == 0) {
            buttonList.get(y).get(x).setIcon(flag);
            flags++;
            if (board[y][x] == -1) rightF++;
            boardStatus[y][x] = 2;
        } else if (boardStatus[y][x] == 2) {
            buttonList.get(y).get(x).setIcon(norm);
            flags--;
            if (board[y][x] == -1) rightF--;
            boardStatus[y][x] = 0;
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (dif != '4') {
            int c = e.getButton();
            MineSweeperJButton button = (MineSweeperJButton) e.getSource();
            int x = button.get_x();
            int y = button.get_y();
            if (y < 0 || y > scale_y - 1 || x < 0 || x > scale_x - 1 || boardStatus[y][x] == 1) {
                return;
            }
            if (c == MouseEvent.BUTTON1) {
                //左键
                LeftOnClickAButton(y, x);
                step++;
                Refresh();
            } else if (c == MouseEvent.BUTTON3) {
                //右键
                RightOnClickAButton(y, x);
                Refresh();
            }
        } else {
            int c = e.getButton();
            MineSweeperJButton button = (MineSweeperJButton) e.getSource();
            int x = button.get_x();
            int y = button.get_y();
            if (y < 0 || y > scale_y - 1 || x < 0 || x > scale_x - 1 || boardStatus[y][x] == 1 ||
                    !(
                            (y <= person.pos_y + 1 && y >= person.pos_y - 1) &&
                                    (x <= person.pos_x + 1 && x >= person.pos_x - 1)
                    )
            ) {
                this.requestFocus();  //键盘重新获得监听
                return;
            }
            if (c == MouseEvent.BUTTON1) {
                //左键
                LeftOnClickAButton(y, x);
                this.requestFocus();  //键盘重新获得监听
                step++;
                Refresh();
            } else if (c == MouseEvent.BUTTON3) {
                //右键
                RightOnClickAButton(y, x);
                this.requestFocus();  //键盘重新获得监听
                Refresh();
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (dif != '4') {
            int c = e.getButton();
            MineSweeperJButton button = (MineSweeperJButton) e.getSource();
            int x = button.get_x();
            int y = button.get_y();
            if (boardStatus[y][x] == 0 && button.is_highlight == 0) {
                button.setIcon(highlight);
                button.is_highlight = 1;
            }
        } else {
            int c = e.getButton();
            MineSweeperJButton button = (MineSweeperJButton) e.getSource();
            int x = button.get_x();
            int y = button.get_y();
            if (boardStatus[y][x] == 0 && button.is_highlight == 0 &&
                    (y <= person.pos_y + 1 && y >= person.pos_y - 1) &&
                    (x <= person.pos_x + 1 && x >= person.pos_x - 1) &&
                    !(x == person.pos_x && y == person.pos_y)
            ) {
                button.setIcon(highlight);
                button.is_highlight = 1;
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        int c = e.getButton();
        MineSweeperJButton button = (MineSweeperJButton) e.getSource();
        int x = button.get_x();
        int y = button.get_y();
        if (boardStatus[y][x] == 0 && button.is_highlight == 1) {
            button.setIcon(norm);
            button.is_highlight = 0;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    private void TestVictory() {
        if (person.health <= 0) {
            System.out.println("游戏失败");
            this.dispose();
            MSGameOverJFrame gameOverJFrame = new MSGameOverJFrame();
            return;
        }
        if (person.pos_x == scale_x - 1 && person.pos_y == 0) {
            score = rightF * 20 + person.health * 10 - step;
            VictoryJFrame victoryJFrame = new VictoryJFrame(score);
            System.out.println(score);
            try {
                RWCache.writeIntoCache(String.valueOf(score), "score");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code) {
            case 37:  //左
                if (person.pos_x > 0 && boardStatus[person.pos_y][person.pos_x - 1] != 2) {
                    person.pos_x--;
                    step++;
                    if (board[person.pos_y][person.pos_x] == -1) {
                        person.health--;
                    }
                    TestVictory();
                    Refresh();
                    RefreshBoard(person.pos_y, person.pos_x + 1);
                } else return;
                break;
            case 38:  //上
                if (person.pos_y > 0 && boardStatus[person.pos_y - 1][person.pos_x] != 2) {
                    person.pos_y--;
                    step++;
                    System.out.println(person.pos_y);
                    if (board[person.pos_y][person.pos_x] == -1) {
                        person.health--;
                    }
                    TestVictory();
                    Refresh();
                    RefreshBoard(person.pos_y + 1, person.pos_x);
                } else return;
                break;
            case 39:  //右
                if (person.pos_x < scale_x - 1 && boardStatus[person.pos_y][person.pos_x + 1] != 2) {
                    person.pos_x++;
                    step++;
                    if (board[person.pos_y][person.pos_x] == -1) {
                        person.health--;
                    }
                    TestVictory();
                    Refresh();
                    RefreshBoard(person.pos_y, person.pos_x - 1);
                } else return;
                break;
            case 40:  //下
                if (person.pos_y < scale_y - 1 && boardStatus[person.pos_y + 1][person.pos_x] != 2) {
                    person.pos_y++;
                    step++;
                    if (board[person.pos_y][person.pos_x] == -1) {
                        person.health--;
                    }
                    TestVictory();
                    Refresh();
                    RefreshBoard(person.pos_y - 1, person.pos_x);
                } else return;
                break;
            case 32:  //扫出周围一圈雷
                if (person.ult > 0) {
                    Ult(person.pos_y - 1, person.pos_x - 1);
                    Ult(person.pos_y - 1, person.pos_x);
                    Ult(person.pos_y - 1, person.pos_x + 1);
                    Ult(person.pos_y, person.pos_x - 1);
                    Ult(person.pos_y, person.pos_x + 1);
                    Ult(person.pos_y + 1, person.pos_x - 1);
                    Ult(person.pos_y + 1, person.pos_x);
                    Ult(person.pos_y + 1, person.pos_x + 1);
                    person.ult--;
                    Refresh();
                }

                break;
        }
    }
}


