package Logic;

import java.util.HashSet;
import java.util.Set;

public class GameAI {

    private int level;

    private Board board;

    public GameAI(Board board, int level) {
        this.board = board;
        this.level = level;
    }

    public void step(boolean isItSheep) {
        runMinMax(isItSheep, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /** Возвращает оценку текущего состояния доски: от 0 до 100. При этом, чем ближе оценка к 0, тем лучше
     * текущее положение для овцы, чем ближе оценка к 100, тем лучше текущее положение для волков.
     * Для нахождения оценки проводиться поиск в глубину, чтобы найти наикратчайший путь овцы к одной из
     * победных клеток, при бездействии волков, в случае если такой путь существует функция возвращает длину
     * данного пути. Если же такого пути нет функция возвращает значение 100 - количество клеток, до которых
     * овца в принципе может дойти.
      */
    public int getHeuristic() {
        Set<Cell> visitedCells = new HashSet<>();
        Set<Cell> candidates = new HashSet<>();
        visitedCells.add(board.sheepCell);
        candidates.add(board.sheepCell);
        int heuristic = 0;
        while (!candidates.isEmpty()) {
            heuristic++;
            Set<Cell> newCandidates = new HashSet<>();
            candidates.forEach(cell -> board.getAvaliableSteps(cell, true).forEach(avaliableStep -> {
                if (!visitedCells.contains(avaliableStep)) newCandidates.add(avaliableStep);
            }));
            for (Cell cell : newCandidates) {
                 if (cell.isItSheepWinCell()) return heuristic;
            }
            visitedCells.addAll(newCandidates);
            candidates = new HashSet<>(newCandidates);
        }
        return 100 - visitedCells.size();
    }

    public int runMinMax(boolean isItSheep, int recursiveLevel, int alpha, int beta) {
        int curHeuristic;
        Cell bestStep = new Cell(0, 0);
        Cell bestStepFor = new Cell(0, 0);
        if (recursiveLevel >= level) {
            return getHeuristic();
        }
        int bestHeuristic = isItSheep ? 100 : 0;
        if (isItSheep) {
            for (Cell newCell : board.getAvaliableSteps(board.sheepCell, true)) {
                Cell oldCell = board.sheepCell;
                board.step(oldCell, newCell);
                curHeuristic = runMinMax(!isItSheep, recursiveLevel + 1, alpha, beta);
                if (curHeuristic <= bestHeuristic) {
                    if (curHeuristic < bestHeuristic || newCell.i > bestStep.i) {
                        bestHeuristic = curHeuristic;
                        bestStep = newCell;
                        bestStepFor = oldCell;
                    }
                }
                board.step(newCell, oldCell);
                beta = Math.min(curHeuristic, beta);
                if (beta < alpha) break;
            }
        } else {
            Set<Cell> wolfsPositionsCopy = new HashSet<>(board.wolfsCells);
            for (Cell oldCell : wolfsPositionsCopy) {
                for (Cell newCell : board.getAvaliableSteps(oldCell, false)) {
                    board.step(oldCell, newCell);
                    curHeuristic = runMinMax(!isItSheep, recursiveLevel + 1, alpha, beta);
                    if (curHeuristic > bestHeuristic) {
                        bestHeuristic = curHeuristic;
                        bestStep = newCell;
                        bestStepFor = oldCell;
                    }
                    board.step(newCell, oldCell);
                    alpha = Math.max(curHeuristic, alpha);
                    if (beta < alpha) break;
                }
            }
        }
        if (recursiveLevel == 0) {
            board.step(bestStepFor, bestStep);
        }
        return bestHeuristic;
    }
}