package org.ddd.fundamental.domain.order.impl;

import org.ddd.fundamental.domain.order.IItem;
import org.ddd.fundamental.domain.order.IOrder;
import org.junit.Assert;
import org.junit.Test;

public class ItemTest {

    @Test
    public void testItemCopy() {
        IItem<String> material = new RawMaterial("原材料",100);
        IItem<String> copyMaterial = IOrder.copy(material);
        copyMaterial.setKey(material.key());
        Assert.assertEquals(material.key(),copyMaterial.key());
    }
}
