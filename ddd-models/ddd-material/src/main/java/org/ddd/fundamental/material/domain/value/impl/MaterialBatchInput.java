package org.ddd.fundamental.material.domain.value.impl;

import org.ddd.fundamental.material.domain.value.MaterialBatchValue;
import org.ddd.fundamental.workprocess.ProcessInput;

import java.util.ArrayList;
import java.util.List;

public record MaterialBatchInput(List<MaterialBatchValue> inputBatch) implements ProcessInput {

    @Override
    public List<MaterialBatchValue> inputBatch() {
        return new ArrayList<>(inputBatch);
    }

    public boolean isSingleBatch(){
        return inputBatch.size() == 1;
    }

    public MaterialBatchValue singleBatch(){
        return inputBatch.get(0);
    }
}
