package org.ddd.fundamental.factory.value;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.junit.Assert;
import org.junit.Test;

public class MachineShopValueObjectTest {

    @Test
    public void testCreateAndChange(){
        MachineShopValueObject machineShop = new MachineShopValueObject(ChangeableInfo.create(
                "车间2","这个是生产电路板的车间"
        ));
        Assert.assertEquals(machineShop.getMachineShop().toString(),"车间2, 这个是生产电路板的车间, false");
        Assert.assertEquals(machineShop.name(),"车间2");
        Assert.assertEquals(machineShop.desc(),"这个是生产电路板的车间");

        machineShop.changeName("车间1");
        Assert.assertEquals(machineShop.name(),"车间1");
        machineShop.changeDesc("这个整改成了一个技改车间");
        Assert.assertEquals(machineShop.desc(),"这个整改成了一个技改车间");
    }
}
