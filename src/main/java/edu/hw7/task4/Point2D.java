package edu.hw7.task4;

record Point2D(double x, double y) {
    private static final Point2D CENTER = new Point2D(0.5, 0.5);
    private static final double RADIUS_SQUARED = 0.25;

    public boolean inCircle() {
        var dx = x - CENTER.x();
        var dy = y - CENTER.y();
        return dx * dx + dy * dy <= RADIUS_SQUARED;
    }
}
