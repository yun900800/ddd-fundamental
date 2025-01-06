package org.ddd.fundamental.factory.domain.repository;

import org.ddd.fundamental.day.YearModelId;
import org.ddd.fundamental.day.YearModelValue;
import org.ddd.fundamental.factory.FactoryAppTest;
import org.ddd.fundamental.factory.domain.model.YearModel;
import org.ddd.fundamental.tuple.Tuple;
import org.ddd.fundamental.tuple.TwoTuple;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class YearModelRepositoryTest extends FactoryAppTest {

    @Autowired(required = false)
    private YearModelRepository yearModelRepository;

    private static TwoTuple<YearModel, YearModelId> create(){
        YearModel yearModel = YearModel.create(
                YearModelValue.createThreeShift("三班年度模型",2025
                        )
        );
        return Tuple.tuple(yearModel,yearModel.id());
    }

    @Test
    public void testAddYearModel(){
        TwoTuple<YearModel, YearModelId> twoTuple = create();
        yearModelRepository.persist(twoTuple.first);
        YearModel query = yearModelRepository.findById(twoTuple.second).orElse(null);
        Assert.assertEquals(query.getYearModel().isHasWeekend(),true);
        Assert.assertEquals(query.getYearModel().hours(),4386.0,0.0);
    }
}
