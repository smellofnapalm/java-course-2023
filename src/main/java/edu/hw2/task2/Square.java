package edu.hw2.task2;

public class Square extends Rectangle {
    private final int side;

    Square(int side) {
        super(side, side);
        this.side = side;
    }

    Square() {
        side = 0;
    }

    @Override
    public Rectangle setWidth(int width) {
        return new Rectangle(this.side, width);
    }

    @Override
    public Rectangle setHeight(int height) {
        return new Rectangle(height, this.side);
    }

    public Square setSide(int side) {
        return new Square(side);
    }
}
