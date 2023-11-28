package edu.flame;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.function.UnaryOperator;

public class FlameFunction implements UnaryOperator<PointWithColor> {
    final Color color;
    final double[] coeffs;
    final static Variations USED_VARIATIONS = new Variations();

    FlameFunction(Color color, double a, double b, double c, double d, double e, double f) {
        this.color = color;
        double normalizeX = 1.0 / (a + b + c);
        double normalizeY = 1.0 / (d + e + f);
        coeffs = new double[] {normalizeX * a, normalizeX * b, normalizeX * c, normalizeY * d, normalizeY * e,
            normalizeY * f};
    }

    @Override
    @SuppressWarnings("MagicNumber")
    public PointWithColor apply(PointWithColor pointWithColor) {
        final Point2D point2D = pointWithColor.point;
        double resX = 0.0;
        double resY = 0.0;
        double x = point2D.getX();
        double y = point2D.getY();
        final double new_x = coeffs[0] * x + coeffs[1] * y + coeffs[2];
        final double new_y = coeffs[3] * x + coeffs[4] * y + coeffs[5];
        final Point2D new_point = new Point2D.Double(new_x, new_y);
        for (int i = 0; i < USED_VARIATIONS.coefficients.size(); i++) {
            final double w = USED_VARIATIONS.coefficients.get(i);
            final Point2D p = Variations.VARIATIONS.get(i).apply(new_point);
            resX += w * p.getX();
            resY += w * p.getY();
        }
        return new PointWithColor(
            new Point2D.Double(resX, resY),
            getNewColor(pointWithColor.color)
        );
    }

    private Color getNewColor(Color color) {
        return new Color(
            (this.color.getRed() + color.getRed()) / 2,
            (this.color.getGreen() + color.getGreen()) / 2,
            (this.color.getBlue() + color.getBlue()) / 2
        );
    }
}
