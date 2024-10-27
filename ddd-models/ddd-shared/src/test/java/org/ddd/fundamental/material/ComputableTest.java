package org.ddd.fundamental.material;

import org.ddd.fundamental.core.generator.Generators;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ComputableTest {

    @Test
    public void testSplit(){
        MaterialRecord1 materialRecord = new MaterialRecord1("颗",10.0);
        SplitStrategy<MaterialRecord1> strategy = new DividedTwoStrategyByRaw1();
        List<MaterialRecord1> splits = materialRecord.split(materialRecord,strategy);
        Assert.assertEquals(splits.size(),2);
        Assert.assertEquals(splits.get(0).getQty(),5,0);
    }

    @Test
    public void testSplitByClass() {
        MaterialRecord1 materialRecord = new MaterialRecord1("颗",10.0);
        List<MaterialRecord1> splits = materialRecord.split(materialRecord);
        Assert.assertEquals(splits.size(),2);
        Assert.assertEquals(splits.get(0).getQty(),5,0);
    }
}

class DividedTwoStrategyByRaw1 implements SplitStrategy<MaterialRecord1>{
    @Override
    public List<MaterialRecord1> split(MaterialRecord1 object) {
        List<MaterialRecord1> result = new ArrayList<>();
        int n = 2;
        Generators.fill(result, () ->  new MaterialRecord1(object.getUnit(),
                object.getQty()/n),n);
        return result;
    }
}




