package edu.hw10.task2;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.Nullable;

public class CacheProxy implements InvocationHandler {

    private final Object target;
    private final Map<Integer, Object> localStorage;

    public CacheProxy(Object target) {
        this.target = target;
        this.localStorage = new HashMap<>();
    }

    public static <T> T create(T target, Class<? super T> targetClass) {
        return (T) Proxy.newProxyInstance(
            targetClass.getClassLoader(),
            new Class<?>[] {targetClass},
            new CacheProxy(target)
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        var cacheAnnotation = method.getAnnotation(Cache.class);
        try {
            if (cacheAnnotation == null) {
                return method.invoke(target, args);
            }
            var key = new InputContainer(method.getName(), Arrays.toString(args)).hashCode();
            final Path path = Path.of("temp", String.valueOf(key) + ".txt");
            if (localStorage.containsKey(key)) {
                return localStorage.get(key);
            }
            return calcObject(method, args, cacheAnnotation, key, path);
        } catch (IOException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable private Object calcObject(Method method, Object[] args, Cache cacheAnnotation, int key, Path path)
        throws IOException, IllegalAccessException, InvocationTargetException {
        Object result;
        if (cacheAnnotation.persist() && Files.exists(path)) {
            result = convertToObject(Files.readString(path), method.getReturnType());
        } else {
            result = method.invoke(target, args);
        }
        localStorage.put(key, result);
        if (cacheAnnotation.persist() && !Files.exists(path)) {
            Files.writeString(path, String.valueOf(result), StandardOpenOption.CREATE_NEW);
        }
        return result;
    }

    private static Object convertToObject(String data, Class<?> type) {
        return switch (type.getName()) {
            case "java.lang.String", "java.lang.Object" -> data;
            case "java.lang.Integer", "int" -> Integer.valueOf(data);
            case "java.lang.Long", "long" -> Long.valueOf(data);
            case "java.lang.Boolean", "boolean" -> Boolean.valueOf(data);
            default -> null;
        };
    }
}
