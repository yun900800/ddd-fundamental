package org.ddd.fundamental.day;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;

public class DayOffTest {

    private static DayOff createDayOff() {
        Date date0 = ShiftTest.strToDate("2024-10-01","yyyy-MM-dd");
        Date date1 = ShiftTest.strToDate("2024-10-02","yyyy-MM-dd");
        Date date2 = ShiftTest.strToDate("2024-10-03","yyyy-MM-dd");
        Date date3 = ShiftTest.strToDate("2024-10-04","yyyy-MM-dd");
        return new DayOff(Arrays.asList(date0,date1,date2,date3));
    }

    @Test
    public void testDayOffEqual() {
        Date date0 = ShiftTest.strToDate("2024-10-01","yyyy-MM-dd");
        Date date1 = ShiftTest.strToDate("2024-10-02","yyyy-MM-dd");
        Date date2 = ShiftTest.strToDate("2024-10-03","yyyy-MM-dd");
        Date date3 = ShiftTest.strToDate("2024-10-04","yyyy-MM-dd");
        DayOff dayOff = new DayOff(Arrays.asList(date0,date1,date2));
        DayOff dayOff1 = new DayOff(Arrays.asList(date0,date1,date2));
        Assert.assertEquals(dayOff,dayOff1);
        DayOff dayOff2 = new DayOff(Arrays.asList(date0,date1,date2,date3));
        Assert.assertNotEquals(dayOff,dayOff2);
    }

    @Test
    public void testDateOffToString() {
        DayOff dayOff = createDayOff();
        Assert.assertEquals("DayOff{假期=10-01,10-02,10-03,10-04}",
                dayOff.toString());
    }
}
