package cn.edu.hust.GoldMinner.GMwin;

import cn.edu.hust.GoldMinner.Stuff.Line;
import cn.edu.hust.GoldMinner.Stuff.Mineral.AllMineral;
import cn.edu.hust.GoldMinner.Stuff.Mineral.Gold;
import cn.edu.hust.GoldMinner.Stuff.Mineral.Rock;
import cn.edu.hust.GoldMinner.Stuff.Reward;
import cn.edu.hust.GoldMinner.settings.gmConfig;
import cn.edu.hust.ui.Game2048JFrame;
import cn.edu.hust.ui.LobbyJFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 游戏窗口
 */
public class GMGameWin extends JFrame {

  /*
   * 0-未开始,1-运行中,2-暂停进行合成,3-失败结束,4-成功结束
   */
  public int state;
  public static int level = 1;
  public int mineralNum;
  public Reward rwd; // 奖励道具
  public Game2048JFrame _2048JFrame;

  public List<AllMineral> mineList;
  public BackGround bg;
  public Line line;
  public Board board;
  public Image offScreenImage; // 画布，用于双缓冲
  public Graphics g;

  public GMGameWin() {
    super();
    state = 0;
    level = 1;
    mineralNum = 0;
    mineList = new ArrayList<>();
    bg = new BackGround();
    line = new Line(this);
    board = new Board(this);
    rwd = new Reward(this);
    _2048JFrame = new Game2048JFrame(this);
    this.setVisible(true); // 显示窗口
    offScreenImage = this.createImage(gmConfig.GM_GAME_WIDTH, gmConfig.GM_GAME_HEIGHT);
    g = this.getGraphics();
  }

  public GMGameWin(Game2048JFrame _2048JFrame, Reward rwd) {
    super();
    // state = 0;
    mineralNum = 0;
    mineList = new ArrayList<>();
    bg = new BackGround();
    line = new Line(this);
    board = new Board(this);
    this.setVisible(true); // 显示窗口
    offScreenImage = this.createImage(gmConfig.GM_GAME_WIDTH, gmConfig.GM_GAME_HEIGHT);
    g = this.getGraphics();

    // 奖励和2048窗口一直延续
    this.rwd = rwd;
    this.rwd.gmWin = this;
    this._2048JFrame = _2048JFrame;
    this._2048JFrame.gmWin = this;
  }

