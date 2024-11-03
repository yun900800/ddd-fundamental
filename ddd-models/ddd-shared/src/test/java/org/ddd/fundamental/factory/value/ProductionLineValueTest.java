package org.ddd.fundamental.factory.value;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.junit.Assert;
import org.junit.Test;

public class ProductionLineValueTest {

    @Test
    public void testCreateAndChange(){
        ProductionLineValue line = new ProductionLineValue(ChangeableInfo.create(
                "这是车间一对应的产线5","这是一条专业的生产CPU的产线"
        ));

        Assert.assertEquals(line.getProductLine().toString(),"这是车间一对应的产线5, 这是一条专业的生产CPU的产线, false");
        Assert.assertEquals(line.name(),"这是车间一对应的产线5");
        Assert.assertEquals(line.desc(),"这是一条专业的生产CPU的产线");

        line.changeName("产线6");
        Assert.assertEquals(line.name(),"产线6");
        line.changeDesc("这条产线整改成了一个技改产线");
        Assert.assertEquals(line.desc(),"这条产线整改成了一个技改产线");
    }
}
