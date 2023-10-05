package edu.hw1;

import java.util.Arrays;

public final class Task3 {
    public static boolean isNestable(long[] arr1, long[] arr2) {
        // Будем считать, что пустой массив можно вложить в любой (даже пустой)
        // Если первый не пустой, а второй пустой, то возвращаем false
        // Считаем, что null и пустой массив -- одно и то же
        if (arr1 == null || arr1.length == 0) {
            return true;
        } else if (arr2 == null || arr2.length == 0) {
            return false;
        }
        long min1 = Arrays.stream(arr1).min().getAsLong();
        long max1 = Arrays.stream(arr1).max().getAsLong();
        long min2 = Arrays.stream(arr2).min().getAsLong();
        long max2 = Arrays.stream(arr2).max().getAsLong();
        return min1 > min2 && max1 < max2;
    }

    private Task3() {
    }
}
