package edu.hw6;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw6.ScanPorts.getAllBusyPorts;
import static edu.hw6.ScanPorts.getInfoAboutAllBusyPorts;
import static edu.hw6.ScanPorts.prettyPrint;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ScanPortsTest {
    @Test
    @DisplayName("Проверка -- сколько занятых портов")
    void scanPortsTest1() {
        int[] ports = getAllBusyPorts();
        assertThat(ports).isNotEmpty();
    }

    @Test
    @DisplayName("Тест информации о занятых портах")
    void scanPortsTest2() {
        List<String> info = getInfoAboutAllBusyPorts();
        assertThat(info).asList().isNotEmpty();
        prettyPrint(info);
    }

    @Test
    @DisplayName("Тест ручного занятия порта 6666")
    void scanPortsTest3() throws IOException {
        var s6666 = new ServerSocket(6666);
        Thread t = new Thread(() -> {
            try {
                s6666.accept();
            } catch (Exception ignored) {
            }
        });
        t.start();
        List<String> info = getInfoAboutAllBusyPorts();
        assertThat(info).asList().contains("TCP 6666 ");
        prettyPrint(info);
        s6666.close();
    }

    @Test
    @DisplayName("Тест ручного занятия порта 6666 на UDP")
    void scanPortsTest4() throws IOException, InterruptedException {
        var s6666 = new DatagramSocket(6666);
        Thread t = new Thread(() -> {
            try {
                s6666.receive(new DatagramPacket(new byte[] {1, 2, 3}, 3));
            } catch (Exception ignored) {
            }
        });
        t.start();
        Thread.sleep(100);
        List<String> info = getInfoAboutAllBusyPorts();
        assertThat(info).asList().contains("UDP 6666 ");
        prettyPrint(info);
        t.join(100);
        s6666.close();
    }
}
