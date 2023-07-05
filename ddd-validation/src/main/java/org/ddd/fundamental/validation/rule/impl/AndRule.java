package org.ddd.fundamental.validation.rule.impl;

import org.ddd.fundamental.validation.base.ParameterValidationResult;
import org.ddd.fundamental.validation.rule.Rule;

public class AndRule implements Rule {

    private Rule rule;

    private Rule other;

    public AndRule(Rule rule,Rule other) {
        this.rule = rule;
        this.other = other;
    }
    @Override
    public ParameterValidationResult validate() {
        ParameterValidationResult bLeftResult = this.rule.validate();
        ParameterValidationResult bRightResult = this.other.validate();
        if (bLeftResult.isSuccess() && bRightResult.isSuccess()) {
            return ParameterValidationResult.success();
        }
        if (!bLeftResult.isSuccess()) {
            return ParameterValidationResult.failed(bLeftResult.getMessage());
        }
        return ParameterValidationResult.failed(bRightResult.getMessage());
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
