package org.ddd.fundamental.day;

import org.junit.Assert;
import org.junit.Test;

public class YearModelTest {
    @Test
    public void testCreateYearModel(){
        YearModelValueObject yearModel = YearModelValueObject.createTwoShift("两班制有周末");
        System.out.println(yearModel);

        YearModelValueObject yearModel1 = YearModelValueObject.createThreeShift("三班制有周末");
        System.out.println(yearModel1);

        Assert.assertEquals(yearModel.minutes(),185040);
        Assert.assertEquals(yearModel1.minutes(),277303);

        YearModelValueObject yearModel2 = YearModelValueObject.createTwoShift("两班制无周末", false);
        YearModelValueObject yearModel3 = YearModelValueObject.createThreeShift("三班制无周末", false);

        Assert.assertEquals(yearModel2.minutes(),259920);
        Assert.assertEquals(yearModel3.minutes(),389519);
    }
}
