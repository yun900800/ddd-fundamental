package org.ddd.fundamental.validation.rule;

import org.ddd.fundamental.validation.base.DomainModel;
import org.ddd.fundamental.validation.base.ParameterValidationResult;
import org.ddd.fundamental.validation.rule.impl.AndRule;
import org.ddd.fundamental.validation.rule.impl.OrRule;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class RuleBaseTest {

    @Test
    public void testBaseRule() {
        DomainModel domainModel = Mockito.mock(DomainModel.class);
        SuccessRule successRule = new SuccessRule("success",domainModel);
        Assert.assertEquals(successRule.getTarget(),domainModel);
        Assert.assertEquals(successRule.getDefaultErrorMessage(),"success");
        Assert.assertEquals(successRule.validate().isSuccess(), true);
        Assert.assertEquals(successRule.validate().getMessage(), "success");
    }

    @Test
    public void testBaseRuleAndMethod() {
        DomainModel domainModel = Mockito.mock(DomainModel.class);
        SuccessRule successRule = new SuccessRule("success",domainModel);
        FailedRule failedRule = new FailedRule("failed",domainModel);
        Rule andRule = successRule.and(failedRule);
        Assert.assertEquals(((AndRule)andRule).getRule(),successRule);
        Assert.assertEquals(((AndRule)andRule).getOther(),failedRule);
        Assert.assertEquals(andRule.validate().getMessage(), "failed");
        Assert.assertEquals(andRule.validate().isSuccess(), false);

        Rule nextAndRule = andRule.and(successRule);
        Assert.assertEquals(((AndRule)nextAndRule).getRule(),andRule);
        Assert.assertEquals(((AndRule)nextAndRule).getOther(),successRule);

        Assert.assertEquals(successRule.and(successRule).validate().isSuccess(),true);
    }
    @Test
    public void testBaseRuleOrMethod() {
        DomainModel domainModel = Mockito.mock(DomainModel.class);
        SuccessRule successRule = new SuccessRule("success",domainModel);
        FailedRule failedRule = new FailedRule("failed",domainModel);
        Rule orRule = successRule.or(failedRule);
        Assert.assertEquals(((OrRule)orRule).getRule(),successRule);
        Assert.assertEquals(((OrRule)orRule).getOther(),failedRule);
        Assert.assertEquals(orRule.validate().getMessage(), "success");
        Assert.assertEquals(orRule.validate().isSuccess(), true);

        Rule nextOrRule = orRule.or(successRule);
        Assert.assertEquals(((OrRule)nextOrRule).getRule(),orRule);
        Assert.assertEquals(((OrRule)nextOrRule).getOther(),successRule);

        Assert.assertEquals(failedRule.or(failedRule).validate().isSuccess(),false);
    }
}

class SuccessRule extends RuleBase{

    protected SuccessRule(String nameOfTarget, DomainModel domainModel) {
        super(nameOfTarget, domainModel);
    }

    @Override
    public ParameterValidationResult validate() {
        return ParameterValidationResult.success();
    }

    @Override
    protected String getDefaultErrorMessage() {
        return "success";
    }
}

class FailedRule extends RuleBase {
    protected FailedRule(String nameOfTarget, DomainModel domainModel) {
        super(nameOfTarget, domainModel);
    }

    @Override
    public ParameterValidationResult validate() {
        return ParameterValidationResult.failed();
    }

    @Override
    protected String getDefaultErrorMessage() {
        return "failed";
    }
}
