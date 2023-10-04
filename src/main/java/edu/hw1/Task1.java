package edu.hw1;

public class Task1 {
    public static long minutesToSeconds(String time) {
        String[] parts = time.split(":");
        // Некорректно задана строка
        if (parts.length != 2) {
            return -1;
        }
        var minutes = Long.parseLong(parts[0]);
        var seconds = Long.parseLong(parts[1]);
        // Некорректное число секунд или минут
        if (seconds < 0 || seconds > 60 || minutes < 0) {
            return -1;
        }
        return minutes*60 + seconds;
    }
}
