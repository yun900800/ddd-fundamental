package org.ddd.fundamental.day;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;


@Slf4j
public class CalendarTypeTest {

    private static List<Shift> createShiftList() {
        LocalTime start = LocalTime.parse("08:00:00");
        LocalTime end = LocalTime.parse("14:00:00");
        Shift shift = Shift.create(start,end,"第一班次早班");

        LocalTime start1 = LocalTime.parse("14:00:00");
        LocalTime end1 = LocalTime.parse("20:00:00");
        Shift shift1 = Shift.create(start1,end1,"第二班次晚班");
        return Arrays.asList(shift,shift1);
    }

    private static List<Shift> createShiftList1() {
        LocalTime start = LocalTime.parse("08:00:00");
        LocalTime end = LocalTime.parse("14:00:00");
        Shift shift = Shift.create(start,end,"第一班次早班");

        LocalTime start1 = LocalTime.parse("14:00:00");
        LocalTime end1 = LocalTime.parse("20:10:00");
        Shift shift1 = Shift.create(start1,end1,"第二班次晚班");
        return Arrays.asList(shift,shift1);
    }

    @Test
    public void testCreateDayType(){
        CalendarTypeValue dayType = CalendarTypeValue.create(createShiftList(),"模切设备日类型");
        Assert.assertEquals(dayType.getDayTypeName(),"模切设备日类型");
        Assert.assertEquals(dayType.minutes(), 720);

    }

    @Test
    public void testDayTypeEquals() {
        CalendarTypeValue dayType = CalendarTypeValue.create(createShiftList(),"模切设备日类型");
        CalendarTypeValue dayType1 = CalendarTypeValue.create(createShiftList(),"模切设备日类型");
        Assert.assertEquals(dayType,dayType1);

        CalendarTypeValue dayType2 = CalendarTypeValue.create(createShiftList1(),"模切设备日类型");
        Assert.assertNotEquals(dayType,dayType2);
    }

    @Test
    public void testDayTypeToString(){
        CalendarTypeValue dayType = CalendarTypeValue.createTwoShiftDateType("两班制");
        Assert.assertEquals("DayType{dayTypeName=两班制, shiftList=[Shift{第一班次早班 = 08:00 - 14:00}, Shift{第二班次晚班 = 14:00 - 20:00}]}",
                dayType.toString());
        CalendarTypeValue dayType1 = CalendarTypeValue.createThreeShiftDateType("三班制");
        Assert.assertEquals("DayType{dayTypeName=三班制, shiftList=[Shift{第一班次早班 = 06:00 - 12:00}, Shift{第二班次中班 = 12:00 - 18:00}, Shift{第三班次晚班 = 18:00 - 23:59}]}",
                dayType1.toString());
    }

    @Test
    public void testCreateRandomShift(){
        CalendarTypeValue calendarTypeValue = CalendarTypeValue.createRandomShift(
                "随机班次",
                LocalTime.of(6,0),
                LocalTime.of(22,0),
                300,260
        );
        Assert.assertTrue(calendarTypeValue.getShiftList().size()>2);
        log.info("calendarTypeValue hour is {} ",calendarTypeValue.hours());
    }
    
}
