package edu.hw2.task4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw2.task4.CallingInfoUtils.callingInfo;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CallingInfoTest {
    @Test
    @DisplayName("Тест")
    void callingInfoTest1() {
        var info = callingInfo();
        assertThat(info.methodName()).isEqualTo("callingInfoTest1");
        assertThat(info.className()).endsWith("CallingInfoTest");
    }
}
