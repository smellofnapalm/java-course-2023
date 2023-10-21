package edu.maze;

public final class Cell {
    final int row;
    final int col;
    private boolean rightWall;
    private boolean downWall;

    Cell(int row, int col, boolean rightWall, boolean downWall) {
        this.row = row;
        this.col = col;
        this.rightWall = rightWall;
        this.downWall = downWall;
    }

    void setRightWall(boolean flag) {
        this.rightWall = flag;
    }

    void setDownWall(boolean flag) {
        this.downWall = flag;
    }

    boolean getDownWall() {
        return downWall;
    }

    boolean getRightWall() {
        return rightWall;
    }

}
