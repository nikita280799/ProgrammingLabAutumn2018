package GUI;

import javax.swing.*;
import java.awt.*;

class VictoryFrame extends JFrame {

    VictoryFrame(boolean isSheepWin) {
        setSize(605, 407);
        setResizable(false);
        setLocationRelativeTo(null);
        setContentPane(new VictoryPanel(isSheepWin));
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    class VictoryPanel extends JPanel {

        boolean isSheepWin;

        VictoryPanel(boolean isSheepWin) {
            this.isSheepWin = isSheepWin;
            this.setLayout(null);
            JButton restart = new JButton();
            restart.setContentAreaFilled(false);
            restart.setBorderPainted(false);
            if (isSheepWin) {
                restart.setSize(265, 30);
                restart.setLocation(20, 330);
            } else {
                restart.setSize(295, 30);
                restart.setLocation(10, 330);
            }
            restart.addActionListener(e -> {
                new SettingsFrame();
                dispose();
            });
            add(restart);
            JButton exit = new JButton();
            exit.setContentAreaFilled(false);
            exit.setBorderPainted(false);
            if (isSheepWin) {
                exit.setSize(115, 30);
                exit.setLocation(467, 330);
            } else {
                exit.setSize(130, 30);
                exit.setLocation(410, 333);
            }
            exit.addActionListener(e -> System.exit(0));
            add(exit);
        }

        @Override
        public void paintComponent(Graphics g) {
            Image back = new ImageIcon(isSheepWin ? "files/backgrounds/sheepwin.png"
                    : "files/backgrounds/wolfswin.png").getImage();
            g.drawImage(back, 0, 0, null);
        }
    }
}