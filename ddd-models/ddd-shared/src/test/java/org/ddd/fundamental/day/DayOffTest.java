package org.ddd.fundamental.day;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;

public class DayOffTest {

    private static DayOff createDayOff() {
        DateOffWrapper date0 = DateOffWrapper.valueOf(LocalDate.parse("2024-10-01"));
        DateOffWrapper date1 = DateOffWrapper.valueOf(LocalDate.parse("2024-10-02"));
        DateOffWrapper date2 = DateOffWrapper.valueOf(LocalDate.parse("2024-10-03"));
        DateOffWrapper date3 = DateOffWrapper.valueOf(LocalDate.parse("2024-10-04"));
        return new DayOff(Arrays.asList(date0,date1,date2,date3));
    }

    @Test
    public void testDayOffEqual() {
        DateOffWrapper date0 = DateOffWrapper.valueOf(LocalDate.parse("2024-10-01"));
        DateOffWrapper date1 = DateOffWrapper.valueOf(LocalDate.parse("2024-10-02"));
        DateOffWrapper date2 = DateOffWrapper.valueOf(LocalDate.parse("2024-10-03"));
        DateOffWrapper date3 = DateOffWrapper.valueOf(LocalDate.parse("2024-10-04"));
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
