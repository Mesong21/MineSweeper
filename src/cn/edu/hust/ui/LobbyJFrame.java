package cn.edu.hust.ui;

import cn.edu.hust.FileUtils.RWCache;
import cn.edu.hust.GoldMinner.GMwin.GMGameWin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

//大厅界面
public class LobbyJFrame extends JFrame implements ActionListener {
    JMenuItem exitItem = new JMenuItem("关闭游戏");
    JMenuItem changeBGItem = new JMenuItem("更改大厅背景");
    JMenuItem aboutUsItem = new JMenuItem("访问官网");
    JButton buttonForGame1 = new JButton("扫雷");
    JButton buttonForGame2 = new JButton("2048");
    JButton buttonForGame3 = new JButton("黄金矿工");
    String ourWebSite = "http://www.baidu.com";

    public LobbyJFrame() throws IOException {
        // 初始化JFrame
        initJFrame();
        // 初始化菜单
        initJMenuBar();
        // 初始化大厅背景图片
        initBackGround();
        // 初始化大厅游戏选择按钮
        initButtons();
        // 显示界面
        this.setVisible(true);
    }

    private void initJMenuBar() {
        // 菜单初始化
        JMenuBar jMenuBar = new JMenuBar();

        JMenu menu = new JMenu("菜单");
        JMenu aboutUs = new JMenu("关于我们");

        exitItem.addActionListener(this);
        changeBGItem.addActionListener(this);
        aboutUsItem.addActionListener(this);

        menu.add(changeBGItem);
        menu.add(exitItem);
        aboutUs.add(aboutUsItem);

        jMenuBar.add(menu);
        jMenuBar.add(aboutUs);

        this.setJMenuBar(jMenuBar);
    }

    private void initJFrame() {
        // JFrame初始化
        this.setSize(720, 1000);
        this.setTitle("游戏大厅");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(3);
        this.setLayout(null);
        Image logo = new ImageIcon("src/Resource/logo.jpg").getImage();
        this.setIconImage(logo);
    }

    private void initBackGround() throws IOException {
        // 大厅背景图片初始化
        StringBuilder sb = new StringBuilder();
        sb.append("src/Resource/lobby");
        sb.append(RWCache.readFromCache("cahe_lobby_bgImage"));
        sb.append(".png");
        ImageIcon lobbyBackGroundIcon = new ImageIcon(sb.toString());
        JLabel jLabel = new JLabel(lobbyBackGroundIcon);
        jLabel.setBounds(0, 20, 1920, 1080);
        this.getLayeredPane().add(jLabel, 99);
    }

    private void initButtons() {
        // 初始化大厅游戏选择按钮

        buttonForGame1.setBounds(0, 300, 400, 50);
        buttonForGame1.setBackground(Color.white);
        buttonForGame1.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonForGame1.addActionListener(this);
        buttonForGame1.setBorder(BorderFactory.createRaisedBevelBorder());

        buttonForGame2.setBounds(0, 500, 400, 50);
        buttonForGame2.setBackground(Color.white);
        buttonForGame2.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonForGame2.addActionListener(this);
        buttonForGame2.setBorder(BorderFactory.createRaisedBevelBorder());

        // 增加buttonForGame3
        buttonForGame3.setBounds(0, 700, 400, 50);
        buttonForGame3.setBackground(Color.white);
        buttonForGame3.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonForGame3.addActionListener(this);

        this.getLayeredPane().add(buttonForGame1, JLayeredPane.PALETTE_LAYER);
        this.getLayeredPane().add(buttonForGame2, JLayeredPane.PALETTE_LAYER);
        this.getLayeredPane().add(buttonForGame3, JLayeredPane.PALETTE_LAYER);
        // this.add(buttonForGame1);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == exitItem) {
            System.out.println("关闭游戏");
            System.exit(0);
        } else if (source == changeBGItem) {
            System.out.println("更换大厅背景图片");
            ChangeBGJFrame changeBGJFrame = new ChangeBGJFrame(this);
        } else if (source == aboutUsItem) {
            System.out.println("访问官网");
            Desktop desktop = Desktop.getDesktop();
            URI uri = null;
            try {
                uri = new URI(ourWebSite);
            } catch (URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
            try {
                desktop.browse(uri); // 使用默认浏览器打开超链接
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else if (source == buttonForGame1) {
            System.out.println("启动扫雷");
            try {
                MineSweeperJFrame mineSweeperJFrame = new MineSweeperJFrame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            this.dispose();

        } else if (source == buttonForGame2) {
            System.out.println("启动2048");
            Game2048JFrame game2048JFrame = new Game2048JFrame();
            this.dispose();
        } else if (source == buttonForGame3) {
            System.out.println("启动黄金矿工");
            GMGameWin gmgameWin = new GMGameWin();
            this.dispose();
            // Game2048JFrame game2048JFrame = new Game2048JFrame(gmgameWin);
            new Thread(() -> gmgameWin.launch()).start();
        }

    }
}
