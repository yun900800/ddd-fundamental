package org.ddd.fundamental.material;

import org.ddd.fundamental.core.generator.Generators;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ComputableTest {

    public static MaterialMaster createMaterialMaster(){
        MaterialMaster materialMaster =  MaterialMaster.create("XG-code","锡膏",
                "XG-spec-001","颗");
        return materialMaster;
    }

    @Test
    public void testSplit(){
        MaterialRecordValue materialRecord = new MaterialRecordValue(createMaterialMaster(),10.0);
        SplitStrategy<MaterialRecordValue> strategy = new DividedTwoStrategyByRaw1();
        List<MaterialRecordValue> splits = materialRecord.split(materialRecord,strategy);
        Assert.assertEquals(splits.size(),2);
        Assert.assertEquals(splits.get(0).getQty(),5,0);
    }

    @Test
    public void testSplitByClass() {
        MaterialRecordValue materialRecord = new MaterialRecordValue(createMaterialMaster(),10.0);
        List<MaterialRecordValue> splits = materialRecord.split(materialRecord);
        Assert.assertEquals(splits.size(),2);
        Assert.assertEquals(splits.get(0).getQty(),5,0);
    }
}

class DividedTwoStrategyByRaw1 implements SplitStrategy<MaterialRecordValue>{
    @Override
    public List<MaterialRecordValue> split(MaterialRecordValue object) {
        List<MaterialRecordValue> result = new ArrayList<>();
        int n = 2;
        Generators.fill(result, () ->  new MaterialRecordValue(object.getMaterialMaster(),
                object.getQty()/n),n);
        return result;
    }
}




