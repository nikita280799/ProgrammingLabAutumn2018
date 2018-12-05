package GUI;

import Logic.Board;
import Logic.Cell;
import Logic.GameAI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GamePanel extends JPanel {

    private Board board;

    private GameAI gameAI;

    private Cell currentCell;

    private boolean isHumanPlayingForSheep;

    private boolean isHumanPlayingForWolfs;

    private boolean isStepOfSheep;

    private final static int CELL_WIDTH = 60;

    GamePanel(boolean isHumanPlayingForSheep, boolean isHumanPlayingForWolfs, int level) {
        this.isHumanPlayingForSheep = isHumanPlayingForSheep;
        this.isHumanPlayingForWolfs = isHumanPlayingForWolfs;
        isStepOfSheep = isHumanPlayingForSheep;
        this.setLayout(null);
        setSize(480, 480);
        setVisible(true);
        board = new Board();
        gameAI = new GameAI(board, level);
        if (!isHumanPlayingForSheep) gameAI.step(true);
        this.addMouseListener(new MouseListener() {
            public void mousePressed(MouseEvent e) {
            }

            public void mouseClicked(MouseEvent e) {
                onMouseClicked(e.getPoint());
            }

            public void mouseReleased(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        });
    }

    private void onMouseClicked(Point clickedPoint) {
        Cell clickedCell = new Cell(clickedPoint.x / CELL_WIDTH, clickedPoint.y / CELL_WIDTH);
        if (!board.isItEmptyCell(clickedCell) && (board.isItSheepPosition(clickedCell) && isStepOfSheep && isHumanPlayingForSheep
                || board.isItWolfPosition(clickedCell) && !isStepOfSheep && isHumanPlayingForWolfs)) {
            currentCell = clickedCell;
        }
        if (currentCell != null && board.isItEmptyCell(clickedCell) && board.isItCorrectStep(currentCell, clickedCell)) {
            board.step(currentCell, clickedCell);
            if (isHumanPlayingForSheep && isHumanPlayingForWolfs) isStepOfSheep = !isStepOfSheep;
            currentCell = null;
            if (!isHumanPlayingForSheep) gameAI.step(true);
            if (!isHumanPlayingForWolfs) gameAI.step(false);
            if (board.isSheepWin()) onEndOfGame(true);
            if (board.isWolfWin()) onEndOfGame(false);
        }
        GamePanel.super.repaint();
    }

    private void onEndOfGame(boolean isSheepWin) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.dispose();
        new VictoryFrame(isSheepWin);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image sheepImage = new ImageIcon("files/images/sheep.png").getImage();
        Image wolfImage = new ImageIcon("files/images/wolf.png").getImage();
        Image back = new ImageIcon("files/images/board.png").getImage();
        Image currentImage = new ImageIcon("files/images/current.png").getImage();
        g.drawImage(back, 0, 0, null);
        g.drawImage(sheepImage, board.sheepCell.i * CELL_WIDTH, board.sheepCell.j * CELL_WIDTH, null);
        for (Cell cell : board.wolfsCells) {
            g.drawImage(wolfImage, cell.i * CELL_WIDTH, cell.j * CELL_WIDTH, null);
        }
        if (currentCell != null) {
            g.drawImage(currentImage, currentCell.i * CELL_WIDTH, currentCell.j * CELL_WIDTH, null);
        }
        g.setColor(new Color(25, 139, 38));
        g.setFont(new Font("PixelForce", Font.PLAIN, 20));
        g.drawString(isStepOfSheep ? "Ходит овца" : "Ходят волки", 10, 470);
    }
}