package org.ddd.fundamental.helper;

import org.ddd.fundamental.domain.order.IItem;
import org.ddd.fundamental.domain.order.impl.RawMaterial;
import org.junit.Assert;
import org.junit.Test;

public class DeepCopyUtilTest {
    @Test
    public void testCopyObject(){
        IItem<String> item = new RawMaterial("raw",60);
        IItem<String> copy = DeepCopyUtil.deepCopy(item);
        Assert.assertEquals(copy.name(),"raw");
        Assert.assertEquals(copy.quantity(),60,0);
        Assert.assertNotNull(copy.key());
    }
}
