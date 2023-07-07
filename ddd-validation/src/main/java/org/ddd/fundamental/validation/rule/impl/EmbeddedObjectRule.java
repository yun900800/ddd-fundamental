package org.ddd.fundamental.validation.rule.impl;

import org.ddd.fundamental.validation.base.DomainModel;
import org.ddd.fundamental.validation.base.ParameterValidationResult;
import org.ddd.fundamental.validation.rule.RuleBase;

public class EmbeddedObjectRule extends RuleBase<DomainModel> {

    private final DomainModel target;
    /**
     * 规则基类
     *
     * @param nameOfTarget 验证目标的名称
     * @param domainModel  验证的目标
     */
    public EmbeddedObjectRule(String nameOfTarget, DomainModel domainModel) {
        super(nameOfTarget, domainModel);
        this.target = domainModel;
    }

    @Override
    public ParameterValidationResult validate() {
        return this.target.validate();
    }

    public DomainModel getTarget() {
        return this.target;
    }

    @Override
    protected String getDefaultErrorMessage() {
        return String.format("%s为空对象", this.getNameOfTarget());
    }
}
