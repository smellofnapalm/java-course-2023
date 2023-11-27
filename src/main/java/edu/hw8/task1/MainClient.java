package edu.hw8.task1;

public class MainClient {
    public static void main(String[] args) throws InterruptedException {
        final var server = new RawSocketsServer();
        Thread serverThread = new Thread(server::runServer);
        Thread clientThread = new Thread(() -> {
            var client = new RemoteClient();
            client.sendRequest("интеллект");
            System.err.println(client.getAnswer());
            client.sendRequest("вкус");
            System.err.println(client.getAnswer());
            server.setFlag(false);
        });

        serverThread.setDaemon(true);
        final int delay = 50;
        serverThread.start();
        Thread.sleep(delay);
        clientThread.start();
        clientThread.join();
        serverThread.interrupt();
    }

    private MainClient() {
    }
}
