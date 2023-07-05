package org.ddd.fundamental.validation.base;

import org.ddd.fundamental.validation.Validatable;
import org.ddd.fundamental.validation.rule.impl.RuleManager;

public abstract class ValidatableBase implements Validatable {

    /**
     * 验证当前领域模型
     * @return 验证的结果
     */
    final public ParameterValidationResult validate() {
        RuleManager ruleManager = new RuleManager((DomainModel)this);
        this.addRule(ruleManager);
        return ruleManager.validate();
    }

    /**
     * 增加验证规则
     * @param ruleManager 验证规则管理器
     */
    protected void addRule(RuleManager ruleManager) {

    }
}
