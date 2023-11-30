package edu.flame;

import java.awt.Color;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Initializer {
    final Path configPath;

    FlameFunctions flameFunctions;
    int height;
    int width;
    int iterations;
    Path pathToFile;
    String format;
    int window;
    double gamma;
    double threshold;

    public Initializer(Path configPath) {
        if (!configPath.toFile().exists()) {
            throw new IllegalArgumentException("Путь к конфигу некорректный!");
        }
        this.configPath = configPath;
        parse();
    }

    private static final Pattern flameFuncPattern =
        Pattern.compile("^FlameFunction\\(RGB\\((.*)\\), AFFINE\\((.*)\\), PROB\\((.*)\\)\\)$");
    private static final List<Pattern> otherPatterns = new ArrayList<>();

    private static final List<String> defaultFormatList = List.of("bmp", "jpg", "jpeg", "png");

    private static final List<String> otherKeyWords = List.of(
        "VariationCoefficients",
        "height",
        "width",
        "iterations",
        "path",
        "format",
        "window",
        "gamma",
        "threshold"
    );

    static {
        final String formattedString = "^%s\\((.*)\\)$";
        for (var keyWord : otherKeyWords) {
            otherPatterns.add(Pattern.compile(formattedString.formatted(keyWord)));
        }
    }

    void parse() {
        List<Map<String, Object>> flameFunctionsParameters = new ArrayList<>();
        List<Double> variationCoefficients = new ArrayList<>();
        List<String> list;
        try {
            list = Files.readAllLines(configPath);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось считать конфиг!", e);
        }
        int n = list.size();
        int i = 0;
        for (; i < n; i++) {
            var matcher = flameFuncPattern.matcher(list.get(i));
            if (matcher.matches()) {
                Map<String, Object> dict = new HashMap<>();
                var colors = Arrays.stream(matcher.group(1).split(", ")).mapToInt(Integer::parseInt).toArray();
                dict.put("color", new Color(colors[0], colors[1], colors[2]));
                dict.put(
                    "affine",
                    Arrays.stream(matcher.group(2).split(", ")).mapToDouble(Double::parseDouble).toArray()
                );
                dict.put("probability", Double.parseDouble(matcher.group(3)));
                flameFunctionsParameters.add(dict);
            } else {
                break;
            }
        }
        var matcher = otherPatterns.get(0).matcher(list.get(i++));
        if (matcher.matches()) {
            variationCoefficients =
                Arrays.stream(matcher.group(1).split(", ")).mapToDouble(Double::parseDouble).boxed().toList();
        }
        matcher = otherPatterns.get(1).matcher(list.get(i++));
        if (matcher.matches()) {
            height = Integer.parseInt(matcher.group(1));
        }
        matcher = otherPatterns.get(2).matcher(list.get(i++));
        if (matcher.matches()) {
            width = Integer.parseInt(matcher.group(1));
        }
        matcher = otherPatterns.get(3).matcher(list.get(i++));
        if (matcher.matches()) {
            iterations = Integer.parseInt(matcher.group(1));
        }
        matcher = otherPatterns.get(4).matcher(list.get(i++));
        if (matcher.matches()) {
            pathToFile = Path.of(matcher.group(1));
        }
        matcher = otherPatterns.get(5).matcher(list.get(i++));
        if (matcher.matches()) {
            format = matcher.group(1);
            if (!defaultFormatList.contains(format)) {
                format = defaultFormatList.get(0);
            }
        }
        matcher = otherPatterns.get(6).matcher(list.get(i++));
        if (matcher.matches()) {
            window = Integer.parseInt(matcher.group(1));
            if (window % 2 == 0) {
                window--;
            }
        }
        matcher = otherPatterns.get(7).matcher(list.get(i++));
        if (matcher.matches()) {
            gamma = Double.parseDouble(matcher.group(1));
        }
        matcher = otherPatterns.get(8).matcher(list.get(i));
        if (matcher.matches()) {
            threshold = Double.parseDouble(matcher.group(1));
        }

        flameFunctions = new FlameFunctions(flameFunctionsParameters, variationCoefficients);
    }
}
