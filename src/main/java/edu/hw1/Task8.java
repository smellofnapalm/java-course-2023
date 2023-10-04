package edu.hw1;

import static java.lang.Math.abs;

public class Task8 {
    public static boolean knightBoardCapture(int[][] a) {
        // Если ни один конь не расставлен, то, действительно, они не бьют друг друга
        if (a == null) {
            return true;
        }
        // Хочу, чтобы программа работала с произвольным размером поля, не только 8x8
        int n = a.length, m = 0;
        if (n >= 1) {
            m = a[0].length;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (a[i][j] != 1) {
                    continue;
                }

                // Пробегаюсь по всему квадрату соседей 5x5 из них достаю только те 8 клеток, которые может бить конь
                // P.S. Я хотел сделать список пар индексов сдвигов, чтобы не делать кучу лишних прогонов,
                // но класс Pair<Integer, Integer> мне не понравился, обертки -- зло :(
                for (int i1 = -2; i1 <= 2; i1++) {
                    for (int j1 = -2; j1 <= 2; j1++) {
                        int new_i = i + i1, new_j = j + j1;
                        if (new_i >= 0 && new_i < n && new_j >= 0 && new_j < m) {
                            if (((abs(new_i - i) == 2 && abs(new_j - j) == 1) ||
                                (abs(new_i - i) == 1 && abs(new_j - j) == 2)) && a[new_i][new_j] == 1) {
                                return false;
                            }
                        }
                    }

                }
            }
        }
        return true;
    }
}
