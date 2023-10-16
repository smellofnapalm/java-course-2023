package edu.hw2.task3;

public class FaultyConnection implements Connection {
    private final double probability;
    final static double DEFAULT_PROBABILITY = 0.4;

    @Override
    public void execute(String command) throws ConnectionException {
        boolean throwsException = ProbabilityGenerator.generate(probability);
        if (throwsException) {
            throw new ConnectionException("Faulty Connection не удалось выполнить команду " + command);
        }
    }

    @Override
    public void close() {

    }

    FaultyConnection(double probability) {
        this.probability = probability;
    }

    FaultyConnection() {
        this.probability = DEFAULT_PROBABILITY;
    }

}
