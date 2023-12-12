package edu.hw11.task2;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;
import static net.bytebuddy.matcher.ElementMatchers.named;

public class DynamicModifier {
    public static <T> T substitute(Class<T> className, Method method, Class<?> interceptingClass)
        throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return new ByteBuddy()
            .subclass(className)
            .method(named(method.getName()))
            .intercept(MethodDelegation.to(interceptingClass))
            .make()
            .load(className.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
            .getLoaded()
            .getDeclaredConstructor()
            .newInstance();
    }
}
