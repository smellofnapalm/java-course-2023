package edu.hw11.task2;

import java.lang.reflect.Method;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperMethod;
import net.bytebuddy.implementation.bind.annotation.This;

public final class Interceptor {
    @RuntimeType
    public static Object intercept(
        @This Object self,
        @Origin Method method,
        @AllArguments Object[] args,
        @SuperMethod Method superMethod
    ) throws Throwable {
        if (method.getName().equals("sum")) {
            return (int) args[0] * (int) args[1];
        }
        return method.invoke(self, args);
    }

    private Interceptor() {
    }
}
