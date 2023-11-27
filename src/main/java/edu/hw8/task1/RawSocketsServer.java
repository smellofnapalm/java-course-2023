package edu.hw8.task1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class RawSocketsServer {

    public RawSocketsServer() {
    }

    private final int PORT = 8080;
    private volatile boolean flag = true;
    private final int maxThreads = Runtime.getRuntime().availableProcessors();
    final ExecutorService service = Executors.newFixedThreadPool(maxThreads);

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void runServer() {
        try (ServerSocket ss = new ServerSocket(PORT)) {
            while (flag) {
                Socket socket = ss.accept();
                service.execute(new SocketProcessor(socket));
            }
        } catch (IOException e) {
            service.shutdown();
        }
        service.shutdown();
    }

    private static final class SocketProcessor implements Runnable {

        private final Socket socket;
        private final InputStream is;
        private final OutputStream os;

        private SocketProcessor(Socket socket) throws IOException {
            this.socket = socket;
            this.is = socket.getInputStream();
            this.os = socket.getOutputStream();
        }

        public void run() {
            final String response = readInput();
            writeResponse(response);
        }

        private void writeResponse(String key) throws RuntimeException {
            String response = CitationData.getAnswer(key);
            System.err.println(response);
            try {
                var writer = new ObjectOutputStream(os);
                writer.writeObject(response);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        private String readInput() throws RuntimeException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            try {
                String input = reader.readLine();
                System.err.println(input);
                return input;
            } catch (IOException e) {
                throw new RuntimeException("Не удалось считать данные от пользователя!", e);
            }
        }
    }

}
