package cn.edu.hust.ui;

import cn.edu.hust.FileUtils.RWCache;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ChangeBGJFrame extends JFrame implements ActionListener {
    JFrame lobbyJFrame;
    JButton buttonForBG1 = new JButton("エルマ");
    JButton buttonForBG2 = new JButton("いさな");
    public ChangeBGJFrame(JFrame lobbyJFrame){
        this.lobbyJFrame = lobbyJFrame;
        initJFrame();
        initButtons();
        initPic();


        this.setVisible(true);
    }
    private void initJFrame() {
        //JFrame初始化
        this.setSize(600,800);
        this.setTitle("更换大厅背景");
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        Image logo = new ImageIcon("src/Resource/logo.jpg").getImage();
        this.setIconImage(logo);
    }

    private void initButtons(){
        //初始化背景选择按钮

        buttonForBG1.setBounds(92,20,100,50);
        buttonForBG1.setBackground(Color.white);
        buttonForBG1.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonForBG1.addActionListener(this);
        buttonForBG1.setBorder(BorderFactory.createRaisedBevelBorder());

        buttonForBG2.setBounds(97,271,100,50);
        buttonForBG2.setBackground(Color.white);
        buttonForBG2.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonForBG2.addActionListener(this);
        buttonForBG2.setBorder(BorderFactory.createRaisedBevelBorder());

        this.getLayeredPane().add(buttonForBG1,JLayeredPane.PALETTE_LAYER);
        this.getLayeredPane().add(buttonForBG2,JLayeredPane.PALETTE_LAYER);
        //this.add(buttonForGame1);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source == buttonForBG1){
            System.out.println("更换为背景1");
            try {
                RWCache.writeIntoCache('1',"cahe_lobby_bgImage");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            lobbyJFrame.dispose();
            this.dispose();
            try {
                LobbyJFrame newLobby = new LobbyJFrame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }else if(source == buttonForBG2){
            System.out.println("更换为背景2");
            try {
                RWCache.writeIntoCache('2',"cahe_lobby_bgImage");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            lobbyJFrame.dispose();
            this.dispose();
            try {
                LobbyJFrame newLobby = new LobbyJFrame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void initPic(){
        //背景图片初始化
        ImageIcon image1 = new ImageIcon("src/Resource/bgSmall1.png");
        JLabel jLabel1 = new JLabel(image1);
        jLabel1.setBounds(92,20,image1.getIconWidth(),image1.getIconHeight());
        this.getLayeredPane().add(jLabel1,99);

        ImageIcon image2 = new ImageIcon("src/Resource/bgSmall2.png");
        JLabel jLabel2 = new JLabel(image2);
        jLabel2.setBounds(92,266,image2.getIconWidth(),image1.getIconHeight());
        this.getLayeredPane().add(jLabel2,99);
    }
}
