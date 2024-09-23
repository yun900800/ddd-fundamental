package org.ddd.fundamental.validation.base;

import org.ddd.fundamental.validation.Validatable;
import org.ddd.fundamental.validation.rule.impl.RuleManager;

public abstract class ValidateBase<T> implements Validatable {

    /**
     * 验证当前领域模型
     * @return 验证的结果
     */
    final public  ParameterValidationResult validate() {
        RuleManager ruleManager = new RuleManager((DomainModel<T>)this);
        this.addRule(ruleManager);
        return ruleManager.validate();
    }

    /**
     * 增加验证规则
     * 这个方法其实是对外提供的一个接口,而这个接口需要子类类实现
     * @param ruleManager 验证规则管理器
     */
    abstract void addRule(RuleManager<T> ruleManager);
}
