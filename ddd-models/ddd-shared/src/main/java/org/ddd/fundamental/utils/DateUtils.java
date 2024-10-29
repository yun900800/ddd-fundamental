package org.ddd.fundamental.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
}

