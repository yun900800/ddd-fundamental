package org.ddd.fundamental.material;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class AbstractComposeComputableTest {

    private List<MaterialRecordValueObject> init(){
        List<MaterialRecordValueObject> materialRecordList = new ArrayList<>();
        MaterialRecordValueObject materialRecord = new MaterialRecordValueObject(ComputableTest.createMaterialMaster(),5);
        materialRecordList.add(materialRecord);
        MaterialRecordValueObject materialRecord1 = new MaterialRecordValueObject(ComputableTest.createMaterialMaster(),5);
        materialRecordList.add(materialRecord1);
        MaterialRecordValueObject materialRecord2 = new MaterialRecordValueObject(ComputableTest.createMaterialMaster(),5);
        materialRecordList.add(materialRecord2);
        return materialRecordList;
    }

    @Test
    public void testCreateAbstractComposeComputable() {
        List<MaterialRecordValueObject> materialRecordList = init();
        ComposeMaterialRecord computableObject = new ComposeMaterialRecord(materialRecordList);
        Assert.assertEquals(computableObject.getUnit(),"颗");
        Assert.assertEquals(computableObject.getQty(),15,0);

        List<ComposeMaterialRecord> dataList = computableObject.split(computableObject);
        System.out.println(dataList);
    }
}

