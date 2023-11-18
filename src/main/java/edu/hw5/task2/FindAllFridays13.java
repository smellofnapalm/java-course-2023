package edu.hw5.task2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public final class FindAllFridays13 {

    final static String DATE_PATTERN = "yyyy-MM-dd";
    final static int THIRTEEN = 13;
    final static int DAYS_IN_WEEK = 7;

    static List<String> findAllFridays13(int year) {
        final int upperBound = 3000;
        final int months = 12;

        if (year < 0 || year >= upperBound) {
            throw new IllegalArgumentException("Год должен быть неотрицательным и не слишком большим!");
        }

        List<String> result = new ArrayList<>();
        var df = new SimpleDateFormat(DATE_PATTERN);
        Calendar calendar = new GregorianCalendar(year, Calendar.JANUARY, THIRTEEN);
        for (int i = 0; i < months; i++) {
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                result.add(df.format(calendar.getTime()));
            }
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        }
        return result;
    }

    static String findNextFriday13(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        var df = new SimpleDateFormat(DATE_PATTERN);
        do {
            if (calendar.get(Calendar.DAY_OF_MONTH) == THIRTEEN) {
                return df.format(calendar.getTime());
            }
            calendar.add(Calendar.DAY_OF_MONTH, DAYS_IN_WEEK);
        } while (true);
    }

    private FindAllFridays13() {
    }
}
