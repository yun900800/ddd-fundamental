package org.ddd.fundamental.validation.base;

import org.ddd.fundamental.validation.rule.RuleManager;

public abstract class DomainModel<T> extends ValidatableBase {


    protected void addRule(RuleManager ruleManager) {
    }
}