package org.ddd.fundamental.material.domain.value;

import org.ddd.fundamental.material.domain.enums.BatchType;
import org.ddd.fundamental.material.value.MaterialId;
import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MaterialBatchTest {

    @Test
    public void testCreateMaterialBatch(){
        MaterialId id = MaterialId.randomId(MaterialId.class);
        MaterialBatch materialBatchERP = new MaterialBatch(
                id,
                10, BatchType.ERP_BATCH
        );
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String pre = simpleDateFormat.format(date);
        String batchNo = pre+"_"+ id.toUUID()+ "_"+ BatchType.ERP_BATCH.getValue()+"_10";
        Assert.assertEquals(materialBatchERP.batchNo(),batchNo);
        Assert.assertEquals(materialBatchERP.materialId(),id);
        Assert.assertEquals(materialBatchERP.batchType(),BatchType.ERP_BATCH);
        Assert.assertTrue(materialBatchERP.canSplit());
        Assert.assertFalse(materialBatchERP.canMerge());

        MaterialBatch materialBatchPD = new MaterialBatch(
                id,
                20, BatchType.PRODUCT_BATCH
        );
        batchNo = pre+"_"+ id.toUUID()+"_"+ BatchType.PRODUCT_BATCH.getValue()+"_20";
        Assert.assertEquals(materialBatchPD.batchNo(),batchNo);
        Assert.assertEquals(materialBatchPD.materialId(),id);
        Assert.assertEquals(materialBatchPD.batchType(),BatchType.PRODUCT_BATCH);
        Assert.assertFalse(materialBatchPD.canSplit());
        Assert.assertFalse(materialBatchPD.canMerge());
    }
}
