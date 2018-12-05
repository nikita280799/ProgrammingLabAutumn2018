package GUI;

import javax.swing.*;
import java.awt.*;

class SettingsFrame extends JFrame {

    SettingsFrame() {
        setSize(620, 440);
        setResizable(false);
        setLocationRelativeTo(null);
        setContentPane(new SettingsPanel());
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    class SettingsPanel extends JPanel {

        private JRadioButton but1, but2, but3;

        private JComboBox<Integer> comboBox;

        private ButtonGroup bg = new ButtonGroup();

        SettingsPanel() {
            setLayout(null);
            Integer[] levels = {1, 2, 3, 4, 5, 6, 7, 8};
            comboBox = new JComboBox<>(levels);
            comboBox.setLocation(370, 80);
            comboBox.setSize(100, 30);
            add(comboBox);
            but1 = new JRadioButton("Играть за овцу");
            but2 = new JRadioButton("Играть за волков");
            but3 = new JRadioButton("Игрок против игрока");
            initRadioButton(but1, 200, 420);
            initRadioButton(but2, 180, 20);
            initRadioButton(but3, 220, 200);
            JButton ok = new JButton(new ImageIcon("files/buttons/startgame.png"));
            ok.setBorderPainted(false);
            ok.setSize(ok.getIcon().getIconWidth(), ok.getIcon().getIconHeight());
            ok.setContentAreaFilled(false);
            ok.setLocation(210, 320);
            ok.addActionListener(e -> onPressOK());
            add(ok);
        }

        private void initRadioButton(JRadioButton button, int width, int x) {
            button.setFont(new Font("PixelForce", Font.PLAIN, 14));
            button.setContentAreaFilled(false);
            button.setSize(width, 50);
            button.setLocation(x, 20);
            bg.add(button);
            add(button);
        }

        private void onPressOK() {
            if (!but1.isSelected() && !but2.isSelected() && !but3.isSelected()) {
                JOptionPane.showMessageDialog(this, "Пожалуйста выберите вариант игры ");
            } else {
                boolean isPlayingForSheep = but1.isSelected() || but3.isSelected();
                boolean isPlayingForWolfs = but2.isSelected() || but3.isSelected();
                int level = comboBox.getItemAt(comboBox.getSelectedIndex());
                new GameFrame(isPlayingForSheep, isPlayingForWolfs, level);
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
                frame.dispose();
            }
        }

        @Override
        public void paintComponent(Graphics g) {
            Image back = new ImageIcon("files/backgrounds/settingsback.png").getImage();
            g.drawImage(back, 0, 0, null);
            setFont(new Font("PixelForce", Font.PLAIN, 14));
            g.drawString("Уровень сложности: ", 180, 100);
        }
    }
}