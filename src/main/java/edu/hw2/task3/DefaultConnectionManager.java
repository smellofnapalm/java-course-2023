package edu.hw2.task3;

public class DefaultConnectionManager implements ConnectionManager {
    static final double PROBABILITY = 0.4;

    @Override
    public Connection getConnection() {
        boolean getsFaultyConnection = ProbabilityGenerator.generate(PROBABILITY);
        if (getsFaultyConnection) {
            return new FaultyConnection();
        }
        return new StableConnection();
    }
}
