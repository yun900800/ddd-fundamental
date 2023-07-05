package org.ddd.fundamental.validation.rule;

import org.ddd.fundamental.validation.base.DomainModel;
import org.ddd.fundamental.validation.rule.impl.AndRule;
import org.ddd.fundamental.validation.rule.impl.OrRule;

public abstract class RuleBase<TTarget extends DomainModel> implements Rule {

    //验证的目标
    private TTarget target;
    //验证目标的名称
    private String nameOfTarget;
    //当规验证失败时的错误提示信息
    private String customErrorMessage = "";

    /**
     * 规则基类
     * @param nameOfTarget 验证目标的名称
     * @param target 验证的目标
     */
    protected RuleBase(String nameOfTarget, TTarget target){
        this(nameOfTarget, target, new String(""));
    }

    protected RuleBase(String nameOfTarget, TTarget target, String customErrorMessage) {
        this.customErrorMessage = customErrorMessage;
        this.nameOfTarget = nameOfTarget;
        this.target = target;
    }

    /**
     * 与操作
     * @param rule 目标规则
     * @return 与后的规则
     */
    @Override
    public Rule and(Rule rule) {
        return new AndRule(this, (RuleBase)rule);
    }

    /**
     * 或操作
     *
     * @param rule 目标规则
     * @return 或后的规则
     */
    @Override
    public Rule or(Rule rule) {
        return new OrRule(this, (RuleBase)rule);
    }

    public TTarget getTarget() {
        return target;
    }

    public String getNameOfTarget() {
        return nameOfTarget;
    }

    public String getCustomErrorMessage() {
        return customErrorMessage;
    }

    protected abstract String getDefaultErrorMessage();
}
