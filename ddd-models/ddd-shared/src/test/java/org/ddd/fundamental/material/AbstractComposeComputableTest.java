package org.ddd.fundamental.material;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class AbstractComposeComputableTest {

    private List<MaterialRecordValue> init(){
        List<MaterialRecordValue> materialRecordList = new ArrayList<>();
        MaterialRecordValue materialRecord = new MaterialRecordValue(ComputableTest.createMaterialMaster(),5);
        materialRecordList.add(materialRecord);
        MaterialRecordValue materialRecord1 = new MaterialRecordValue(ComputableTest.createMaterialMaster(),5);
        materialRecordList.add(materialRecord1);
        MaterialRecordValue materialRecord2 = new MaterialRecordValue(ComputableTest.createMaterialMaster(),5);
        materialRecordList.add(materialRecord2);
        return materialRecordList;
    }

    @Test
    public void testCreateAbstractComposeComputable() {
        List<MaterialRecordValue> materialRecordList = init();
        ComposeMaterialRecord computableObject = new ComposeMaterialRecord(materialRecordList);
        Assert.assertEquals(computableObject.getUnit(),"颗");
        Assert.assertEquals(computableObject.getQty(),15,0);

        List<ComposeMaterialRecord> dataList = computableObject.split(computableObject);
        System.out.println(dataList);
    }
}

