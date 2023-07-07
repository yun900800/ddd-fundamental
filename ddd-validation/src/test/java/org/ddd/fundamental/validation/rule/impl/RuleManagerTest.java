package org.ddd.fundamental.validation.rule.impl;

import org.ddd.fundamental.validation.base.CompositeParameterValidateResult;
import org.ddd.fundamental.validation.base.DomainModel;
import org.ddd.fundamental.validation.base.ParameterValidationResult;
import org.ddd.fundamental.validation.rule.RuleBase;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class RuleManagerTest {

    @Test
    public void testRuleManagerCreate() {
        DomainModel domainModel = Mockito.mock(DomainModel.class);
        RuleManager ruleManager = new RuleManager(domainModel);
        Assert.assertEquals(ruleManager.getOwner(),domainModel);
        Assert.assertEquals(ruleManager.getRules().size(),0);
        ruleManager.addRule(new SuccessRule("success",domainModel));
        Assert.assertEquals(ruleManager.getRules().size(),1);
    }

    @Test
    public void testRuleManagerValidate() {
        DomainModel domainModel = Mockito.mock(DomainModel.class);
        RuleManager ruleManager = new RuleManager(domainModel);
        ruleManager.addRule(new SuccessRule("success",domainModel));
        Assert.assertEquals(ruleManager.validate().isSuccess(),true);
        ruleManager.addRule(new FailedRule("failed",domainModel));
        Assert.assertEquals(ruleManager.validate().isSuccess(),false);
        Assert.assertEquals(((CompositeParameterValidateResult)ruleManager.validate()).getResultList().size(),1);
    }

    @Test
    public void testRuleManagerAddEmbedObjectRule() {
        DomainModel domainModel = Mockito.mock(DomainModel.class);
        Mockito.when(domainModel.validate()).thenReturn(ParameterValidationResult.success());
        EmbeddedObjectRule embeddedObjectRule = Mockito.mock(EmbeddedObjectRule.class);
        Mockito.when(embeddedObjectRule.getTarget()).thenReturn(domainModel);
        RuleManager ruleManager = new RuleManager(domainModel);
        ruleManager.addRule(embeddedObjectRule);
        Assert.assertEquals(ruleManager.validate().isSuccess(),true);
        verify(embeddedObjectRule, times(1)).getTarget();
        verify(domainModel).validate();
    }
}

class SuccessRule extends RuleBase {

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
