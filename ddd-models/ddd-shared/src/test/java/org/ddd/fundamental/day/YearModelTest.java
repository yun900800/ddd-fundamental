package org.ddd.fundamental.day;

import org.junit.Assert;
import org.junit.Test;

public class YearModelTest {
    @Test
    public void testCreateYearModel(){
        YearModelValue yearModel = YearModelValue.createTwoShift("两班制有周末",2024);
        YearModelValue yearModel1 = YearModelValue.createThreeShift("三班制有周末",2024);

        Assert.assertEquals(yearModel.minutes(),185760);
        Assert.assertEquals(yearModel.hours(),3096.0,0.0);
        Assert.assertEquals(yearModel.isHasWeekend(),true);
        Assert.assertEquals(yearModel1.minutes(),278382);
        Assert.assertEquals(yearModel1.hours(),4386.0,0.0);
        Assert.assertEquals(yearModel1.isHasWeekend(),true);

        YearModelValue yearModel2 = YearModelValue.createTwoShift("两班制无周末", false,2024);
        YearModelValue yearModel3 = YearModelValue.createThreeShift("三班制无周末", false,2024);

        Assert.assertEquals(yearModel2.minutes(),260640);
        Assert.assertEquals(yearModel2.hours(),4344,0.0);
        Assert.assertEquals(yearModel2.isHasWeekend(),false);
        Assert.assertEquals(yearModel3.minutes(),390598);
        Assert.assertEquals(yearModel3.hours(),6154.0,0.0);
        Assert.assertEquals(yearModel3.isHasWeekend(),false);
    }
}
