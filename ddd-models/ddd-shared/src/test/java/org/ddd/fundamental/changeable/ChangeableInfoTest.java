package org.ddd.fundamental.changeable;

import org.junit.Assert;
import org.junit.Test;

public class ChangeableInfoTest {

    @Test
    public void testCreateChangeableInfo(){
        ChangeableInfo changeableInfo = ChangeableInfo.create("原材料仓库","用来存储原材料的仓库");
        Assert.assertEquals(changeableInfo.toString(),"{\"desc\":\"用来存储原材料的仓库\",\"isUse\":false,\"name\":\"原材料仓库\"}");

        ChangeableInfo changeableInfo1 = ChangeableInfo.create("原材料区域","用来存储原材料的区域", true);
        Assert.assertEquals(changeableInfo1.toString(),"{\"desc\":\"用来存储原材料的区域\",\"isUse\":true,\"name\":\"原材料区域\"}");
    }

    @Test
    public void testChangeInfo(){
        ChangeableInfo changeableInfo = ChangeableInfo.create("原材料仓库","用来存储原材料的仓库");
        Assert.assertEquals(changeableInfo.toString(),"{\"desc\":\"用来存储原材料的仓库\",\"isUse\":false,\"name\":\"原材料仓库\"}");
        changeableInfo = changeableInfo.changeName("原材料区域");
        Assert.assertEquals(changeableInfo.toString(),"{\"desc\":\"用来存储原材料的仓库\",\"isUse\":false,\"name\":\"原材料区域\"}");

        changeableInfo = changeableInfo.changeDesc("用来存储原材料的区域");
        Assert.assertEquals(changeableInfo.toString(),"{\"desc\":\"用来存储原材料的区域\",\"isUse\":false,\"name\":\"原材料区域\"}");

        changeableInfo = changeableInfo.enableUse();
        Assert.assertEquals(changeableInfo.toString(),"{\"desc\":\"用来存储原材料的区域\",\"isUse\":true,\"name\":\"原材料区域\"}");

        changeableInfo = changeableInfo.disableUse();
        Assert.assertEquals(changeableInfo.toString(),"{\"desc\":\"用来存储原材料的区域\",\"isUse\":false,\"name\":\"原材料区域\"}");
    }
}
