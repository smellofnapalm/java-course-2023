package edu.hw2.task1;

import edu.hw2.task1.Expr;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static java.lang.Math.pow;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ExprTest {
    @Test
    @DisplayName("Тест на все операции")
    void exprTest1() {
        var c1 = new Expr.Constant(1.0);
        var c2 = new Expr.Constant(2.5);
        var add1 = new Expr.Addition(c1, c2);
        var mul1 = new Expr.Multiplication(add1, c2);
        var exp1 = new Expr.Exponent(c2, mul1);
        var neg1 = new Expr.Negate(exp1);

        assertThat(c1.evaluate()).isEqualTo(1.0);
        assertThat(add1.evaluate()).isEqualTo(3.5);
        assertThat(mul1.evaluate()).isEqualTo(8.75);
        assertThat(exp1.evaluate()).isEqualTo(pow(2.5, 8.75));
        assertThat(neg1.evaluate()).isEqualTo(-pow(2.5, 8.75));
    }

    @Test
    @DisplayName("Тест на вывод")
    void exprTest2() {
        var c1 = new Expr.Constant(1.0);
        var c2 = new Expr.Constant(2.5);
        var add1 = new Expr.Addition(c1, c2);
        var mul1 = new Expr.Multiplication(add1, c2);
        var exp1 = new Expr.Exponent(c2, mul1);
        var neg1 = new Expr.Negate(exp1);

        assertThat(c1.toString()).isEqualTo("1.0");
        assertThat(add1.toString()).isEqualTo("1.0 + 2.5");
        assertThat(mul1.toString()).isEqualTo("3.5 * 2.5");
        assertThat(exp1.toString()).isEqualTo("2.5 ** 8.75");
        assertThat(neg1.toString()).isEqualTo("(-1) * " + pow(2.5, 8.75));
    }

    @Test
    @DisplayName("Тест на возведение отрицательного числа в степень")
    void exprTest3() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            var base = new Expr.Constant(-1.5);
            var power = new Expr.Constant(2.5);
            var exp = new Expr.Exponent(base, power);
            exp.evaluate();
        });

        Assertions.assertEquals("В вещественную степень можно возводить только положительное число", thrown.getMessage());
    }

    @Test
    @DisplayName("Тест на возведение нуля в отрицательную степень")
    void exprTest4() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            var base = new Expr.Constant(0.0);
            var power = new Expr.Constant(-2.5);
            var exp = new Expr.Exponent(base, power);
            exp.evaluate();
        });

        Assertions.assertEquals("В вещественную степень можно возводить только положительное число", thrown.getMessage());
    }

}
