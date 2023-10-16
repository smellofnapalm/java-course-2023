package edu.hw3.task1;

public final class AtbashCipher {
    public static String atbash(String s) {
        if (s == null) {
            return null;
        }

        var chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c >= 'a' && c <= 'z') {
                chars[i] = (char) ('z' - (c - 'a'));
            } else if (c >= 'A' && c <= 'Z') {
                chars[i] = (char) ('Z' - (c - 'A'));
            }
        }
        return new String(chars);

    }

    private AtbashCipher() {
    }
}
