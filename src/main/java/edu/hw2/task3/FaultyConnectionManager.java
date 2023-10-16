package edu.hw2.task3;

public class FaultyConnectionManager implements ConnectionManager {
    @Override
    public FaultyConnection getConnection() {
        return new FaultyConnection();
    }
}
