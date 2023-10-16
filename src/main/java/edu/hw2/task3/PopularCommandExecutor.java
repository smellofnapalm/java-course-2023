package edu.hw2.task3;

public final class PopularCommandExecutor {
    private final ConnectionManager manager;
    private final int maxAttempts;

    public PopularCommandExecutor(ConnectionManager manager, int maxAttempts) throws IllegalArgumentException {
        if (maxAttempts <= 0) {
            throw new IllegalArgumentException("maxAttempts > 0");
        }
        this.manager = manager;
        this.maxAttempts = maxAttempts;
    }

    public void updatePackages() {
        tryExecute("apt update && apt upgrade -y");
    }

    void tryExecute(String command) {
        try (var connection = manager.getConnection()) {
            for (int i = 0; i < maxAttempts; i++) {
                try {
                    connection.execute(command);
                } catch (ConnectionException e) {
                    if (i == maxAttempts - 1) {
                        throw e;
                    }
                    continue;
                }
                break;
            }
        } catch (ConnectionException ex) {
            throw new ConnectionException(command + " не была успешно выполнена после "
                + maxAttempts + " попыток", ex);
        }

    }
}
