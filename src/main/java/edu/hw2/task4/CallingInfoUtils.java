package edu.hw2.task4;

public class CallingInfoUtils {
    public static CallingInfo callingInfo() {
        RuntimeException e = new RuntimeException();
        var stack = e.getStackTrace();
        for (int i = 0; i < stack.length; i++) {
            if (stack[i].getMethodName().equals("callingInfo")
                && stack[i].getClassName().endsWith("CallingInfoUtils")) {
                return new CallingInfo(stack[i + 1].getClassName(), stack[i + 1].getMethodName());
            }
        }
        throw new RuntimeException("Функцию никто не вызвал");
    }

    private CallingInfoUtils() {
    }
}
