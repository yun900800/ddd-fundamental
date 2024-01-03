package org.ddd.fundamental.validation.base;

import org.junit.Assert;
import org.junit.Test;

public class CompositeParameterValidateResultTest {

    @Test
    public void testCreateCompositeParameterValidateResult() {
        CompositeParameterValidateResult result =
                new CompositeParameterValidateResult(true,"success");
        Assert.assertEquals(result.isSuccess(),true);
        Assert.assertEquals(result.getMessage(),"success");
    }

    @Test
    public void testCompositeParameterValidateAddResult() {
        CompositeParameterValidateResult result =
                new CompositeParameterValidateResult(true,"success");
        Assert.assertEquals(result.isSuccess(),true);
        Assert.assertEquals(result.getMessage(),"success");
        result.addValidationResult(ParameterValidationResult.success());
        Assert.assertEquals(result.getResultList().size(),1);
        Assert.assertEquals(result.getResultList().get(0).getMessage(),"success");
    }
}
