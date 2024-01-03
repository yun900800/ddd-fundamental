package org.ddd.fundamental.algorithms.basedata.stepbuilder;

import org.ddd.fundamental.algorithm.basedata.stepbuilder.RequestParam;
import org.ddd.fundamental.algorithm.basedata.stepbuilder.RequestParamStepBuilder;
import org.junit.Assert;
import org.junit.Test;

public class StepBuilderTest {

    @Test
    public void testPostStepBuilder() {
        RequestParam postParam = RequestParamStepBuilder.newBuilder().url("www.baidu.com")
                .postMethod("post").body("{}").build();
        Assert.assertEquals("www.baidu.com",postParam.getUrl());

        Assert.assertEquals("post",postParam.getMethod());
        Assert.assertEquals("{}",postParam.getBody());
    }

    @Test
    public void testGetStepBuilder() {
        RequestParam getParam = RequestParamStepBuilder.newBuilder().url("www.google.com")
                .getMethod("get").queryParams("&a=5&b=2").build();
        Assert.assertEquals("www.google.com",getParam.getUrl());

        Assert.assertEquals("get",getParam.getMethod());
        Assert.assertEquals("&a=5&b=2",getParam.getQueryParams());
    }
}
