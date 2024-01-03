package org.ddd.fundamental.core.validation.impl;

import org.ddd.fundamental.core.validation.Validation;

public class ValidationBase implements Validation {

    /**
     * 这里是领域模型的具体验证方法,
     * 实际上就是子类添加验证规则,然后交给RuleManager来验证
     */
    public final void validate() {
        RuleManager ruleManager = new RuleManager();
        this.addRule(ruleManager);
        ruleManager.validate();
    }

    /**
     * 增加验证规则
     * @param ruleManager 验证规则管理器
     */
    protected void addRule(RuleManager ruleManager) {

    }
}
