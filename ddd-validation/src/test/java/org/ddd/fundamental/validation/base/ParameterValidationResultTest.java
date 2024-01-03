package org.ddd.fundamental.validation.base;

import org.junit.Assert;
import org.junit.Test;

public class ParameterValidationResultTest {

    @Test
    public void testCreateParameterValidationResult() {
        ParameterValidationResult result =
                new ParameterValidationResult(true,"消息验证成功");
        Assert.assertEquals(result.isSuccess(),true);
        Assert.assertEquals(result.getMessage(),"消息验证成功");
    }

    @Test
    public void testStaticCreateParameterValidationResult() {
        ParameterValidationResult success = ParameterValidationResult.success();
        Assert.assertEquals(success.isSuccess(),true);
        Assert.assertEquals(success.getMessage(),"success");
        success = ParameterValidationResult.success("XXX领域模型验证成功");
        Assert.assertEquals(success.isSuccess(),true);
        Assert.assertEquals(success.getMessage(),"XXX领域模型验证成功");
        ParameterValidationResult failed = ParameterValidationResult.failed("XXX领域模型验证失败");
        Assert.assertEquals(failed.isSuccess(),false);
        Assert.assertEquals(failed.getMessage(),"XXX领域模型验证失败");

        failed = ParameterValidationResult.failed();
        Assert.assertEquals(failed.isSuccess(),false);
        Assert.assertEquals(failed.getMessage(),"failed");
    }

    @Test
    public void testMakeParameterValidationResult() {
        ParameterValidationResult success = ParameterValidationResult.success();
        Assert.assertEquals(success.isSuccess(),true);
        Assert.assertEquals(success.getMessage(),"success");
        success.fail("XXX模型验证失败");
        Assert.assertEquals(success.isSuccess(),false);
        Assert.assertEquals(success.getMessage(),"XXX模型验证失败");
    }
}
