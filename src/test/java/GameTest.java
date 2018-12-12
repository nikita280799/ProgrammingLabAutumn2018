import Logic.Board;
import Logic.Cell;
import Logic.GameAI;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class GameTest {

    @Test
    public void stepTest() {
        Board board = new Board();
        Cell oldCell = new Cell(0, 3);
        Cell newCell = new Cell(1, 2);
        board.step(oldCell, newCell);
        assertTrue(board.isItSheepCell(newCell));
        assertFalse(board.isItSheepCell(oldCell));
        oldCell = new Cell(7, 0);
        newCell = new Cell(6, 1);
        board.step(oldCell, newCell);
        assertTrue(board.isItWolfCell(newCell));
        assertFalse(board.isItWolfCell(oldCell));
        oldCell = new Cell(1, 2);
        newCell = new Cell(2, 3);
        board.step(oldCell, newCell);
        assertTrue(board.isItSheepCell(newCell));
        assertFalse(board.isItSheepCell(oldCell));
    }

    @Test
    public void getAvaliableStepsTest() {
        Board board = new Board();
        board.clear();
        board.setSheep(new Cell(3, 3));
        board.setWolf(new Cell(2, 2));
        board.setWolf(new Cell(4, 2));
        board.setWolf(new Cell(5, 5));
        board.setWolf(new Cell(7, 1));
        Set<Cell> expected = new HashSet<>();
        expected.add(new Cell(4, 4));
        expected.add(new Cell(2, 4));
        assertEquals(expected, board.getAvaliableSteps(board.sheepCell, true));
        board.clear();
        board.setSheep(new Cell(5, 5));
        board.setWolf(new Cell(4, 2));
        board.setWolf(new Cell(5, 3));
        board.setWolf(new Cell(6, 4));
        board.setWolf(new Cell(7, 5));
        expected.clear();
        assertEquals(expected, board.getAvaliableSteps(new Cell(6, 4), false));
        board.clear();
        board.setSheep(new Cell(4, 2));
        board.setWolf(new Cell(6, 2));
        board.setWolf(new Cell(6, 4));
        board.setWolf(new Cell(4, 6));
        board.setWolf(new Cell(7, 7));
        expected.add(new Cell(5, 3));
        expected.add(new Cell(5, 1));
        assertEquals(expected, board.getAvaliableSteps(new Cell(6, 2), false));
    }

    @Test
    public void isItCorrectStepTest() {
        Board board = new Board();
        assertFalse(board.isItCorrectStep(board.sheepCell, new Cell(7, 1)));
        assertFalse(board.isItCorrectStep(board.sheepCell, new Cell(0, 3)));
        assertFalse(board.isItCorrectStep(new Cell(7, 0), new Cell(1, 1)));
        assertFalse(board.isItCorrectStep(new Cell(7, 0), new Cell(8, -1)));
        board.clear();
        board.setSheep(new Cell(3, 3));
        board.setWolf(new Cell(4, 2));
        board.setWolf(new Cell(5, 3));
        board.setWolf(new Cell(6, 4));
        board.setWolf(new Cell(7, 5));
        assertFalse(board.isItCorrectStep(new Cell(4, 2), new Cell(5, 1)));
        assertFalse(board.isItCorrectStep(board.sheepCell, new Cell(4, 2)));
        assertFalse(board.isItCorrectStep(new Cell(7, 5), new Cell(6, 4)));
        assertTrue(board.isItCorrectStep(board.sheepCell, new Cell(4, 4)));
        assertTrue(board.isItCorrectStep(board.sheepCell, new Cell(2, 4)));
        assertTrue(board.isItCorrectStep(new Cell(5, 3), new Cell(4, 4)));
        assertTrue(board.isItCorrectStep(new Cell(7, 5), new Cell(6, 6)));
    }

    @Test
    public void heuristicTest() {
        Board board = new Board();
        GameAI gameAI = new GameAI(board, 3);
        board.clear();
        board.setSheep(new Cell(3, 3));
        board.setWolf(new Cell(4, 2));
        board.setWolf(new Cell(5, 3));
        board.setWolf(new Cell(6, 4));
        board.setWolf(new Cell(7, 5));
        assertEquals(4, gameAI.getHeuristic());
        board.clear();
        board.setSheep(new Cell(1, 7));
        board.setWolf(new Cell(2, 6));
        board.setWolf(new Cell(2, 4));
        board.setWolf(new Cell(3, 1));
        board.setWolf(new Cell(5, 3));
        assertEquals(10, gameAI.getHeuristic());
        board.clear();
        board.setSheep(new Cell(2, 2));
        board.setWolf(new Cell(6, 0));
        board.setWolf(new Cell(6, 2));
        board.setWolf(new Cell(6, 4));
        board.setWolf(new Cell(6, 6));
        assertEquals(76, gameAI.getHeuristic());
        board.clear();
        board.setSheep(new Cell(3, 5));
        board.setWolf(new Cell(2, 4));
        board.setWolf(new Cell(4, 4));
        board.setWolf(new Cell(2, 6));
        board.setWolf(new Cell(4, 6));
        assertEquals(99, gameAI.getHeuristic());
    }

    @Test
    public void minMaxTest() {
        Board board = new Board();
        GameAI lowGameAI = new GameAI(board, 1);
        GameAI midGameAI = new GameAI(board, 4);
        GameAI highGameAI = new GameAI(board, 8);
        assertEquals(72, lowGameAI.runMinMax(true, 0, Integer.MIN_VALUE, Integer.MAX_VALUE));
        assertEquals(74, midGameAI.runMinMax(true, 0, Integer.MIN_VALUE, Integer.MAX_VALUE));
        assertEquals(76, highGameAI.runMinMax(true, 0, Integer.MIN_VALUE, Integer.MAX_VALUE));
        board.clear();
        board.setSheep(new Cell(3, 3));
        board.setWolf(new Cell(5, 1));
        board.setWolf(new Cell(5, 3));
        board.setWolf(new Cell(5, 5));
        board.setWolf(new Cell(5, 7));
        assertEquals(81, lowGameAI.runMinMax(false, 0, Integer.MIN_VALUE, Integer.MAX_VALUE));
        assertEquals(8, midGameAI.runMinMax(false, 0, Integer.MIN_VALUE, Integer.MAX_VALUE));
        assertEquals(86, highGameAI.runMinMax(false, 0, Integer.MIN_VALUE, Integer.MAX_VALUE));
        board.clear();
        board.setSheep(new Cell(5, 5));
        board.setWolf(new Cell(4, 2));
        board.setWolf(new Cell(5, 3));
        board.setWolf(new Cell(6, 4));
        board.setWolf(new Cell(6, 6));
        assertEquals(7, lowGameAI.runMinMax(true, 0, Integer.MIN_VALUE, Integer.MAX_VALUE));
        assertEquals(5, midGameAI.runMinMax(true, 0, Integer.MIN_VALUE, Integer.MAX_VALUE));
        assertEquals(2, highGameAI.runMinMax(true, 0, Integer.MIN_VALUE, Integer.MAX_VALUE));
        board.clear();
        board.setSheep(new Cell(2, 6));
        board.setWolf(new Cell(1, 3));
        board.setWolf(new Cell(2, 4));
        board.setWolf(new Cell(3, 5));
        board.setWolf(new Cell(4, 6));
        assertEquals(96, lowGameAI.runMinMax(false, 0, Integer.MIN_VALUE, Integer.MAX_VALUE));
        assertEquals(98, midGameAI.runMinMax(false, 0, Integer.MIN_VALUE, Integer.MAX_VALUE));
        assertEquals(100, highGameAI.runMinMax(false, 0, Integer.MIN_VALUE, Integer.MAX_VALUE));
    }

    @Test
    public void aIStepTestInSomeGeneralSituations() {
        Board board = new Board();
        GameAI gameAI = new GameAI(board, 3);
        board.clear();
        board.setSheep(new Cell(3, 3));
        board.setWolf(new Cell(4, 2));
        board.setWolf(new Cell(5, 3));
        board.setWolf(new Cell(6, 4));
        board.setWolf(new Cell(7, 5));
        gameAI.step(true);
        assertTrue(board.isItSheepCell(new Cell(2, 2)));
        board.clear();
        board.setSheep(new Cell(4, 2));
        board.setWolf(new Cell(5, 1));
        board.setWolf(new Cell(5, 3));
        board.setWolf(new Cell(4, 0));
        board.setWolf(new Cell(2, 2));
        gameAI.step(true);
        assertTrue(board.isItSheepCell(new Cell(3, 3)));
        board.clear();
        board.setSheep(new Cell(1, 7));
        board.setWolf(new Cell(0, 4));
        board.setWolf(new Cell(1, 5));
        board.setWolf(new Cell(2, 6));
        board.setWolf(new Cell(3, 7));
        gameAI.step(false);
        assertTrue(board.isItWolfCell(new Cell(0, 6)));
        board.clear();
        board.setSheep(new Cell(5, 1));
        board.setWolf(new Cell(6, 0));
        board.setWolf(new Cell(6, 2));
        board.setWolf(new Cell(4, 0));
        board.setWolf(new Cell(5, 3));
        gameAI.step(false);
        assertTrue(board.isItWolfCell(new Cell(4, 2)));
    }

    @Test
    public void aIStepTestInSituationsWhenSheepCanWin() {
        Board board = new Board();
        GameAI gameAI = new GameAI(board, 1);
        board.clear();
        board.setSheep(new Cell(6, 3));
        board.setWolf(new Cell(7, 2));
        board.setWolf(new Cell(4, 3));
        board.setWolf(new Cell(6, 5));
        board.setWolf(new Cell(3, 6));
        gameAI.step(true);
        assertTrue(board.isSheepWin());
        board.clear();
        board.setSheep(new Cell(6, 7));
        board.setWolf(new Cell(6, 5));
        board.setWolf(new Cell(1, 2));
        board.setWolf(new Cell(5, 3));
        board.setWolf(new Cell(4, 5));
        gameAI.step(true);
        assertTrue(board.isSheepWin());
    }

    @Test
    public void aIStepTestInSituationsWhenWolfsCanWin() {
        Board board = new Board();
        GameAI gameAI = new GameAI(board, 1);
        board.clear();
        board.setSheep(new Cell(3, 4));
        board.setWolf(new Cell(4, 3));
        board.setWolf(new Cell(2, 3));
        board.setWolf(new Cell(4, 5));
        board.setWolf(new Cell(3, 6));
        gameAI.step(false);
        assertTrue(board.isWolfWin());
        board.clear();
        board.setSheep(new Cell(2, 7));
        board.setWolf(new Cell(1, 6));
        board.setWolf(new Cell(3, 2));
        board.setWolf(new Cell(4, 3));
        board.setWolf(new Cell(4, 7));
        gameAI.step(false);
        assertTrue(board.isWolfWin());
    }
}