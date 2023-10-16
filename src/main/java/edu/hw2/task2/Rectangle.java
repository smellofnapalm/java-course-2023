package edu.hw2.task2;

public class Rectangle {
    private final int width;
    private final int height;

    public Rectangle(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public Rectangle() {
        height = 0;
        width = 0;
    }

    public Rectangle setWidth(int width) {
        return new Rectangle(this.height, width);
    }

    public Rectangle setHeight(int height) {
        return new Rectangle(height, this.width);
    }

    public double area() {
        return width * height;
    }
}

