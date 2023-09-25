package org.ddd.fundamental.core.domain;

import org.ddd.fundamental.core.validation.impl.RuleManager;
import org.ddd.fundamental.core.validation.impl.ValidationBase;

public abstract class DomainModel extends ValidationBase {

    /**
     * 初始化当前状态
     */
    public void initializeForNewCreation() {

    }

    protected void addRule(RuleManager ruleManager) {
    }
}
