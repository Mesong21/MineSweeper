package cn.edu.hust.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class VictoryJFrame extends JFrame implements ActionListener {
    JButton button1;
    JButton button2;
    public VictoryJFrame(int score){
        initJFrame(score);
        initButtons();
        this.setVisible(true);
    }
    private void initJFrame(int score){
        this.setSize(600,600);
        this.setTitle("Victory-胜利");
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(null);
        Image logo = new ImageIcon("src/Resource/logo.jpg").getImage();
        this.setIconImage(logo);

        JLabel ShowScore = new JLabel("得分：" + score);
        ShowScore.setBounds(300,300,150,30);
        ShowScore.setFont(new Font("仿宋", Font.PLAIN, 24));
        this.getLayeredPane().add(ShowScore);
    }

    private void initButtons(){
        button1 = new JButton("再来一局");
        button1.setBounds(75,100,150,50);
        button1.setBackground(Color.white);
        button1.setAlignmentX(Component.CENTER_ALIGNMENT);
        button1.addActionListener(this);
        button1.setBorder(BorderFactory.createRaisedBevelBorder());

        button2 = new JButton("回到大厅");
        button2.setBounds(75,250,150,50);
        button2.setBackground(Color.white);
        button2.setAlignmentX(Component.CENTER_ALIGNMENT);
        button2.addActionListener(this);
        button2.setBorder(BorderFactory.createRaisedBevelBorder());

        this.getLayeredPane().add(button1,JLayeredPane.PALETTE_LAYER);
        this.getLayeredPane().add(button2,JLayeredPane.PALETTE_LAYER);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source == button1){
            System.out.println("再来一局");
            this.dispose();
            try {
                MineSweeperJFrame mineSweeperJFrame = new MineSweeperJFrame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }else if(source == button2){
            System.out.println("回到大厅");
            this.dispose();
            try {
                LobbyJFrame lobbyJFrame = new LobbyJFrame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
