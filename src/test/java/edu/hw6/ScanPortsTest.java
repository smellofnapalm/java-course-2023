package edu.hw6;

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
}
