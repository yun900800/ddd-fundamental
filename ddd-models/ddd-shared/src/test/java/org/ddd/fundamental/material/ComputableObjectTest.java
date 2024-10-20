package org.ddd.fundamental.material;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ComputableObjectTest {

    @Test
    public void testCreateComputableObject(){
        ComputableObject computableObject = new MaterialRecord("颗",10.0);
        Assert.assertEquals(computableObject.getQty(),10.0,1);
        Assert.assertEquals(computableObject.getUnit(),"颗");
        Assert.assertTrue(computableObject.getQrCode().length()==36);
    }

    @Test
    public void testSplitComputableObjectByClass(){
        MaterialRecord materialRecord = new MaterialRecord("颗",10.0);
        List<MaterialRecord> splits = MaterialRecord.split(materialRecord);
        Assert.assertTrue(splits.size()==2);
        Assert.assertEquals(splits.get(0).getQty(),5,0);
        Assert.assertEquals(splits.get(0).getUnit(),"颗");

        Assert.assertFalse(splits.get(0).getQrCode().equals(splits.get(1).getQrCode()));
    }

    @Test
    public void testSplitComputableObject(){
        MaterialRecord materialRecord = new MaterialRecord("颗",10.0);
        List<ComputableObject> splits = MaterialRecord.split1(materialRecord);
        Assert.assertTrue(splits.size()==2);
        Assert.assertEquals(splits.get(0).getQty(),5,0);
        Assert.assertEquals(splits.get(0).getUnit(),"颗");

        Assert.assertFalse(splits.get(0).getQrCode().equals(splits.get(1).getQrCode()));
    }

    @Test
    public void testSplitComputableObjectByParameter(){
        MaterialRecord materialRecord = new MaterialRecord("颗",10.0);
        SplitStrategy<MaterialRecord> strategy = new DividedTwoStrategyByRaw();
        List<MaterialRecord> splits = materialRecord.split2(materialRecord,strategy);
        Assert.assertTrue(splits.size()==2);
        Assert.assertEquals(splits.get(0).getQty(),5,0);
        Assert.assertEquals(splits.get(0).getUnit(),"颗");

        Assert.assertFalse(splits.get(0).getQrCode().equals(splits.get(1).getQrCode()));

    }


}

