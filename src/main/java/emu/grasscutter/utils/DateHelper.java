package emu.grasscutter.utils;

import java.util.Date;
import java.util.Calendar;

public final class DateHelper {
    public static Date onlyYDM(Date now) {
        Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
    }
}
