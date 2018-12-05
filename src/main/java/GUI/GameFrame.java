package GUI;

import javax.swing.*;

class GameFrame extends JFrame {
    GameFrame(boolean isPlayingForSheep, boolean isPlayingForWolfs, int level) {
        setSize(490, 518);
        setLocationRelativeTo(null);
        setResizable(false);
        setContentPane(new GamePanel(isPlayingForSheep, isPlayingForWolfs, level));
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}