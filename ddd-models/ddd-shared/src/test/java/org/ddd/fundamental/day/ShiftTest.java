package org.ddd.fundamental.day;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
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
    public void testShiftToString(){
        LocalTime start = LocalTime.parse("08:00:00");
        LocalTime end = LocalTime.parse("14:00:00");
        Shift shift = new Shift(start,end,"第一班次早班");
        Assert.assertEquals("Shift{第一班次早班 = 08:00 - 14:00}",shift.toString());
    }



    @Test
    public void testCreateShift() {

        LocalTime start = LocalTime.parse("08:00:00");
        LocalTime end = LocalTime.parse("14:00:00");
        Shift shift = new Shift(start,end,"第一班次早班");

        LocalTime start1 = LocalTime.parse("14:00:00");
        LocalTime end1 = LocalTime.parse("20:00:00");
        Shift shift1 = new Shift(start1,end1,"第二班次晚班");
        Assert.assertNotEquals(shift,shift1);
    }

    @Test
    public void testShiftEqual(){
        LocalTime start = LocalTime.parse("08:00:00");
        LocalTime end = LocalTime.parse("14:00:00");
        Shift shift = new Shift(start,end,"第一班次早班");

        LocalTime start1 = LocalTime.parse("08:00:00");
        LocalTime end1 = LocalTime.parse("14:00:00");
        Shift shift1 = new Shift(start1,end1,"第一班次早班");
        Assert.assertEquals(shift,shift1);

        LocalTime start2 = LocalTime.parse("08:00:00");
        LocalTime end2 = LocalTime.parse("14:00:00");
        Shift shift2 = new Shift(start2,end2,"第一班次早班");
        Assert.assertEquals(shift,shift2);
    }

    @Test
    public void testCalculateMinutes(){
        LocalTime start = LocalTime.parse("08:00:00");
        LocalTime end = LocalTime.parse("14:00:00");
        Shift shift = new Shift(start,end,"第一班次早班");
        Assert.assertEquals(shift.minutes(),360);
    }
}
