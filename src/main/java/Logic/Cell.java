package Logic;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Cell {
    public int i;

    public int j;

    @Override
    public String toString() {
        return "Cell{" +
                "i=" + i +
                ", j=" + j +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return i == cell.i &&
                j == cell.j;
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, j);
    }

    public Cell(int i, int j) {
        this.i = i;
        this.j = j;
    }

    Set<Cell> getAvaliableSteps(boolean isItSheep) {
        Set<Cell> result = new HashSet<>();
        for (int di = -1; di <= 1; di += 2) {
            for (int dj = -1; dj <= 1; dj += 2) {
                Cell cell = new Cell(i + di, j + dj);
                if (isItCorrectStep(cell, isItSheep)) result.add(cell);
            }
        }
        return result;
    }

    boolean isItCorrectStep(Cell newCell, boolean isItSheep) {
        if (isOutOfBoard(newCell)) return false;
        if (isItSheep) return isItCorrectStepForSheep(newCell);
        else return isItCorrectStepForWolf(newCell);
    }

    private boolean isItCorrectStepForSheep(Cell newCell) {
        return ((Math.abs(newCell.i - i) == 1 && Math.abs(newCell.j - j) == 1));
    }

    private boolean isItCorrectStepForWolf(Cell newCell) {
        return (newCell.i - i == -1 && Math.abs(newCell.j - j) == 1);
    }

    private static boolean isOutOfBoard(Cell cell) {
        return (cell.i > 7 || cell.i < 0 || cell.j > 7 || cell.j < 0);
    }

    boolean isItSheepWinCell() {
        return (i == 7);
    }
}