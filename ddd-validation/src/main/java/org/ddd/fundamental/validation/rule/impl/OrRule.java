package org.ddd.fundamental.validation.rule.impl;

import org.ddd.fundamental.validation.base.ParameterValidationResult;
import org.ddd.fundamental.validation.rule.Rule;

public class OrRule implements Rule {

    private Rule rule;

    private Rule other;

    public OrRule(Rule rule,Rule other) {
        this.rule = rule;
        this.other = other;
    }
    @Override
    public ParameterValidationResult validate() {
        ParameterValidationResult bLeftResult = this.rule.validate();
        ParameterValidationResult bRightResult = this.other.validate();
        if (bLeftResult.isSuccess() || bRightResult.isSuccess()) {
            return ParameterValidationResult.success();
        }
        return ParameterValidationResult.failed(bLeftResult.getMessage()
                + "||"+ bRightResult.getMessage());
    }

    @Override
    public Rule and(Rule rule) {
        return new AndRule(this, rule);
    }

    @Override
    public Rule or(Rule rule) {
        return new OrRule(this, rule);
    }
}
