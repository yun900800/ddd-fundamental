package org.ddd.fundamental.changeable;

import org.junit.Assert;
import org.junit.Test;

public class ChangeableInfoTest {

    @Test
    public void testCreateChangeableInfo(){
        ChangeableInfo changeableInfo = ChangeableInfo.create("原材料仓库","用来存储原材料的仓库");
        Assert.assertEquals(changeableInfo.toString(),"ChangeableInfo{"+
                "name=原材料仓库" +
                ", desc=用来存储原材料的仓库" +
                ", isUse=false" +
                "}");

        ChangeableInfo changeableInfo1 = ChangeableInfo.create("原材料区域","用来存储原材料的区域", true);
        Assert.assertEquals(changeableInfo1.toString(),"ChangeableInfo{"+
                "name=原材料区域" +
                ", desc=用来存储原材料的区域" +
                ", isUse=true" +
                "}");
    }

    @Test
    public void testChangeInfo(){
        ChangeableInfo changeableInfo = ChangeableInfo.create("原材料仓库","用来存储原材料的仓库");
        Assert.assertEquals(changeableInfo.toString(),"ChangeableInfo{"+
                "name=原材料仓库" +
                ", desc=用来存储原材料的仓库" +
                ", isUse=false" +
                "}");
        changeableInfo = changeableInfo.changeName("原材料区域");
        Assert.assertEquals(changeableInfo.toString(),"ChangeableInfo{"+
                "name=原材料区域" +
                ", desc=用来存储原材料的仓库" +
                ", isUse=false" +
                "}");

        changeableInfo = changeableInfo.changeDesc("用来存储原材料的区域");
        Assert.assertEquals(changeableInfo.toString(),"ChangeableInfo{"+
                "name=原材料区域" +
                ", desc=用来存储原材料的区域" +
                ", isUse=false" +
                "}");

        changeableInfo = changeableInfo.enableUse();
        Assert.assertEquals(changeableInfo.toString(),"ChangeableInfo{"+
                "name=原材料区域" +
                ", desc=用来存储原材料的区域" +
                ", isUse=true" +
                "}");

        changeableInfo = changeableInfo.disableUse();
        Assert.assertEquals(changeableInfo.toString(),"ChangeableInfo{"+
                "name=原材料区域" +
                ", desc=用来存储原材料的区域" +
                ", isUse=false" +
                "}");
    }
}
