package org.ddd.fundamental.spring.env;

import org.junit.Test;

public class PropertySourcesLoadersTest {

    @Test
    public void testPropertySourcesLoadersLoad() {
        PropertySourcesLoaders.load();
    }
}
