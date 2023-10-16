package edu.hw3.task3;

import java.util.HashMap;
import java.util.Map;

public final class WordLen {
    public static <T> Map<T, Integer> wordLen(T[] arr) {
        var dict = new HashMap<T, Integer>();
        if (arr == null) {
            return dict;
        }
        for (T el : arr) {
            if (dict.containsKey(el)) {
                dict.replace(el, dict.get(el) + 1);
            } else {
                dict.put(el, 1);
            }
        }
        return dict;
    }

    private WordLen() {
    }
}
