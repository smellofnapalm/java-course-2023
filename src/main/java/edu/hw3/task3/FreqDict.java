package edu.hw3.task3;

import java.util.HashMap;
import java.util.Map;

public final class FreqDict {
    public static <T> Map<T, Integer> freqDict(T[] arr) {
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

    private FreqDict() {
    }
}
