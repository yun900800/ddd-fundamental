package org.ddd.fundamental.share.domain.cretiria;

import org.ddd.fundamental.share.domain.creteria.Filter;
import org.ddd.fundamental.share.domain.creteria.Filters;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FiltersTest {

    @Test
    public void testCreateFilters() {
        Filters noFilters = Filters.none();
        Assert.assertEquals(noFilters.filters().size(),0);
        Assert.assertEquals("",noFilters.serialize());

        Filter filter = Mockito.mock(Filter.class);
        List<Filter> filterList = new ArrayList<>();
        filterList.add(filter);
        Filters filters = new Filters(filterList);
        Assert.assertEquals(filters.filters().size(),1);
    }

    @Test
    public void testFromValues() {
        HashMap<String,String> map = new HashMap<>();
        map.put("field","username");
        map.put("operator","=");
        map.put("value","hello");
        HashMap<String,String> map1 = new HashMap<>();
        map1.put("field","username");
        map1.put("operator","=");
        map1.put("value","hello");

        List<HashMap<String,String>> filterList = new ArrayList<>();
        filterList.add(map);
        filterList.add(map1);
        Filters filters = Filters.fromValues(filterList);
        Assert.assertEquals(filters.filters().size(),2);
        Assert.assertEquals("username.=.hello^username.=.hello",filters.serialize());
    }
}
