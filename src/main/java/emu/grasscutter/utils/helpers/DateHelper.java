package emu.grasscutter.utils.helpers;

import java.util.*;

public interface DateHelper {
    static Date onlyYearMonthDay(Date now) {
        var calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    static int getUnixTime(Date localDateTime) {
        return (int) (localDateTime.getTime() / 1000L);
    }
}
