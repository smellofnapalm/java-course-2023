package edu.hw9.task1;

public class Statistics {
    final Args statName;
    final double statValue;

    Statistics(Statistics.Args statName, double value) {
        this.statName = statName;
        this.statValue = value;
    }

    enum Args {
        MEAN,
        MAX,
        MIN,
        SUM
    }
}
