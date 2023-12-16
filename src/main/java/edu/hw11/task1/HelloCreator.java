package edu.hw11.task1;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

public final class HelloCreator {
    public static Class<?> createClass() {
        Class<?> dynamicType = new ByteBuddy()
            .subclass(Object.class)
            .method(ElementMatchers.named("toString"))
            .intercept(FixedValue.value("Hello, ByteBuddy!"))
            .make()
            .load(HelloCreator.class.getClassLoader())
            .getLoaded();
        return dynamicType;
    }

    private HelloCreator() {
    }
}
