package edu.hw8.task1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class RemoteClient {
    RemoteClient() {
    }

    private Socket socket;

    private static final int PORT = 8080;

    public void sendRequest(String send) {
        try {
            socket = new Socket("127.0.0.1", PORT);
            var os = socket.getOutputStream();
            final byte[] input = send.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
            os.write('\n');
            os.flush();
        } catch (IOException e) {
            throw new RuntimeException("Не получилось отправить запрос серверу!", e);
        }
    }

    public String getAnswer() {
        try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
            String s = (String) ois.readObject();
            return s;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Не удалось получить ответ от сервера!", e);
        }
    }
}
