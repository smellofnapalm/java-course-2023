package edu.hw9.task1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StatisticsCollector {
    final Map<double[], List<Statistics>> listOfData = new ConcurrentHashMap<>();

    StatisticsCollector() {
    }

    void push(List<String> statNames, double[] data) {
        listOfData.put(data, new StatisticsCalculator(getToArgs(statNames), data).calcStatistics());
    }

    StatisticsMap get(double[] data) {
        var stats = listOfData.getOrDefault(data, null);
        if (stats == null) {
            throw new IllegalArgumentException("Данных нет в списке!");
        }
        Map<String, Double> map = new HashMap<>();
        for (var stat : stats) {
            map.put(stat.statName.name().toLowerCase(), stat.statValue);
        }
        return new StatisticsMap(
            map.getOrDefault("max", Double.MAX_VALUE),
            map.getOrDefault("min", Double.MAX_VALUE),
            map.getOrDefault("mean", Double.MAX_VALUE),
            map.getOrDefault("sum", Double.MAX_VALUE)
        );
    }

    private List<Statistics.Args> getToArgs(List<String> statNames) {
        List<Statistics.Args> list = new ArrayList<>();
        for (var statName : statNames) {
            try {
                var arg = Statistics.Args.valueOf(statName.toUpperCase());
                list.add(arg);
            } catch (IllegalArgumentException ignored) {

            }
        }
        return list;
    }
}
