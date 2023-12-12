package edu.hw11.task3;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.DynamicType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DynamicFibTest {
    @Test
    @DisplayName("Создание класса с функцией long fib(int)")
    void dynamicFibTest1() {
        var dynamicClass =
            new ByteBuddy().subclass(Object.class)
                .defineMethod("fib", long.class, Visibility.PUBLIC);
    }
}
