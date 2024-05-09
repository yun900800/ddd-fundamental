package org.ddd.fundamental.domain.impl;

import org.ddd.fundamental.domain.IItem;
import org.junit.Assert;
import org.junit.Test;

public class ItemTest {

    @Test
    public void testItemCopy() {
        IItem<String> material = new RawMaterial("原材料",100);
        IItem<String> copyMaterial = Item.copy(material);
        Assert.assertEquals(material.key(),copyMaterial.key());
    }
}
