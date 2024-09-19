package cn.edu.hust.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GameOver2048JFrame extends JFrame implements ActionListener {
    int score;      //得分
    JButton button1;
    JButton button2;
    Game2048JFrame game2048JFrame;
    public GameOver2048JFrame(Game2048JFrame game2048JFrame,int score){
        this.score = score;
        this.game2048JFrame = game2048JFrame;
        initJFrame();
        initButtons();
        this.setVisible(true);
    }
    private void initJFrame(){
        this.setSize(300,400);
        StringBuilder sb = new StringBuilder("score: ");
        sb.append(score);
        this.setTitle(sb.toString());
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        Image logo = new ImageIcon("src/Resource/logo.jpg").getImage();
        this.setIconImage(logo);
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
            game2048JFrame.dispose();
            this.dispose();
            Game2048JFrame game2048JFrame = new Game2048JFrame();
        }else if(source == button2){
            System.out.println("回到大厅");
            game2048JFrame.dispose();
            this.dispose();
            try {
                LobbyJFrame lobbyJFrame = new LobbyJFrame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
