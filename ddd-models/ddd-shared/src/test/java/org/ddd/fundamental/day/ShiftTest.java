package org.ddd.fundamental.day;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ShiftTest {

    public static Date strToDate(String dateStr,String strFormat) {
        SimpleDateFormat format = new SimpleDateFormat(strFormat);
        try {
            return format.parse(dateStr);
        } catch (ParseException e){
            e.printStackTrace();
        }
        return null;
    }


    @Test
    public void testCreateShift() {

        Date start = strToDate("2024-10-27 08:00:00","yyyy-MM-dd HH:mm:ss");
        Date end = strToDate("2024-10-27 14:00:00","yyyy-MM-dd HH:mm:ss");
        Shift shift = new Shift(start,end,"第一班次早班");

        Date start1 = strToDate("2024-10-27 14:00:00","yyyy-MM-dd HH:mm:ss");
        Date end1 = strToDate("2024-10-27 20:00:00","yyyy-MM-dd HH:mm:ss");
        Shift shift1 = new Shift(start1,end1,"第二班次晚班");
        Assert.assertNotEquals(shift,shift1);
    }

    @Test
    public void testShiftEqual(){
        Date start = strToDate("2024-10-27 08:00:00","yyyy-MM-dd HH:mm:ss");
        Date end = strToDate("2024-10-27 14:00:00","yyyy-MM-dd HH:mm:ss");
        Shift shift = new Shift(start,end,"第一班次早班");

        Date start1 = strToDate("2024-10-26 08:00:00","yyyy-MM-dd HH:mm:ss");
        Date end1 = strToDate("2024-10-26 14:00:00","yyyy-MM-dd HH:mm:ss");
        Shift shift1 = new Shift(start1,end1,"第一班次早班");
        Assert.assertEquals(shift,shift1);

        Date start2 = strToDate("2024-10-27 08:00:00","yyyy-MM-dd HH:mm:ss");
        Date end2 = strToDate("2024-10-27 14:00:00","yyyy-MM-dd HH:mm:ss");
        Shift shift2 = new Shift(start2,end2,"第一班次早班");
        Assert.assertEquals(shift,shift2);
    }
}
