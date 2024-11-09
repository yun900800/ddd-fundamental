package org.ddd.fundamental.material.domain.value;

import org.ddd.fundamental.material.domain.enums.BatchType;
import org.ddd.fundamental.material.value.MaterialId;
import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Stack;

public class MaterialBatchTest {

    @Test
    public void testCreateMaterialBatch(){
        MaterialId id = MaterialId.randomId(MaterialId.class);
        MaterialBatchValue materialBatchERP = new MaterialBatchValue(
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

        MaterialBatchValue materialBatchPD = new MaterialBatchValue(
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

    @Test
    public void testCreateBatchNoWithDifferentStrategy(){
        MaterialId id = MaterialId.randomId(MaterialId.class);
        MaterialBatchValue materialBatchERP = new MaterialBatchValue(
                id,
                10, BatchType.ERP_BATCH
        );
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String pre = simpleDateFormat.format(date);
        String batchNo = pre+"_"+ id.toUUID()+ "_"+ BatchType.ERP_BATCH.getValue()+"_10" + "_SB_6999_XG_688";
        materialBatchERP.addCommonProps("spec","XG_688");
        materialBatchERP.addCommonProps("equipment","SB_6999");
        materialBatchERP.changeStrategy(new BatchNoGenerator());
        String resultBatchNo = materialBatchERP.batchNo();
        Assert.assertEquals(resultBatchNo,batchNo);
    }


}

class BatchNoGenerator implements IBatchNoGenerateStrategy{

    @Override
    public String generate(IBatch batch) {
        String defaultBatchNo = defaultBatchNo(batch.materialId(),batch.batchNumber(),batch.batchType());
        String extendBatchNo = extendBatchNo(batch.commonProps(),batch.specialProps());
        return defaultBatchNo + extendBatchNo;
    }

    private static String extendBatchNo(Map<String,String> commonProps,
                                        Map<String,String> specialProps){
        String result = "_";
        for (Map.Entry<String,String> entry: commonProps.entrySet()) {
            result+= entry.getValue();
            result+="_";
        }
        for (Map.Entry<String,String> entry: specialProps.entrySet()) {
            result+= entry.getValue();
            result+="_";
        }
        return result.substring(0,result.length()-1);
    }

    private static String defaultBatchNo(MaterialId id, int batchNumber,BatchType batchType){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String pre = simpleDateFormat.format(date);
        String materialId = id.toUUID();
        return pre + "_" + materialId +"_" +batchType.getValue()+ "_"  + batchNumber;
    }
}
