package org.ddd.fundamental.workprocess.value.step;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.changeable.ChangeableInfo;
import org.junit.Assert;
import org.junit.Test;

@Slf4j
public class StepDefinitionTest {

    @Test
    public void testCreateStepDefinition(){
        StepDefinition stepDefinition = StepDefinition
                .create(
                        ChangeableInfo.create("焊接工序第一步","主要做一些基础焊接"),
                        1
                );
        Assert.assertEquals("{\"order\":1,\"stepInfo\":\"焊接工序第一步, 主要做一些基础焊接, false\"}",stepDefinition.toString());

        Assert.assertEquals(stepDefinition.getStepInfo().getName(),"焊接工序第一步");
        Assert.assertEquals(stepDefinition.getStepInfo().getDesc(),"主要做一些基础焊接");
        Assert.assertEquals(stepDefinition.getOrder(),1,0);
    }

    @Test
    public void changeStepInfo(){
        StepDefinition stepDefinition = StepDefinition
                .create(
                        ChangeableInfo.create("焊接工序第一步","主要做一些基础焊接"),
                        1
                );
        ChangeableInfo stepInfo = ChangeableInfo.create("焊接工序第二步","主要做一些助力焊接");
        stepDefinition.changeStepInfo(stepInfo).changeOrder(2);
        Assert.assertEquals("{\"order\":2,\"stepInfo\":\"焊接工序第二步, 主要做一些助力焊接, false\"}",stepDefinition.toString());

        Assert.assertEquals(stepDefinition.getStepInfo().getName(),"焊接工序第二步");
        Assert.assertEquals(stepDefinition.getStepInfo().getDesc(),"主要做一些助力焊接");
        Assert.assertEquals(stepDefinition.getOrder(),2,0);
    }
}
