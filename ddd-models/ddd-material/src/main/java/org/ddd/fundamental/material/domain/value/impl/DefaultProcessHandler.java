package org.ddd.fundamental.material.domain.value.impl;

import org.ddd.fundamental.material.domain.enums.BatchType;
import org.ddd.fundamental.material.domain.value.MaterialBatchValue;
import org.ddd.fundamental.workprocess.IWorkProcessHandler;
import org.ddd.fundamental.workprocess.ProcessInput;
import org.ddd.fundamental.workprocess.ProcessOutput;

import java.util.Arrays;

/**
 * 工序的输入和输出有点类似于结构一致
 * 存在不同的处理方式
 * 比如都是处理的物料批次,但是对物料批次有不同的处理策略
 * 所以类似于访问者模式
 */
public class DefaultProcessHandler implements IWorkProcessHandler {

    private MaterialBatchInput inputs;


    @Override
    public void input(ProcessInput input) {
        inputs = (MaterialBatchInput)input;

        if (!inputs.isSingleBatch()) {
            throw new RuntimeException("输入批次不能多余一个");
        }
        MaterialBatchValue batch = inputs.singleBatch();
        if (!batch.batchType().equals(BatchType.INPUT_BATCH)){
            throw new RuntimeException("输入批次的类型不对");
        }
    }

    @Override
    public ProcessOutput output() {
        MaterialBatchValue value = inputs.singleBatch();
        value.changeBatchType(BatchType.OUTPUT_BATCH);
        MaterialBatchOutput output = new MaterialBatchOutput(Arrays.asList(value));
        //修改批次类型
        //修改批次号
        // 分解数量
        return output;
    }
}
