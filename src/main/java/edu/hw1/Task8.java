package edu.hw1;

import java.util.Vector;
import org.apache.commons.lang3.tuple.Pair;

public final class Task8 {
    @SuppressWarnings("MagicNumber")
    public static boolean knightBoardCapture(int[][] a) {
        // Если ни один конь не расставлен, то, действительно, они не бьют друг друга
        if (a == null) {
            return true;
        }
        // Хочу, чтобы программа работала с произвольным размером поля, не только 8x8
        int n = a.length;
        int m = 0;
        if (n >= 1) {
            m = a[0].length;
        }
        var shifts = new Vector<Pair<Integer, Integer>>();
        shifts.add(Pair.of(1, 2));
        shifts.add(Pair.of(2, 1));
        shifts.add(Pair.of(1, -2));
        shifts.add(Pair.of(-2, 1));
        shifts.add(Pair.of(-1, 2));
        shifts.add(Pair.of(2, -1));
        shifts.add(Pair.of(-1, -2));
        shifts.add(Pair.of(-2, -1));
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (a[i][j] != 1) {
                    continue;
                }
                for (var shift : shifts) {
                    int newI = i + shift.getLeft();
                    int newJ = j + shift.getRight();
                    if (0 <= newI && newI < n && 0 <= newJ && newJ < m) {
                        if (a[newI][newJ] == 1) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private Task8() {
    }
}
