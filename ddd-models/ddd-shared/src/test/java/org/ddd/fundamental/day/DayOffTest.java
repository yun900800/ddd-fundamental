package org.ddd.fundamental.day;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;

public class DayOffTest {

    private static DayOff createDayOff() {
        DateOffWrapper date0 = DateOffWrapper.valueOf(LocalDate.parse("2024-10-01"));
        DateOffWrapper date1 = DateOffWrapper.valueOf(LocalDate.parse("2024-10-02"));
        DateOffWrapper date2 = DateOffWrapper.valueOf(LocalDate.parse("2024-10-03"));
        DateOffWrapper date3 = DateOffWrapper.valueOf(LocalDate.parse("2024-10-04"));
        return DayOff.create(Arrays.asList(date0,date1,date2,date3),2024);
    }

    @Test
    public void testDayOffEqual() {
        DateOffWrapper date0 = DateOffWrapper.valueOf(LocalDate.parse("2024-10-01"));
        DateOffWrapper date1 = DateOffWrapper.valueOf(LocalDate.parse("2024-10-02"));
        DateOffWrapper date2 = DateOffWrapper.valueOf(LocalDate.parse("2024-10-03"));
        DateOffWrapper date3 = DateOffWrapper.valueOf(LocalDate.parse("2024-10-04"));
        DayOff dayOff = DayOff.create(Arrays.asList(date0,date1,date2),2024);
        DayOff dayOff1 = DayOff.create(Arrays.asList(date0,date1,date2),2024);
        Assert.assertEquals(dayOff,dayOff1);
        DayOff dayOff2 = DayOff.create(Arrays.asList(date0,date1,date2,date3),2024);
        Assert.assertNotEquals(dayOff,dayOff2);
    }

    @Test
    public void testDateOffToString() {
        DayOff dayOff = createDayOff();
        Assert.assertEquals("DayOff{假期=10-01,10-02,10-03,10-04}",
                dayOff.toString());

        DayOff dayOff1 = DayOff.createDayOff(2025);
        Assert.assertEquals("DayOff{假期=01-04,01-05,01-11,01-12," +
                        "01-18,01-19,01-25,01-26,02-01,02-02,02-08,02-09," +
                        "02-15,02-16,02-22,02-23,03-01,03-02,03-08,03-09," +
                        "03-15,03-16,03-22,03-23,03-29,03-30,04-05,04-06,04-12," +
                        "04-13,04-19,04-20,04-26,04-27,05-03,05-04,05-10," +
                        "05-11,05-17,05-18,05-24,05-25,05-31,06-01,06-07,06-08," +
                        "06-14,06-15,06-21,06-22,06-28,06-29,07-05,07-06,07-12," +
                        "07-13,07-19,07-20,07-26,07-27,08-02,08-03,08-09,08-10," +
                        "08-16,08-17,08-23,08-24,08-30,08-31,09-06,09-07,09-13,09-14," +
                        "09-20,09-21,09-27,09-28,10-04,10-05,10-11,10-12,10-18,10-19," +
                        "10-25,10-26,11-01,11-02,11-08,11-09,11-15,11-16,11-22,11-23," +
                        "11-29,11-30,12-06,12-07,12-13,12-14,12-20,12-21,12-27,12-28," +
                        "01-01,05-01,10-01}",
                dayOff1.toString());
    }

    @Test
    public void testDetWorkingDays(){
        int workingDays = DayOff.getWorkingDays(2025).size();
        Assert.assertEquals(workingDays,258);

        int restDays = DayOff.getRestDays(2025).size();
        Assert.assertEquals(restDays,107);

    }
}
