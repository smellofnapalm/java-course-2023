package edu.hw11.task1;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class HelloCreatorTest {
    @Test
    @DisplayName("Тест создания класса, у которого есть нужный метод toString()")
    void helloCreatorTest1()
        throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        var helloClass = HelloCreator.createClass();
        Method method = helloClass.getMethod("toString");
        Object instance = helloClass.getDeclaredConstructor().newInstance();
        String result = (String) method.invoke(instance);
        assertThat(result).isEqualTo("Hello, ByteBuddy!");
        assertThat(instance.toString()).isEqualTo("Hello, ByteBuddy!");
    }
}
