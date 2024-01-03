package org.ddd.fundamental.share.domain.cretiria;

import org.ddd.fundamental.share.domain.creteria.Filter;
import org.ddd.fundamental.share.domain.creteria.FilterField;
import org.ddd.fundamental.share.domain.creteria.FilterOperator;
import org.ddd.fundamental.share.domain.creteria.FilterValue;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class FilterTest {

    @Test
    public void testCreateCreteria() {
        Filter filter = Filter.create("username","=","hello");
        Assert.assertEquals(filter.field(),new FilterField("username"));
        Assert.assertEquals(filter.operator(), FilterOperator.EQUAL);
        Assert.assertEquals(filter.value(), new FilterValue("hello"));
        Assert.assertEquals("username.=.hello",filter.serialize());
    }

    @Test
    public void testFromValues() {
        HashMap<String,String> map = new HashMap<>();
        map.put("field","username");
        map.put("operator","=");
        map.put("value","hello");
        Filter filter = Filter.fromValues(map);
        Assert.assertEquals(filter.field(),new FilterField("username"));
        Assert.assertEquals(filter.operator(), FilterOperator.EQUAL);
        Assert.assertEquals(filter.value(), new FilterValue("hello"));
        Assert.assertEquals("username.=.hello",filter.serialize());
    }
}
