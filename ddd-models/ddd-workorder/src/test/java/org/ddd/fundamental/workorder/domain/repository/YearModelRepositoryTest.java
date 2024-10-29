package org.ddd.fundamental.workorder.domain.repository;

import org.ddd.fundamental.day.YearModelValueObject;
import org.ddd.fundamental.workorder.WorkOrderAppTest;
import org.ddd.fundamental.workorder.domain.model.YearModel;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class YearModelRepositoryTest extends WorkOrderAppTest {

    @Autowired
    private YearModelRepository repository;

    @Test
    public void testCreateYearModel(){
        YearModel yearModel = new YearModel(
                YearModelValueObject.createTwoShift("两班制有周末"));
        repository.save(yearModel);
        YearModel yearModel1 = new YearModel(
                YearModelValueObject.createThreeShift("三班制有周末"));
        repository.save(yearModel1);
    }

    @Test
    public void testAllYearModel() {
        List<YearModel> modelList = repository.findAll();
        System.out.println(modelList);
    }
}
