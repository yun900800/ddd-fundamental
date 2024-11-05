package org.ddd.fundamental.day.range;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

        Assert.assertEquals(range.minutes(),230);

        DateRange range1 = new DateRange(
                DateUtils.strToDate("2024-09-29 12:58:12","yyyy-MM-dd HH:mm:ss"),
                DateUtils.strToDate("2024-10-01 11:48:12","yyyy-MM-dd HH:mm:ss"),"工单排班不合理");
        TwoTuple<Integer,Long> result1 = range1.range();
        Assert.assertEquals(result1.first,2,0);
        Assert.assertEquals(result1.second,-70,0);
        range1.range();
        Assert.assertEquals(range1.minutes(),2810);
    }

    @Test
    public void testDateRangeJson() throws JsonProcessingException {
        DateRange range = new DateRange(
                DateUtils.strToDate("2024-10-01 12:58:12","yyyy-MM-dd HH:mm:ss"),
                DateUtils.strToDate("2024-10-01 16:48:12","yyyy-MM-dd HH:mm:ss"),"机器维修");
        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(range);
        System.out.println(result);
    }

    @Test
    public void testDateRangeClone(){
        DateRange range = new DateRange(
                DateUtils.strToDate("2024-10-01 12:58:12","yyyy-MM-dd HH:mm:ss"),
                DateUtils.strToDate("2024-10-01 16:48:12","yyyy-MM-dd HH:mm:ss"),"机器维修");
        DateRange copy = range.clone();
        Assert.assertEquals(range,copy);
        Assert.assertNotSame(range,copy);
    }


}
