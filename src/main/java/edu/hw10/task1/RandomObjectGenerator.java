package edu.hw10.task1;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class RandomObjectGenerator {
    static <T> T nextObject(Class<T> className, String methodName) {
        return null;
    }

    static <T> T nextObjectConstructor(Class<T> className, Class<?>[] paramTypes) {
        try {
            var constructor = className.getDeclaredConstructor(paramTypes);
            var params = Arrays.stream(paramTypes).map(RandomObjectGenerator::constructRandom).toArray();
            return constructor.newInstance(params);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    static <T> T nextRecordConstructor(Class<T> className) {
        try {
            var constructor = className.getDeclaredConstructors()[0];
            var params1 = className.getRecordComponents();
            var params = Arrays.stream(params1)
                .map(c -> RandomObjectGenerator.constructRandom(c.getAnnotatedType())).toArray();
            return (T) constructor.newInstance(params);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    static Object constructRandom(Class<?> className) {
        String name = className.getName();
        var min = className.getDeclaredAnnotation(Min.class);
        var max = className.getDeclaredAnnotation(Max.class);
        return switch (name) {
            case ("java.lang.String") -> constructRandomString();
            case ("java.lang.Integer"), ("int") -> {
                boolean flag = min != null && max != null;
                if (!flag) {
                    yield ThreadLocalRandom.current().nextInt();
                } else {
                    yield ThreadLocalRandom.current().nextInt(min.value(), max.value());
                }
            }
            case ("java.lang.Double"), ("double") -> ThreadLocalRandom.current().nextDouble();
            default -> nextObjectConstructor(className, className.getConstructors()[0].getParameterTypes());
        };
    }

    private static String constructRandomString() {
        final int lower = 1;
        final int upper = 10;
        final char[] alphabet = new char[] {'a', 'b', 'c', 'd', 'e', 'f'};
        int size = ThreadLocalRandom.current().nextInt(lower, upper);
        final char[] str = new char[size];
        for (int i = 0; i < size; i++) {
            str[i] = alphabet[ThreadLocalRandom.current().nextInt(alphabet.length)];
        }
        return String.valueOf(str);
    }
}
