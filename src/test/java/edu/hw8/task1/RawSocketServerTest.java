package edu.hw8.task1;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RawSocketServerTest {
    @Test
    @DisplayName("Тест работы с двумя входящими соединениями")
    public void rawSocketServerTest1() throws InterruptedException {
        final RawSocketsServer server = new RawSocketsServer();
        Thread serverThread = new Thread(server::runServer);
        Set<String> answers = new ConcurrentSkipListSet<>();
        Thread clientThread1 = new Thread(() -> {
            var client = new RemoteClient();
            client.sendRequest("интеллект");
            answers.add(client.getAnswer());
            client.sendRequest("вкус");
            answers.add(client.getAnswer());
        });
        Thread clientThread2 = new Thread(() -> {
            var client = new RemoteClient();
            client.sendRequest("личности");
            answers.add(client.getAnswer());
            client.sendRequest("глупый");
            answers.add(client.getAnswer());
        });

        serverThread.start();
        clientThread1.start();
        clientThread2.start();
        clientThread1.join();
        clientThread2.join();

        assertThat(answers.contains("Чем ниже интеллект, тем громче оскорбления")).isTrue();
        assertThat(answers.contains("Не переходи на личности там, где их нет")).isTrue();
    }

    @Test
    @DisplayName("Тест работы с 5 запросами, при наличии у сервера только 3 потоков")
    void rawSocketTest2() {
        final int threadCount = 3;
        final int userCount = 5;
        final RawSocketsServer server = new RawSocketsServer(threadCount);
        Thread serverThread = new Thread(server::runServer);
        List<String> answers = new CopyOnWriteArrayList<>(new String[userCount]);

        var threadList = IntStream.range(0, userCount).mapToObj(i -> new Thread(() -> {
            var client = new RemoteClient();
            client.sendRequest("интеллект");
            var ans = client.getAnswer();
            answers.set(i, ans);
        })).toList();

        serverThread.start();
        threadList.forEach(Thread::start);
        threadList.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        boolean allMatch = answers.stream().allMatch(s -> s.equals("Чем ниже интеллект, тем громче оскорбления"));
        assertThat(allMatch).isTrue();
    }
}
