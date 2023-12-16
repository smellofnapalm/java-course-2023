package edu.hw11.task3;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.jar.asm.Opcodes;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DynamicFibTest {
    static Class<?> dynamicClass;
    static Method method;
    static Object instance;

    @BeforeAll static void setup()
        throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        dynamicClass =
            new ByteBuddy().subclass(Object.class)
                .name("MyFibCalc")
                .defineMethod("fib", long.class, Opcodes.ACC_PUBLIC)
                .withParameter(int.class, "n")
                .intercept(new Implementation.Simple(FibImpl.INSTANCE)).make()
                .load(ClassLoader.getSystemClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded();
        method = dynamicClass.getDeclaredMethod("fib", int.class);
        instance = dynamicClass.getConstructor().newInstance();
    }

    @DisplayName("Создание класса с функцией long fib(int)")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 10, 20})
    void dynamicFibTest1(int n)
        throws InvocationTargetException, IllegalAccessException {

        long result = (long) method.invoke(instance, n);
        long expected = RealFib.fib(n);

        assertThat(result)
            .isEqualTo(expected);
    }
}
