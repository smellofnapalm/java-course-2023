package edu.hw1;

import java.util.regex.Pattern;

public final class Task1 {
    public static long minutesToSeconds(String time) {
        String[] parts = time.split(":");
        final long secInMin = 60;
        // Некорректно задана строка
        if (parts.length != 2) {
            return -1;
        }
        // Проверка на то, что мы парсим действительно положительные числа
        // Причем длина записи секунд должна быть = 2
        // А длина записи минут >= 2
        Pattern patternForSeconds = Pattern.compile("[+]?\\d{2}");
        Pattern patternForMinutes = Pattern.compile("[+]?\\d{2,}");
        if (!patternForMinutes.matcher(parts[0]).matches()
            || !patternForSeconds.matcher(parts[1]).matches()) {
            return -1;
        }
        var minutes = Integer.parseInt(parts[0]);
        var seconds = Integer.parseInt(parts[1]);
        // Некорректное число секунд или минут
        if (seconds >= secInMin) {
            return -1;
        }
        return minutes * secInMin + seconds;
    }

    private Task1() {
    }
}
