package edu.hw11.task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.lang.reflect.InvocationTargetException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DynamicModifierTest {
    @Test
    @DisplayName("Проверка подмены метода суммы на произведение")
    void dynamicModifierTest1()
        throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        var method = ArithmeticUtils.class.getMethod("sum", int.class, int.class);
        var sub =
            DynamicModifier.substitute(ArithmeticUtils.class,
                ArithmeticUtils.class.getMethod("sum", int.class, int.class), BrokenArithmeticUtils.class
            );
        int a = 5;
        int b = 13;
        int res = sub.sum(a, b);
        assertThat(res).isEqualTo(a * b);
    }
}
