package org.ddd.fundamental.factory.value;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.junit.Assert;
import org.junit.Test;

public class WorkStationValueObjectTest {

    @Test
    public void testCreateAndChange(){
        WorkStationValueObject workStation = new WorkStationValueObject(ChangeableInfo.create(
                "车间2工位1","这是一个张大胖的工位"
        ));
        Assert.assertEquals(workStation.getWorkStation().toString(),"车间2工位1, 这是一个张大胖的工位, false");
        Assert.assertEquals(workStation.name(),"车间2工位1");
        Assert.assertEquals(workStation.desc(),"这是一个张大胖的工位");

        workStation.changeName("车间2工位3");
        Assert.assertEquals(workStation.name(),"车间2工位3");
        workStation.changeDesc("这个工位现在分配给王五的工位");
        Assert.assertEquals(workStation.desc(),"这个工位现在分配给王五的工位");
    }
}
