package edu.hw9.task1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class StatisticsCalculator {
    final List<Statistics> statisticsList = new ArrayList<>();

    final List<Statistics.Args> stats;
    final double[] data;

    StatisticsCalculator(List<Statistics.Args> stats, double[] data) {
        this.stats = stats;
        this.data = data;
    }

    List<Statistics> calcStatistics() {
        List<CalcTask> tasks = new ArrayList<>();
        for (final var stat : stats) {
            var task = new CalcTask(stat);
            task.fork();
            tasks.add(task);
        }
        List<Statistics> resultingStatistics = new ArrayList<>();
        tasks.forEach(task -> resultingStatistics.add(task.join()));
        return resultingStatistics;
    }

    class CalcTask extends RecursiveTask<Statistics> {
        final Statistics.Args statName;

        CalcTask(Statistics.Args statName) {
            this.statName = statName;
        }

        @Override
        protected Statistics compute() {
            return computeStat(statName);
        }

        private Statistics computeStat(Statistics.Args statName) {
            double value = switch (statName) {
                case MAX -> Arrays.stream(data).max().orElse(Double.MAX_VALUE);
                case MIN -> Arrays.stream(data).min().orElse(Double.MAX_VALUE);
                case MEAN -> Arrays.stream(data).average().orElse(Double.MAX_VALUE);
                case SUM -> Arrays.stream(data).sum();
            };
            return new Statistics(statName, value);
        }
    }
}
