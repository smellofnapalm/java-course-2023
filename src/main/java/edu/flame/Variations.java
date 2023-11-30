package edu.flame;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.function.UnaryOperator;

public final class Variations {
    public final static List<UnaryOperator<Point2D>> VARIATIONS = List.of(
        point2D -> point2D,
        point2D -> new Point2D.Double(Math.sin(point2D.getX()), Math.sin(point2D.getY())),
        point2D -> {
            double x = point2D.getX();
            double y = point2D.getY();
            double dist = x * x + y * y;
            return new Point2D.Double(x / dist, y / dist);
        },
        point2D -> {
            double x = point2D.getX();
            double y = point2D.getY();
            double dist = x * x + y * y;
            return new Point2D.Double(Math.atan2(y, x) / Math.PI, Math.sqrt(dist) - 1);
        }
    );

    public final List<Double> coefficients;
    private final static List<Double> DEFAULT_COEFFICIENTS = List.of(
        1.6 / 6.4,
        2.0 / 6.4,
        1.3 / 6.4,
        1.5 / 6.4
    );

    Variations(List<Double> coeffs) {
        coefficients = coeffs;
    }

    Variations() {
        coefficients = DEFAULT_COEFFICIENTS;
    }

    private List<Double> normalizeCoefficients(List<Double> coeffs) {
        double total = coeffs.stream().mapToDouble(x -> x).sum();
        return coeffs.stream().map(w -> w / total).toList();
    }
}
