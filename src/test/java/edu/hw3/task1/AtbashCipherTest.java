package edu.hw3.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw3.task1.AtbashCipher.atbash;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AtbashCipherTest {
    @Test
    @DisplayName("Проверка на латинском слове в разных регистрах")
    void atbashTest1() {
        String s = "Hello World!";

        var ciphered = atbash(s);

        assertThat(ciphered).isEqualTo("Svool Dliow!");
    }

    @Test
    @DisplayName("Тест с пустой строкой")
    void atbashTest2() {
        String s = "";

        var ciphered = atbash(s);

        assertThat(ciphered).isEqualTo("");
    }

    @Test
    @DisplayName("Тест с null строкой")
    void atbashTest3() {
        String s = null;

        var ciphered = atbash(s);

        assertThat(ciphered).isEqualTo(null);
    }

    @Test
    @DisplayName("Тест с ивритом")
    void atbashTest4() {
        String s = "שלום עולם!";

        var ciphered = atbash(s);

        assertThat(ciphered).isEqualTo(s);
    }

    @Test
    @DisplayName("Тест с цифрами")
    void atbashTest5() {
        String s = "Sum-41";

        var ciphered = atbash(s);

        assertThat(ciphered).isEqualTo("Hfn-41");
    }
}
