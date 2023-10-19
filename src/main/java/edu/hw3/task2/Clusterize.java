package edu.hw3.task2;

import java.util.Vector;

public final class Clusterize {
    static boolean isBracketSequence(String s) {
        int score = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                score--;
            } else if (c == ')') {
                score++;
            }
            if (score > 0) {
                return false;
            }
        }
        return score == 0;
    }

    public static String[] clusterize(String s) throws IllegalArgumentException {
        if (s == null || !s.matches("[()]+") || !isBracketSequence(s)) {
            throw new IllegalArgumentException("Строка не является (правильной) скобочной последовательностью");
        }
        Vector<String> res = new Vector<String>();
        int score = 0;
        int last = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                score--;
            } else if (c == ')') {
                score++;
            }
            if (score == 0) {
                res.add(s.substring(last, i + 1));
                last = i + 1;
            }
        }
        return res.toArray(new String[0]);
    }

    private Clusterize() {
    }
}
