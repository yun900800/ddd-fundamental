package org.ddd.fundamental.validation.rule;

import org.ddd.fundamental.validation.base.DomainModel;
import org.ddd.fundamental.validation.base.ParameterValidationResult;

public class LE extends RuleBase<DomainModel>{

    private int expected;

    private int actual;

    public LE(String nameOfTarget, DomainModel target, int expected) {
        super(nameOfTarget,target,"");
        this.expected = expected;
    }

    public LE(String nameOfTarget, int actual, int expected) {
        super(nameOfTarget,null,"");
        this.expected = expected;
        this.actual = actual;
    }
    @Override
    public ParameterValidationResult validate() {
        if (this.actual <= this.expected) {
            return ParameterValidationResult.failed(this.getDefaultErrorMessage());
        }
        return ParameterValidationResult.success();
    }

    @Override
    protected String getDefaultErrorMessage() {
        return String.format("%s属性验证%s > %s验证失败。", this.getNameOfTarget(), this.actual, this.expected);
    }
}
