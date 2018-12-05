package GUI;

import javax.swing.*;
import java.awt.*;

public class StartFrame extends JFrame {

    StartFrame(String s) {
        super(s);
        setSize(610, 402);
        setResizable(false);
        setLocationRelativeTo(null);
        setContentPane(new StartPanel());
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public class StartPanel extends JPanel {

        StartPanel() {
            setLayout(null);
            JButton newGame = initButton(new ImageIcon("files/buttons/newgame.png"), 20, 40);
            newGame.addActionListener(e -> onNewGame());
            JButton rules = initButton(new ImageIcon("files/buttons/rules.png"), 40, 90);
            rules.addActionListener(e -> onRules());
            JButton exit = initButton(new ImageIcon("files/buttons/exit.png"), 400, 330);
            exit.addActionListener(e -> onExit());
            setVisible(true);
        }

        private JButton initButton(ImageIcon icon, int x, int y) {
            JButton button = new JButton(icon);
            button.setBorderPainted(false);
            button.setSize(button.getIcon().getIconWidth(), button.getIcon().getIconHeight());
            button.setLocation(x, y);
            add(button);
            return button;
        }

        private void onRules() {
            new RulesFrame();
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.dispose();
        }

        private void onNewGame() {
            new SettingsFrame();
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.dispose();
        }

        private void onExit() {
            System.exit(0);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Image back = new ImageIcon("files/backgrounds/startback.png").getImage();
            g.drawImage(back, 0, 0, null);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StartFrame("Wolfs and Sheep"));
    }
}