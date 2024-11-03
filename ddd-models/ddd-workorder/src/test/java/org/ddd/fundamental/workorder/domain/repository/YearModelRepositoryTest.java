package org.ddd.fundamental.workorder.domain.repository;

import org.ddd.fundamental.day.YearModelValue;
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
                YearModelValue.createTwoShift("两班制有周末"));
        repository.save(yearModel);
        YearModel yearModel1 = new YearModel(
                YearModelValue.createThreeShift("三班制有周末"));
        repository.save(yearModel1);
    }

    @Test
    public void testAllYearModel() {
        List<YearModel> modelList = repository.findAll();
        System.out.println(modelList);
    }
}
