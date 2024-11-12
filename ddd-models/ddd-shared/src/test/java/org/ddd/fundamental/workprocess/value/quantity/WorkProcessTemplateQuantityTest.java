package org.ddd.fundamental.workprocess.value.quantity;

import org.junit.Assert;
import org.junit.Test;

public class WorkProcessTemplateQuantityTest {

    @Test
    public void testCreateWorkProcessTemplateQuality(){
        WorkProcessTemplateQuantity quality = WorkProcessTemplateQuantity.newBuilder()
                .targetQualifiedRate(98.5).noTransferPercent().build();
        Assert.assertEquals(quality.getTargetQualifiedRate(), 98.5,0.1);
        Assert.assertNull(quality.getOverCrossPercent());
        Assert.assertNull(quality.getTransferPercent());
        Assert.assertNull(quality.getOwePaymentPercent());
        WorkProcessTemplateQuantity quality1 = WorkProcessTemplateQuantity.newBuilder()
                .targetQualifiedRate(98.5).transferPercent(95.6)
                .overCrossPercent(115.0).build();
        Assert.assertEquals(quality1.getTargetQualifiedRate(), 98.5,0.1);
        Assert.assertEquals(quality1.getOverCrossPercent(),115.0,0.1);
        Assert.assertEquals(quality1.getTransferPercent(),95.6,0.1);
        Assert.assertNull(quality1.getOwePaymentPercent());

        WorkProcessTemplateQuantity quality2 = WorkProcessTemplateQuantity.newBuilder()
                .targetQualifiedRate(98.5).transferPercent(95.6)
                .noDeliver().build();
        System.out.println(quality2);
    }
}
