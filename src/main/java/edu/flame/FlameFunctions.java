package edu.flame;

import java.awt.Color;
import java.util.List;
import java.util.Map;

public final class FlameFunctions {
    public final List<FlameFunction> functionList;

    public final List<Double> probabilities;

    public final Variations variations;

    private final static List<FlameFunction> DEFAULT_LIST = List.of(
        new FlameFunction(new Color(150, 250, 127), 3.0, -1.5, 0.25, -0.5, 1.75, 0.5),
        new FlameFunction(new Color(150, 10, 250), 5.5, 2.0, -1.5, 1.25, 3.1, -1.0),
        new FlameFunction(new Color(160, 200, 127), 1.25, -6.75, 0.5, 2.55, -2.5, -1.2),
        new FlameFunction(new Color(250, 40, 40), 10.25, 0.75, 1.2, 3.2, 1.5, -1.2)
    );

    private final static List<Double> DEFAULT_PROBABILITIES = List.of(0.3, 0.2, 0.3, 0.2);

    public FlameFunctions(List<Map<String, Object>> parameters, List<Double> coefficients) {
        functionList = parameters.stream().map(kv -> {
            double[] arr = (double[]) kv.get("affine");
            return new FlameFunction(
                (Color) kv.get("color"),
                arr[0],
                arr[1],
                arr[2],
                arr[1 + 2],
                arr[2 + 2],
                arr[arr.length - 1]
            );
        }).toList();

        final String probabilityKey = "probability";
        double total = parameters.stream().mapToDouble(kv -> (double) kv.get(probabilityKey)).sum();
        probabilities = parameters.stream().map(kv -> (double) kv.get(probabilityKey) / total).toList();

        variations = new Variations(coefficients);
        FlameFunction.USED_VARIATIONS = variations;
    }

    public FlameFunctions() {
        functionList = DEFAULT_LIST;
        probabilities = DEFAULT_PROBABILITIES;
        variations = new Variations();
    }
}
