package org.ddd.fundamental.validation.base.impl;

import org.ddd.fundamental.validation.base.CompositeParameterValidateResult;
import org.ddd.fundamental.validation.base.DomainModel;
import org.ddd.fundamental.validation.base.ParameterValidationResult;
import org.ddd.fundamental.validation.rule.RuleBase;
import org.ddd.fundamental.validation.rule.impl.EmbeddedObjectRule;
import org.ddd.fundamental.validation.rule.impl.RuleManager;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;


public class RuleManagerTest {

    @Test
    public void testCreateRuleManager() {
        DomainModel domainModel = Mockito.mock(DomainModel.class);
        RuleManager ruleManager = new RuleManager(domainModel);
        Assert.assertEquals(ruleManager.getRules().size(),0);
        Assert.assertNotNull(ruleManager.getOwner());
    }

    @Test
    public void testRuleManagerAddRule() {
        DomainModel domainModel = Mockito.mock(DomainModel.class);
        RuleBase rule = Mockito.mock(RuleBase.class);
        RuleManager ruleManager = new RuleManager(domainModel);
        ruleManager.addRule(rule);
        Assert.assertEquals(ruleManager.getRules().size(),1);
        ruleManager.addRule(null);
        Assert.assertEquals(ruleManager.getRules().size(),1);
    }

    @Test
    public void testRuleManagerValidateSuccess() {
        DomainModel domainModel = Mockito.mock(DomainModel.class);
        RuleBase rule = Mockito.mock(RuleBase.class);
        when(rule.validate()).thenReturn(ParameterValidationResult.success());
        RuleManager ruleManager = new RuleManager(domainModel);
        ruleManager.addRule(rule);
        ParameterValidationResult result = ruleManager.validate();
        verify(rule,times(1)).validate();
        Assert.assertEquals(result.isSuccess(),true);
        Assert.assertEquals(((CompositeParameterValidateResult)result)
                .getResultList().size(),0);
    }

    @Test
    public void testRuleManagerValidateFailed() {
        DomainModel domainModel = Mockito.mock(DomainModel.class);
        RuleBase rule = Mockito.mock(RuleBase.class);
        when(rule.validate()).thenReturn(ParameterValidationResult.failed());
        RuleManager ruleManager = new RuleManager(domainModel);
        ruleManager.addRule(rule);
        ParameterValidationResult result = ruleManager.validate();
        verify(rule,times(1)).validate();
        Assert.assertEquals(result.isSuccess(),false);
        Assert.assertEquals(((CompositeParameterValidateResult)result)
                .getResultList().size(),1);
    }

    @Test
    public void testRuleManagerEmbeddedValidate() {
        DomainModel domainModel = Mockito.mock(DomainModel.class);
        when(domainModel.validate()).thenReturn(ParameterValidationResult.failed());
        RuleBase rule = Mockito.mock(RuleBase.class);
        when(rule.validate()).thenReturn(ParameterValidationResult.failed());
        EmbeddedObjectRule embeddedObjectRule = Mockito.mock(EmbeddedObjectRule.class);
        when(embeddedObjectRule.getTarget()).thenReturn(domainModel);
        RuleManager ruleManager = new RuleManager(domainModel);
        ruleManager.addRule(embeddedObjectRule);
        ruleManager.addRule(rule);
        ParameterValidationResult result = ruleManager.validate();
        verify(rule,times(1)).validate();
        verify(domainModel,times(1)).validate();
        Assert.assertEquals(result.isSuccess(),false);
        Assert.assertEquals(((CompositeParameterValidateResult)result)
                .getResultList().size(),2);
    }

}
