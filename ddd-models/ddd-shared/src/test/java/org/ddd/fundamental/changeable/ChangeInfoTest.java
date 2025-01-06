package org.ddd.fundamental.changeable;

import org.junit.Assert;
import org.junit.Test;

public class ChangeInfoTest {

    @Test
    public void testCreateChangeInfo(){
        ChangeInfo changeInfo = ChangeInfo.create(
                NameDescInfo.create("name","desc"),
                false
        );
        Assert.assertEquals(changeInfo.toString(),"{\"isUse\":false,\"nameDescInfo\":{\"desc\":\"desc\",\"name\":\"name\"}}");
        Assert.assertEquals(
                changeInfo.getNameDescInfo().getName(),"name"
        );
        Assert.assertEquals(
                changeInfo.getNameDescInfo().getDesc(),"desc"
        );
        Assert.assertEquals(
                changeInfo.isUse(),false
        );
    }

    @Test
    public void testChangeInfo(){
        ChangeInfo changeInfo = ChangeInfo.create(
                NameDescInfo.create("name","desc"),
                false
        );
        changeInfo.changeInfo(
                NameDescInfo.create("name1","desc1")
        ).enableUse();
        Assert.assertEquals(
                changeInfo.getNameDescInfo().getName(),"name1"
        );
        Assert.assertEquals(
                changeInfo.getNameDescInfo().getDesc(),"desc1"
        );
        Assert.assertEquals(
                changeInfo.isUse(),true
        );
    }

    @Test
    public void testCopyChangeInfo(){
        ChangeInfo changeInfo = ChangeInfo.create(
                NameDescInfo.create("name","desc"),
                false
        );
        ChangeInfo copy = changeInfo.clone();
        changeInfo.enableUse();
        changeInfo.changeInfo(NameDescInfo.create("name1","desc1"));

        Assert.assertEquals(changeInfo.isUse(), true);
        Assert.assertEquals(changeInfo.getNameDescInfo().getDesc(), "desc1");
        Assert.assertEquals(changeInfo.getNameDescInfo().getName(), "name1");
        Assert.assertEquals(copy.isUse(), false);
        Assert.assertEquals(copy.getNameDescInfo().getDesc(), "desc");
        Assert.assertEquals(copy.getNameDescInfo().getName(), "name");
    }
}
