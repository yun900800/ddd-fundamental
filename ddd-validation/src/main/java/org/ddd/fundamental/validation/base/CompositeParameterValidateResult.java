package org.ddd.fundamental.validation.base;

import java.util.ArrayList;
import java.util.List;

public class CompositeParameterValidateResult extends ParameterValidationResult{

    private List<ParameterValidationResult> resultList = new ArrayList<>();

    public CompositeParameterValidateResult(boolean success, String message) {
        super(success, message);
    }
    public CompositeParameterValidateResult() {
        super(true, "success");
    }

    @Override
    public boolean isSuccess() {
        return super.isSuccess();
    }

    @Override
    public void fail() {
        super.fail();
    }

    public void addValidationResult(ParameterValidationResult result) {
        this.resultList.add(result);
    }

    public List<ParameterValidationResult> getResultList() {
        return resultList;
    }
}