  /**
   * 启动游戏窗口
   */
  public void launch() {
    initWin();
    addMineral(gmConfig.MINERAL_NUM);

    // 键盘事件
    addKeyListener((KeyListener) new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        if (state == 1) {
          if (e.getKeyCode() == gmConfig.BOMB_KEY) {
            // 爆破
            rwd.useBomb();
          } else if (e.getKeyCode() == gmConfig.POWER_KEY) {
            // 增加力量
            rwd.usePower();
          } else if (e.getKeyCode() == gmConfig.LUCK_KEY) {
            // 增加运气
            rwd.useLuck();
          }
        }
      }
    });

    // 鼠标事件
    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        switch (state) {
          case 0:
            // 准备开始
            if (e.getButton() == MouseEvent.BUTTON1) {
              state = 1;
              board.startTime = System.currentTimeMillis();
            }
            break;
          case 1:
            // 游戏中
            if (e.getButton() == MouseEvent.BUTTON1) {
              // 线的状态：0-摇摆，1-抓取，2-收回
              if (line.state == 0) {
                line.state = 1;
              }
            } else if (e.getButton() == MouseEvent.BUTTON3) {
              // 暂停以游玩2048
              rwd.usePause();
            }
            break;
          case 2:
            // 暂停中
            if (e.getButton() == MouseEvent.BUTTON3) {
              // 右键，继续倒计时
              state = 1;
              Board.basedt = Board.dt;
            }
            break;

          case 3:
            // 失败
            // System.exit(0);
            if (e.getButton() == MouseEvent.BUTTON1) {
              restart();
            } else if (e.getButton() == MouseEvent.BUTTON3) {
              // 右键回到大厅
              dispose();
              try {
                LobbyJFrame lobbyJFrame = new LobbyJFrame();
              } catch (IOException ex) {
                throw new RuntimeException(ex);
              }
            }
            break;
          case 4:
            // 成功
            if (e.getButton() == MouseEvent.BUTTON1) {
              restart();
            } else if (e.getButton() == MouseEvent.BUTTON3) {
              // 右键回到大厅
              dispose();
              try {
                LobbyJFrame lobbyJFrame = new LobbyJFrame();
              } catch (IOException ex) {
                throw new RuntimeException(ex);
              }
            }
            break;
          default:
            break;
        }
        System.out.println(state);
      }
    });

    // 实现线的运动
    while (true) {
      // 读取文件./src/Cache/score.txt的数据，要求是一个整数
      // 如果扫雷的得分够高，直接进入后面的关卡
      try {
        FileReader fr = new FileReader("src/Cache/score");
        BufferedReader br = new BufferedReader(fr);
        String score = br.readLine();
        br.close();
        fr.close();
        // System.out.println(score);
        int sweeperScore = Integer.parseInt(score);
        if (sweeperScore >= this.board.goal) {
          this.board.now = sweeperScore;
        }
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }

      paint(g);
      mineralNum = mineList.size();
      for (AllMineral m : mineList) {
        if (m.have) {
          mineralNum--;
        }
      }
      // 矿物不够，添加矿物
      if (mineralNum <= gmConfig.MIN_MINERAL_NUM) {
        addMineral(gmConfig.MINERAL_NUM);
      }
      // System.out.println(mineralNum);
      mineralNum = 0;

      if (this.board.now >= this.board.goal) {
        if (level == gmConfig.GAME_LEVEL) {
          // 通关
          state = 4;
        } else
          // 进入下一关
          nextLevel();
      } else if (this.board.t <= 0) {
        // 时间到，游戏结束
        state = 3;
      }
      try {
        Thread.sleep(10); // 每隔10毫秒重绘一次
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      // repaint();
    }

  }

  /**
   * 下一关
   */
  public void nextLevel() {
    level++;
    // 新建一个窗体，释放当前窗体
    GMGameWin gameWin = new GMGameWin(_2048JFrame, rwd);
    this.disposeWin();
    gameWin.state = 1;
    gameWin.launch();
  }

  public void restart() {
    // 重新开始

    this.dispose();
    GMGameWin gameWin = new GMGameWin();
    // state = 0;
    // level = 1;
    new Thread(() -> gameWin.launch()).start();
  }

  @Override
  /*
   * 重写paint方法，绘制背景图片和线的运动
   */
  public void paint(Graphics g) {
    // offScreenImage = this.createImage(Config.GM_GAME_WIDTH,
    // Config.GM_GAME_HEIGHT);
    Graphics gImage = offScreenImage.getGraphics();
    bg.paintSelf(gImage, this); // 先将背景与物体绘制到画布上，再将画布一并绘制到窗口中。

    switch (state) {
      case 0 -> {
        drawWord(gImage, 50, gmConfig.TXT_COLOR, "单击鼠标左键开始。", 200, 400);
        // 写游戏提示，显示如下：
        // 道具不够用时，借助2048来获得吧！
        drawWord(gImage, 14, gmConfig.TXT_COLOR, "温馨提示: 道具不够用时, 借助2048来获得吧! ", 200, 450);
      }
      case 1 -> {
        board.paintSelf(gImage); // 仅在游戏中才绘制面板，线，矿物
        line.paintSelf(gImage); // 先绘制线，再绘制矿物
        for (AllMineral m : mineList) {
          m.paintSelf(gImage);
        }
      }
      case 2 -> { // 暂停
        board.paintSelf(gImage);
        line.paintSelf(gImage);
        for (AllMineral m : mineList) {
          m.paintSelf(gImage);
        }
        drawWord(gImage, 50, gmConfig.TXT_COLOR, "暂停！单击右键继续。", 50, 400);

      }
      case 3 -> {
        drawWord(gImage, 50, gmConfig.TXT_COLOR, "游戏失败!", 250, 400);
        drawWord(gImage, 50, gmConfig.TXT_COLOR, "左键重新开始, 右键回到大厅", 100, 450);
      }
      case 4 -> {
        drawWord(gImage, 50, gmConfig.TXT_COLOR, "恭喜通过全部关卡!", 250, 400);
        drawWord(gImage, 50, gmConfig.TXT_COLOR, "左键重新开始, 右键回到大厅", 100, 450);
      }
      default -> {
      }
    }

    g.drawImage(offScreenImage, 0, 0, null);
  }

  /**
   * 按照概率添加矿物
   */
  public void addMineral(int num) {
    int i = num;
    while (i-- != 0) {
      boolean isPlace = true;
      double rand = Math.random();
      double j = Math.random();
      AllMineral m;
      // 小:中:大=4:3:3, 金:石=4:6
      if (rand < 0.4) {
        m = (j < 0.4 ? new Gold(0) : new Rock(0));
      } else if (rand < 0.7) {
        m = (j < 0.4 ? new Gold(1) : new Rock(1));
      } else {
        m = (j < 0.4 ? new Gold(2) : new Rock(2));
      }

      for (AllMineral med : mineList) {
        if (m.getRect().intersects(med.getRect())) {
          // 不能放置
          isPlace = false;
          break;
        }
      }
      if (isPlace) {
        mineList.add(m);
      } else {
        i++; // 重新生成
      }
    }

    System.out.println("添加矿物成功");
  }

  /**
   * 绘制文本
   */
  public static void drawWord(Graphics g, int size, Color color, String word, int x, int y) {
    g.setColor(color);
    g.setFont(new Font("微软雅黑", Font.BOLD, size));
    g.drawString(word, x, y);
  }

  @Override
  public void dispose() {
    super.dispose();
    _2048JFrame.dispose();
  }

  public void disposeWin() {
    super.dispose();
  }

  public void initWin() {
    this.setSize(gmConfig.GM_GAME_WIDTH, gmConfig.GM_GAME_HEIGHT); // 设置窗口大小
    // 设置窗口偏左
    this.setLocation(gmConfig.LEFT_EDGE, 0);
    this.setTitle("黄金矿工");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 关闭窗口时退出程序
  }
}
