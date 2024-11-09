package org.ddd.fundamental.material.domain.value.impl;

import org.ddd.fundamental.material.domain.value.MaterialBatchValue;
import org.ddd.fundamental.workprocess.ProcessOutput;

import java.util.ArrayList;
import java.util.List;

public record MaterialBatchOutput(List<MaterialBatchValue> outputBatch) implements ProcessOutput {

    @Override
    public List<MaterialBatchValue> outputBatch() {
        return new ArrayList<>(outputBatch);
    }

    public boolean isSingleBatch(){
        return outputBatch.size() == 1;
    }

    public MaterialBatchValue singleBatch(){
        return outputBatch.get(0);
    }
}
