package edu.hw11.task2;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static net.bytebuddy.matcher.ElementMatchers.isDeclaredBy;
import static net.bytebuddy.matcher.ElementMatchers.returns;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DynamicModifierTest {
    @Test
    @DisplayName("Проверка подмены метода суммы на произведение")
    void dynamicModifierTest1()
        throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        int a = 5;
        int b = 13;

        int result = new ByteBuddy()
            .subclass(ArithmeticUtils.class)
            .method(ElementMatchers.named("sum"))
            .intercept(MethodDelegation.to(Interceptor.class))
            .make()
            .load(ArithmeticUtils.class.getClassLoader())
            .getLoaded().getDeclaredConstructor().newInstance().sum(a, b);

        assertThat(result).isEqualTo(a * b);
    }
}
