package GUI;

import javax.swing.*;
import java.awt.*;

class RulesFrame extends JFrame {

    RulesFrame() {
        setSize(610, 420);
        setResizable(false);
        setLocationRelativeTo(null);
        setContentPane(new RulesPanel());
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    class RulesPanel extends JPanel {
        RulesPanel() {
            setLayout(null);
            JButton back = new JButton(new ImageIcon("files/buttons/back.png"));
            back.setBorderPainted(false);
            back.setSize(back.getIcon().getIconWidth(), back.getIcon().getIconHeight());
            back.setContentAreaFilled(false);
            back.setLocation(20, 270);
            back.addActionListener(e -> {
                new StartFrame("Wolfs and Sheep");
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
                frame.dispose();
            });
            add(back);
            setVisible(true);
        }

        @Override
        public void paintComponent(Graphics g) {
            Image background = new ImageIcon("files/backgrounds/rulesback.png").getImage();
            g.drawImage(background, 0, 0, null);
        }
    }
}