package edu.hw10.task1;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import org.jetbrains.annotations.Nullable;

public final class RandomObjectGenerator {
    private static AnnotatedClass[] combineClassAndAnnotations(Class<?>[] classes, Annotation[][] annotations) {
        int n = classes.length;
        AnnotatedClass[] annotatedClasses = new AnnotatedClass[n];
        for (int i = 0; i < n; i++) {
            annotatedClasses[i] = new AnnotatedClass(classes[i], annotations[i]);
        }
        return annotatedClasses;
    }

    static <T> T nextObject(Class<T> className, String methodName) {
        try {
            Method method = getMethodByName(className, methodName);
            if (method == null) {
                throw new NullPointerException("Такой функции нет у этого класса!");
            }
            var paramTypes = method.getParameterTypes();
            var annotations = method.getParameterAnnotations();
            var annotatedClasses = combineClassAndAnnotations(paramTypes, annotations);
            var params = Arrays.stream(annotatedClasses)
                .map(RandomObjectGenerator::constructRandom)
                .toArray();
            return (T) method.invoke(className, params);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private static Method getMethodByName(Class<?> className, String methodName) {
        var methods = className.getDeclaredMethods();
        for (var m : methods) {
            if (m.getName().equals(methodName)) {
                return m;
            }
        }
        return null;
    }

    static <T> T nextObjectConstructor(Class<T> className, Class<?>[] paramTypes) {
        try {
            var constructor =
                className.getDeclaredConstructor(paramTypes);
            var annotations = constructor.getParameterAnnotations();
            var params = Arrays.stream(combineClassAndAnnotations(paramTypes, annotations))
                .map(RandomObjectGenerator::constructRandom)
                .toArray();
            return constructor.newInstance(params);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    static <T> T nextRecordConstructor(Class<T> className) {
        try {
            var constructor = className.getDeclaredConstructors()[0];
            var recordComponents = className.getRecordComponents();
            int n = recordComponents.length;
            AnnotatedClass[] annotatedClasses = new AnnotatedClass[n];
            for (int i = 0; i < n; i++) {
                annotatedClasses[i] =
                    new AnnotatedClass(recordComponents[i].getType(), recordComponents[i].getAnnotations());
            }
            var params = Arrays.stream(annotatedClasses)
                .map(RandomObjectGenerator::constructRandom).toArray();
            return (T) constructor.newInstance(params);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    static Object constructRandom(AnnotatedClass className) {
        String name = className.className().getName();
        var notNull = className.find(NotNullReflection.class);
        var min = className.find(Min.class);
        var max = className.find(Max.class);
        return getRandomObject(name, (Min) min, (Max) max, (NotNullReflection) notNull);
    }

    @Nullable private static Object getRandomObject(String name, Min min, Max max, NotNullReflection notNull) {
        return switch (name) {
            case ("java.lang.String") -> {
                if (notNull == null) {
                    yield "";
                }
                yield constructRandomString();
            }
            case ("java.lang.Integer"), ("int") -> {
                boolean flag = min != null && max != null;
                if (!flag) {
                    yield ThreadLocalRandom.current().nextInt();
                } else {
                    yield ThreadLocalRandom.current().nextInt(min.value(), max.value());
                }
            }
            case ("java.lang.Double"), ("double") -> ThreadLocalRandom.current().nextDouble();
            default -> null;
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

    private RandomObjectGenerator() {
    }
}
