package org.ddd.fundamental.day;

import org.junit.Test;

public class YearModelTest {
    @Test
    public void testCreateYearModel(){
        YearModelValueObject yearModel = YearModelValueObject.createTwoShift("两班制有周末");
        System.out.println(yearModel);

        YearModelValueObject yearModel1 = YearModelValueObject.createThreeShift("三班制有周末");
        System.out.println(yearModel1);
    }
}
