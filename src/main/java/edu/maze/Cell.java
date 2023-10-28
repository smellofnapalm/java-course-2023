package edu.maze;

public final class Cell {
    public final int row;
    public final int col;
    private boolean rightWall;
    private boolean downWall;

    public Cell(int row, int col, boolean rightWall, boolean downWall) {
        this.row = row;
        this.col = col;
        this.rightWall = rightWall;
        this.downWall = downWall;
    }

    public void setRightWall(boolean flag) {
        this.rightWall = flag;
    }

    public void setDownWall(boolean flag) {
        this.downWall = flag;
    }

    public boolean getDownWall() {
        return downWall;
    }

    public boolean getRightWall() {
        return rightWall;
    }

}
