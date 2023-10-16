package edu.hw2.task3;

public class ConnectionException extends RuntimeException {
    public ConnectionException(String s) {
        super(s);
    }

    public ConnectionException(String s, ConnectionException e) {
        super(s, e);
    }
}
