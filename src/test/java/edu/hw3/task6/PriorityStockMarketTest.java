package edu.hw3.task6;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PriorityStockMarketTest {
    @Test
    @DisplayName("Тест работы с рынком акций")
    void priorityStockMarketTest1() {
        Stock s1 = new Stock(5.5, "Tinkoff");
        Stock s2 = new Stock(1.0, "Yandex");
        PriorityStockMarket market = new PriorityStockMarket();

        market.add(s1);
        market.add(s2);
        assertThat(market.mostValuableStock().companyName).isEqualTo("Tinkoff");
        market.remove(s2);
        market.remove(s2);
        market.remove(s1);
        market.remove(s1);
        assertThat(market.mostValuableStock()).isNull();
    }

    @Test
    @DisplayName("Тест работы с акциями")
    void priorityStockMarketTest2() {
        var thrown = Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> {
                Stock s1 = new Stock(5.5, "Tinkoff");
                Stock s2 = new Stock(1.0, "Yandex");
                s1.setValue(-1.0);
            }
        );
        assertThat(thrown).hasMessage("Стоимость акции не может быть отрицательной!");

    }

    @Test
    @DisplayName("Тест создания акции с отрицательной стоимостью")
    void priorityStockMarketTest3() {
        var thrown = Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> {
                Stock s1 = new Stock(5.5, "Tinkoff");
                Stock s2 = new Stock(-1.0, "Yandex");
            }
        );
        assertThat(thrown).hasMessage("Стоимость акции не может " +
            "быть отрицательна, название компании должно быть валидным");

    }
}
