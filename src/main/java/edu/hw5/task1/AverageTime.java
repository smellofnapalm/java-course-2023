package edu.hw5.task1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public final class AverageTime {
    static String averageTime(List<String> strings) {
        if (strings == null || strings.isEmpty()) {
            throw new IllegalArgumentException("Размер списка строк должен быть ненулевым!");
        }
        var dateFormat = new SimpleDateFormat("yyyy-MM-dd, HH:mm");
        dateFormat.setLenient(false);
        double average = strings.stream().map(str -> Arrays.stream(str.split(" - "))
                .map(date -> {
                    try {
                        return dateFormat.parse(date).getTime();
                    } catch (ParseException e) {
                        throw new IllegalArgumentException("Строки должны быть в нужном формате!");
                    }
                }))
            .map(Stream::toList)
            .mapToLong(lst -> lst.get(1) - lst.get(0)).average().getAsDouble();
        var duration = Duration.ofMillis(Math.round(average));
        var seconds = duration.toSecondsPart();
        var minutes = duration.toMinutesPart();
        var hours = duration.toHoursPart();
        var days = duration.toDaysPart();
        return "%dд %dч %dм %dс".formatted(days, hours, minutes, seconds);
    }

    private AverageTime() {
    }
}
