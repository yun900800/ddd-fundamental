package org.ddd.fundamental.validation.base;

import org.ddd.fundamental.validation.rule.impl.RuleManager;

public abstract class DomainModel<T> extends ValidateBase<T> {


    protected void addRule(RuleManager<T> ruleManager) {
    }
}
