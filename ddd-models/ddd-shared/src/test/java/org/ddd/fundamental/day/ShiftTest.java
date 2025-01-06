package org.ddd.fundamental.day;

import org.junit.Assert;
import org.junit.Test;
import java.time.LocalTime;


public class ShiftTest {

    @Test
    public void testShiftToString(){
        LocalTime start = LocalTime.parse("08:00:00");
        LocalTime end = LocalTime.parse("14:00:00");
        Shift shift = Shift.create(start,end,"第一班次早班");
        Assert.assertEquals("{\"start\":\"08:00:00\",\"end\":\"14:00:00\",\"shiftName\":\"第一班次早班\"}",shift.toString());
    }



    @Test
    public void testCreateShift() {

        LocalTime start = LocalTime.parse("08:00:00");
        LocalTime end = LocalTime.parse("14:00:00");
        Shift shift = Shift.create(start,end,"第一班次早班");

        LocalTime start1 = LocalTime.parse("14:00:00");
        LocalTime end1 = LocalTime.parse("20:00:00");
        Shift shift1 = Shift.create(start1,end1,"第二班次晚班");
        Assert.assertNotEquals(shift,shift1);
    }

    @Test
    public void testShiftEqual(){
        LocalTime start = LocalTime.parse("08:00:00");
        LocalTime end = LocalTime.parse("14:00:00");
        Shift shift = Shift.create(start,end,"第一班次早班");

        LocalTime start1 = LocalTime.parse("08:00:00");
        LocalTime end1 = LocalTime.parse("14:00:00");
        Shift shift1 = Shift.create(start1,end1,"第一班次早班");
        Assert.assertEquals(shift,shift1);

        LocalTime start2 = LocalTime.parse("08:00:00");
        LocalTime end2 = LocalTime.parse("14:00:00");
        Shift shift2 = Shift.create(start2,end2,"第一班次早班");
        Assert.assertEquals(shift,shift2);
    }

    @Test
    public void testCalculateMinutes(){
        LocalTime start = LocalTime.parse("08:00:00");
        LocalTime end = LocalTime.parse("14:00:00");
        Shift shift = Shift.create(start,end,"第一班次早班");
        Assert.assertEquals(shift.minutes(),360);
    }

    @Test
    public void testCreateFromStart(){
        Shift shift = Shift.createFromStart(LocalTime.parse("08:00:00"),4,"创建一个早班");
        Assert.assertEquals(shift.minutes(),240);
        Assert.assertEquals("{\"start\":\"08:00:00\",\"end\":\"12:00:00\",\"shiftName\":\"创建一个早班\"}",shift.toString());
    }

    @Test
    public void testCreateFromEnd(){
        Shift shift = Shift.createFromEnd(LocalTime.parse("12:00:00"),4,"创建一个中班");
        Assert.assertEquals(shift.minutes(),240);
        Assert.assertEquals("{\"start\":\"08:00:00\",\"end\":\"12:00:00\",\"shiftName\":\"创建一个中班\"}",shift.toString());
    }
}
