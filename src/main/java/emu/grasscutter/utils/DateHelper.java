package emu.grasscutter.utils;

import java.util.Calendar;
import java.util.Date;

public final class DateHelper {
    public static Date onlyYearMonthDay(Date now) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static int getUnixTime(Date localDateTime) {
        return (int) (localDateTime.getTime() / 1000L);
    }
}
