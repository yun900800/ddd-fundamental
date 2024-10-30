package org.ddd.fundamental.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

public final class DateUtils {
    public static Date strToDate(String dateStr, String strFormat) {
        SimpleDateFormat format = new SimpleDateFormat(strFormat);
        try {
            return format.parse(dateStr);
        } catch (ParseException e){
            e.printStackTrace();
        }
        return null;
    }

    public static String dateToStr(Date date,String strFormat){
        SimpleDateFormat format = new SimpleDateFormat(strFormat);
        return format.format(date);
    }

    public static LocalDate dataToLocalDate(Date date) {
        Instant instant = date.toInstant();
        return instant.atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalTime dataToLocalTime(Date date) {
        Instant instant = date.toInstant();
        return instant.atZone(ZoneId.systemDefault()).toLocalTime();
    }
}

