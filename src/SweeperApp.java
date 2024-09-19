import java.io.IOException;

import cn.edu.hust.ui.MineSweeperJFrame;

public class SweeperApp {
    public static void main(String[] args) {
        try {
                MineSweeperJFrame mineSweeperJFrame = new MineSweeperJFrame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
    }
}
