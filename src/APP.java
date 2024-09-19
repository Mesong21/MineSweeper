//小程序的入口

import cn.edu.hust.FileUtils.RWCache;
import cn.edu.hust.ui.LobbyJFrame;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class APP {

    public static void main(String[] args) throws IOException {
        //获取Cache记录
        char mark = RWCache.readFromCache("cahe_lobby_bgImage");
        //创建游戏大厅界面
        LobbyJFrame lobbyJFrame = new LobbyJFrame();

    }
}
