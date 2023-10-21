package edu.maze;

public final class Maze {
    final int height;
    final int width;
    private final Cell[][] grid;

    public Maze(int height, int width, Cell[][] grid) {
        this.height = height;
        this.width = width;
        this.grid = grid;
    }

    public Cell getCell(int i, int j) {
        return grid[i][j];
    }

    public Cell getCell(Coordinate coordinate) {
        return grid[coordinate.row()][coordinate.col()];
    }

    public boolean coordinateIsValid(Coordinate coordinate) {
        if (coordinate == null) {
            return false;
        }
        int i = coordinate.row();
        int j = coordinate.col();
        return 0 <= i && i < this.height
            && 0 <= j && j < this.width;
    }
}
