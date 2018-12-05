package Logic;

import java.util.HashSet;
import java.util.Set;

public class Board {

    public Set<Cell> wolfsCells = new HashSet<>();

    public Cell sheepCell;

    public Board() {
        sheepCell = new Cell(0, 3);
        wolfsCells.add(new Cell(7, 0));
        wolfsCells.add(new Cell(7, 4));
        wolfsCells.add(new Cell(7, 6));
        wolfsCells.add(new Cell(7, 2));
    }

    public void setSheep(Cell cell) {
        sheepCell = cell;
    }

    public void setWolf(Cell cell) {
        wolfsCells.add(cell);
    }

    public void clear() {
        wolfsCells.clear();
        sheepCell = null;
    }

    public void step(Cell start, Cell end) {
        if (start.equals(sheepCell)) sheepCell = end;
        else {
            wolfsCells.remove(start);
            wolfsCells.add(end);
        }
    }

    public boolean isItEmptyCell(Cell cell) {
        return !(isItSheepPosition(cell) || isItWolfPosition(cell));
    }

    public boolean isItSheepPosition(Cell pos) {
        return sheepCell.equals(pos);
    }

    public boolean isItWolfPosition(Cell pos) {
        return wolfsCells.contains(pos);
    }

    public boolean isSheepWin() {
        return sheepCell.isItSheepWinCell();
    }

    public boolean isWolfWin() {
        for (Cell p : sheepCell.getAvaliableSteps(true)) {
            if (!wolfsCells.contains(p)) return false;
        }
        return true;
    }

    public Set<Cell> getAvaliableSteps(Cell start, boolean isItSheep) {
        Set<Cell> result = new HashSet<>();
        start.getAvaliableSteps(isItSheep).forEach(cell -> {
            if (!sheepCell.equals(cell) && !wolfsCells.contains(cell)) result.add(cell);
        });
        return result;
    }

    public boolean isItCorrectStep(Cell start, Cell end) {
        return !sheepCell.equals(end) && !wolfsCells.contains(end) && start.isItCorrectStep(end, isItSheepPosition(start));
    }
}