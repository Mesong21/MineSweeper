import cn.edu.hust.GoldMinner.GMwin.GMGameWin;

public class MinerApp {
    public static void main(String[] args) {
        GMGameWin gmgameWin = new GMGameWin();
        new Thread(() -> gmgameWin.launch()).start();
    }
}
