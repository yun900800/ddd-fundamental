package org.ddd.fundamental.day.range;

import org.ddd.fundamental.tuple.TwoTuple;
import org.ddd.fundamental.utils.DateUtils;
import org.junit.Assert;
import org.junit.Test;

public class DateRangeTest {

    @Test
    public void testDateRangeCreate(){
        DateRange range = new DateRange(
                DateUtils.strToDate("2024-10-01 12:58:12","yyyy-MM-dd HH:mm:ss"),
                DateUtils.strToDate("2024-10-01 16:48:12","yyyy-MM-dd HH:mm:ss"),"机器维修");
        TwoTuple<Integer,Long> result = range.range();
        Assert.assertEquals(result.first,0,0);
        Assert.assertEquals(result.second,230,0);

        DateRange range1 = new DateRange(
                DateUtils.strToDate("2024-09-29 12:58:12","yyyy-MM-dd HH:mm:ss"),
                DateUtils.strToDate("2024-10-01 11:48:12","yyyy-MM-dd HH:mm:ss"),"工单排班不合理");
        TwoTuple<Integer,Long> result1 = range1.range();
        Assert.assertEquals(result1.first,2,0);
        Assert.assertEquals(result1.second,-70,0);
        TwoTuple<Integer,Long> result2 = range1.range();
    }

}
