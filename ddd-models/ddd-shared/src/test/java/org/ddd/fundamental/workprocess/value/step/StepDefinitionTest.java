package org.ddd.fundamental.workprocess.value.step;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.changeable.ChangeInfo;
import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.changeable.NameDescInfo;
import org.junit.Assert;
import org.junit.Test;

@Slf4j
public class StepDefinitionTest {

    @Test
    public void testCreateStepDefinition(){
        StepDefinition stepDefinition = StepDefinition
                .create(
                        ChangeInfo.create(NameDescInfo.create("焊接工序第一步","主要做一些基础焊接"),false),
                        1
                );
        Assert.assertEquals("{\"order\":1,\"stepInfo\":{\"isUse\":false,\"nameDescInfo\":{\"desc\":\"主要做一些基础焊接\",\"name\":\"焊接工序第一步\"}}}",stepDefinition.toString());

        Assert.assertEquals(stepDefinition.getStepInfo().getNameDescInfo().getName(),"焊接工序第一步");
        Assert.assertEquals(stepDefinition.getStepInfo().getNameDescInfo().getDesc(),"主要做一些基础焊接");
        Assert.assertEquals(stepDefinition.getOrder(),1,0);
    }

    @Test
    public void changeStepInfo(){
        StepDefinition stepDefinition = StepDefinition
                .create(
                        ChangeInfo.create(NameDescInfo.create("焊接工序第一步","主要做一些基础焊接"),false),
                        1
                );
        ChangeInfo stepInfo = ChangeInfo.create(NameDescInfo.create("焊接工序第二步","主要做一些助力焊接"),false);
        stepDefinition.changeStepInfo(stepInfo).changeOrder(2);
        Assert.assertEquals("{\"order\":2,\"stepInfo\":{\"isUse\":false,\"nameDescInfo\":{\"desc\":\"主要做一些助力焊接\",\"name\":\"焊接工序第二步\"}}}",stepDefinition.toString());

        Assert.assertEquals(stepDefinition.getStepInfo().getNameDescInfo().getName(),"焊接工序第二步");
        Assert.assertEquals(stepDefinition.getStepInfo().getNameDescInfo().getDesc(),"主要做一些助力焊接");
        Assert.assertEquals(stepDefinition.getOrder(),2,0);
    }
}
