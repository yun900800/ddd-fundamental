package org.ddd.fundamental.bom.value;

import org.ddd.fundamental.material.MaterialMaster;
import org.junit.Assert;
import org.junit.Test;

public class ProductStructureNodeTest {

    @Test
    public void testCreateProductStructureNode(){
        ProductStructureNode node =
                ProductStructureNode.create(MaterialMaster.create(
                    "dell-xmp","戴尔电脑","dell-xmp-1","台"
                ),1);
        Assert.assertEquals(node.getQty(),1,0);
    }
}
