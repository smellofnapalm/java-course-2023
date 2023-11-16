package edu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.math.BigInteger;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ParallelFactorialTest {
    @Test
    @DisplayName("Тест на факториал от отрицательного числа")
    void parallelFactorialTest1() {
        int n = -1;
        var thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> new ParallelFactorial(n).compute());
        assertThat(thrown).hasMessage("Факториал вычисляется только от натурального числа!");
    }

    @Test
    @DisplayName("Тест на факториал от нуля")
    void parallelFactorialTest2() {
        var factComputer = new ParallelFactorial(0);
        assertThat(factComputer.compute()).isEqualTo(BigInteger.ONE);
    }

    @Test
    @DisplayName("Тест на факториал от 150")
    void parallelFactorialTest3() {
        var factComputer = new ParallelFactorial(150);
        assertThat(factComputer.compute().toString()).isEqualTo(
            "5713383956445854590478932865261054003189553578601126418254837583317982912484539839312657448"
                + "867531114537710787874685420416266625019868450446635594919592206657494259209573577892932535729"
                + "0444962472405416790722118445437122269675520000000000000000000000000000000000000");
    }
}
