package edu.hw9.task1;

import java.util.ArrayList;
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

    private List<Statistics.Args> getToArgs(List<String> statNames) {
        List<Statistics.Args> list = new ArrayList<>();
        for (var statName : statNames) {
            try {
                var arg = Statistics.Args.valueOf(statName.toUpperCase())
                list.add(arg);
            } catch (IllegalArgumentException ignored) {

            }
        }
        return list;
    }
}
