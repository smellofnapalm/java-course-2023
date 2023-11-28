package edu.flame;

import java.awt.Color;
import java.util.List;
import java.util.Map;

public final class FlameFunctions {
    public final List<FlameFunction> functionList;

    public final List<Double> probabilities;

    public final Variations variations;

    public FlameFunctions(List<Map<String, Object>> parameters, List<Double> coefficients) {
        functionList = parameters.stream().map(kv -> {
            double[] arr = (double[]) kv.get("affine");
            return new FlameFunction((Color) kv.get("color"), arr[0], arr[1], arr[2], arr[3], arr[4], arr[5]);
        }).toList();

        double total = parameters.stream().mapToDouble(kv -> (double) kv.get("probability")).sum();
        probabilities = parameters.stream().map(kv -> (double) kv.get("probability") / total).toList();

        variations = new Variations(coefficients);
    }

    public FlameFunctions() {
        functionList = List.of(
            new FlameFunction(new Color(150, 250, 127), 3.0, -1.5, 0.25, -0.5, 1.75, 0.5),
            new FlameFunction(new Color(150, 10, 250), 5.5, 2.0, -1.5, 1.25, 3.1, -1.0),
            new FlameFunction(new Color(160, 200, 127), 1.25, -6.75, 0.5, 2.55, -2.5, -1.2),
            new FlameFunction(new Color(250, 40, 40), 10.25, 0.75, 1.2, 3.2, 1.5, -1.2)
        );
        probabilities = List.of(
            0.3,
            0.2,
            0.3,
            0.2
        );
        variations = new Variations();
    }
}
