package org.ddd.fundamental.validation.external;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ParameterValidatorsTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void testCreateParameterValidators() {
        Assert.assertEquals(ParameterValidators.build().getParameters().size(),0);
    }

    @Test
    public void testAddVoRule() {
        EmptyVO vo = new EmptyVO();
        ParameterValidators parameterValidators = ParameterValidators.build().addVoRule(vo,"vo is null");
        Assert.assertEquals(parameterValidators.getParameters().size(),1);
        parameterValidators.validate();
    }

    @Test
    public void testAddVoRuleException() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("vo is null");
        ParameterValidators parameterValidators = ParameterValidators.build().addVoRule(null,"vo is null");
        parameterValidators.validate();
    }

    @Test
    public void testStringNotNullRule() {
        ParameterValidators parameterValidators = ParameterValidators.build().addStringNotNullRule("yun900800","string is null");
        Assert.assertEquals(parameterValidators.getParameters().size(),1);
        parameterValidators.validate();
    }
    @Test
    public void testStringNotNullRuleException() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("string is null");
        ParameterValidators parameterValidators = ParameterValidators.build().addStringNotNullRule(null,"string is null");
        Assert.assertEquals(parameterValidators.getParameters().size(),1);
        parameterValidators.validate();
    }
    @Test
    public void testObjectNotNullRule() {
        EmptyVO vo = new EmptyVO();
        ParameterValidators parameterValidators = ParameterValidators.build().addObjectNotNullRule(vo,"obj is null");
        Assert.assertEquals(parameterValidators.getParameters().size(),1);
        parameterValidators.validate();
    }
    @Test
    public void testObjectNotNullRuleException() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("obj is null");
        ParameterValidators parameterValidators = ParameterValidators.build().addObjectNotNullRule(null,"obj is null");
        Assert.assertEquals(parameterValidators.getParameters().size(),1);
        parameterValidators.validate();
    }


}

class EmptyVO extends VOBase {

}
