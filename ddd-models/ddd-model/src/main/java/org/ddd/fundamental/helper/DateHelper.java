package org.ddd.fundamental.helper;

import java.util.Calendar;
import java.util.Date;

public final class DateHelper {
    public static Date addInterval(int interval, Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH,interval);
        Date date1 = calendar.getTime();
        return date1;
    }
}
