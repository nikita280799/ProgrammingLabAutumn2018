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

    public int getHeuristic() {
        Set<Cell> visitedCells = new HashSet<>();
        Set<Cell> candidates = new HashSet<>();
        visitedCells.add(board.sheepCell);
        candidates.add(board.sheepCell);
        int value = 0;
        while (!candidates.isEmpty()) {
            value++;
            Set<Cell> newCandidates = new HashSet<>();
            candidates.forEach(p -> board.getAvaliableSteps(p, true).forEach(p1 -> {
                if (!visitedCells.contains(p1)) newCandidates.add(p1);
            }));
            for (Cell cell : newCandidates) {
                 if (cell.isItSheepWinCell()) return value;
            }
            visitedCells.addAll(newCandidates);
            candidates = new HashSet<>(newCandidates);
        }
        if (visitedCells.size() > 4) return 100 - visitedCells.size();
        return 100;
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