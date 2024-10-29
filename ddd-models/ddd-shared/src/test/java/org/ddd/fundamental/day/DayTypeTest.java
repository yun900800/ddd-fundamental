package org.ddd.fundamental.day;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.ddd.fundamental.day.ShiftTest.strToDate;

public class DayTypeTest {

    private static List<Shift> createShiftList() {
        Date start = strToDate("2024-10-27 08:00:00","yyyy-MM-dd HH:mm:ss");
        Date end = strToDate("2024-10-27 14:00:00","yyyy-MM-dd HH:mm:ss");
        Shift shift = new Shift(start,end,"第一班次早班");

        Date start1 = strToDate("2024-10-27 14:00:00","yyyy-MM-dd HH:mm:ss");
        Date end1 = strToDate("2024-10-27 20:00:00","yyyy-MM-dd HH:mm:ss");
        Shift shift1 = new Shift(start1,end1,"第二班次晚班");
        return Arrays.asList(shift,shift1);
    }

    private static List<Shift> createShiftList1() {
        Date start = strToDate("2024-10-26 08:00:00","yyyy-MM-dd HH:mm:ss");
        Date end = strToDate("2024-10-26 14:00:00","yyyy-MM-dd HH:mm:ss");
        Shift shift = new Shift(start,end,"第一班次早班");

        Date start1 = strToDate("2024-10-26 14:00:00","yyyy-MM-dd HH:mm:ss");
        Date end1 = strToDate("2024-10-26 20:10:00","yyyy-MM-dd HH:mm:ss");
        Shift shift1 = new Shift(start1,end1,"第二班次晚班");
        return Arrays.asList(shift,shift1);
    }

    @Test
    public void testCreateDayType(){
        DayType dayType = new DayType(createShiftList(),"模切设备日类型");
        Assert.assertEquals(dayType.getDayTypeName(),"模切设备日类型");

    }

    @Test
    public void testDayTypeEquals() {
        DayType dayType = new DayType(createShiftList(),"模切设备日类型");
        DayType dayType1 = new DayType(createShiftList(),"模切设备日类型");
        Assert.assertEquals(dayType,dayType1);

        DayType dayType2 = new DayType(createShiftList1(),"模切设备日类型");
        Assert.assertNotEquals(dayType,dayType2);
    }

    @Test
    public void testDayTypeToString(){
        DayType dayType = DayType.createTwoShiftDateType("两班制");
        Assert.assertEquals("DayType{dayTypeName=两班制, shiftList=[Shift{第一班次早班 = 08:00 - 14:00}, Shift{第二班次晚班 = 14:00 - 20:00}]}",
                dayType.toString());
        DayType dayType1 = DayType.createThreeShiftDateType("三班制");
        Assert.assertEquals("DayType{dayTypeName=三班制, shiftList=[Shift{第一班次早班 = 06:00 - 12:00}, Shift{第二班次中班 = 12:00 - 18:00}, Shift{第三班次晚班 = 18:00 - 00:00}]}",
                dayType1.toString());
    }
}
