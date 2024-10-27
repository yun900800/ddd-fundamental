package org.ddd.fundamental.material;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class AbstractComposeComputableTest {

    private List<MaterialRecord1> init(){
        List<MaterialRecord1> materialRecordList = new ArrayList<>();
        MaterialRecord1 materialRecord = new MaterialRecord1("颗",5);
        materialRecordList.add(materialRecord);
        MaterialRecord1 materialRecord1 = new MaterialRecord1("颗",5);
        materialRecordList.add(materialRecord1);
        MaterialRecord1 materialRecord2 = new MaterialRecord1("颗",5);
        materialRecordList.add(materialRecord2);
        return materialRecordList;
    }

    @Test
    public void testCreateAbstractComposeComputable() {
        List<MaterialRecord1> materialRecordList = init();
        ComposeMaterialRecord1 computableObject = new ComposeMaterialRecord1(materialRecordList);
        Assert.assertEquals(computableObject.getUnit(),"颗");
        Assert.assertEquals(computableObject.getQty(),15,0);

        List<ComposeMaterialRecord1> dataList = computableObject.split(computableObject);
        System.out.println(dataList);
    }
}

