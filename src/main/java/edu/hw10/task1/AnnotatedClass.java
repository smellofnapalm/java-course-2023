package edu.hw10.task1;

import java.lang.annotation.Annotation;

public record AnnotatedClass(Class<?> className, Annotation[] annotations) {
    <T extends java.lang.annotation.Annotation> T find(Class<?> toFind) {
        for (var a : annotations) {
            if (a.annotationType().equals(toFind)) {
                return (T) a;
            }
        }
        return null;
    }
}
