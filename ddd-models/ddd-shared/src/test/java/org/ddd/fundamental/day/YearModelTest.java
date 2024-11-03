package org.ddd.fundamental.day;

import org.junit.Assert;
import org.junit.Test;

public class YearModelTest {
    @Test
    public void testCreateYearModel(){
        YearModelValue yearModel = YearModelValue.createTwoShift("两班制有周末");
        System.out.println(yearModel);

        YearModelValue yearModel1 = YearModelValue.createThreeShift("三班制有周末");
        System.out.println(yearModel1);

        Assert.assertEquals(yearModel.minutes(),185040);
        Assert.assertEquals(yearModel1.minutes(),277303);

        YearModelValue yearModel2 = YearModelValue.createTwoShift("两班制无周末", false);
        YearModelValue yearModel3 = YearModelValue.createThreeShift("三班制无周末", false);

        Assert.assertEquals(yearModel2.minutes(),259920);
        Assert.assertEquals(yearModel3.minutes(),389519);
    }
}
