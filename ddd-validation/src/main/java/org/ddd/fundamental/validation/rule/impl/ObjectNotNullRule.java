package org.ddd.fundamental.validation.rule.impl;

import org.ddd.fundamental.validation.base.DomainModel;
import org.ddd.fundamental.validation.base.ParameterValidationResult;
import org.ddd.fundamental.validation.rule.RuleBase;

public class ObjectNotNullRule extends RuleBase<DomainModel> {

    private Object target;

    /**
     * 获取验证失败时缺省的错误提示信息
     */
    @Override
    protected String getDefaultErrorMessage() {
        return String.format("%s为空对象", this.getNameOfTarget());
    }

    /**
     * 对象非空规则
     * @param nameOfTarget 验证目标的名称
     * @param target       验证的目标
     */
    public ObjectNotNullRule(String nameOfTarget, DomainModel target) {
        this(nameOfTarget, target, nameOfTarget+" is null");
    }

    public ObjectNotNullRule(String nameOfTarget, DomainModel target, String customErrorMessage) {
        super(nameOfTarget, target, customErrorMessage);
    }

    public ObjectNotNullRule(String nameOfTarget, String target) {
        super(nameOfTarget, null, nameOfTarget+" is null");
        this.target = target;
    }

    /**
     * 执行验证
     * @return 验证是否成功
     */
    @Override
    public ParameterValidationResult validate() {
        if(this.getTarget() == null && this.target == null){
            return ParameterValidationResult.failed(this.getDefaultErrorMessage());
        }
        return ParameterValidationResult.success();
    }
}
