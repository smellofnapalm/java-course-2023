package edu.hw1;

public final class Task4 {
    public static String fixString(String s) {
        if (s == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(s);
        for (int i = 0; i < s.length() - 1; i += 2) {
            var c1 = s.charAt(i);
            var c2 = s.charAt(i + 1);
            sb.setCharAt(i, c2);
            sb.setCharAt(i + 1, c1);
        }
        return sb.toString();
    }

    private Task4() {
    }
}
