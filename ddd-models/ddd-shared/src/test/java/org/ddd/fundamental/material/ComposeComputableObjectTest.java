package org.ddd.fundamental.material;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ComposeComputableObjectTest {

    private List<MaterialRecord> init(){
        List<MaterialRecord> materialRecordList = new ArrayList<>();
        MaterialRecord materialRecord = new MaterialRecord("颗",5);
        materialRecordList.add(materialRecord);
        MaterialRecord materialRecord1 = new MaterialRecord("颗",5);
        materialRecordList.add(materialRecord1);
        MaterialRecord materialRecord2 = new MaterialRecord("颗",5);
        materialRecordList.add(materialRecord2);
        return materialRecordList;
    }

    @Test
    public void testCreateComposeComputableObject() {
        List<MaterialRecord> materialRecordList = init();

        ComposeComputableObject<MaterialRecord> computableObject = new ComposeComputableObject(materialRecordList);
        Assert.assertEquals(computableObject.getUnit(),"颗");
        Assert.assertEquals(computableObject.getQty(),15,0);
    }

    @Test
    public void testCreateComposeMaterialRecord(){
        List<MaterialRecord> materialRecordList = init();
        ComposeMaterialRecord composeMaterialRecord = new ComposeMaterialRecord(materialRecordList);
        Assert.assertEquals(composeMaterialRecord.getUnit(),"颗");
        Assert.assertEquals(composeMaterialRecord.getQty(),15,0);
    }
}

