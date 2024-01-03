package org.ddd.fundamental.spring.env;

import org.junit.Test;

import java.io.IOException;

public class PropertySourcesExampleTest {

    @Test
    public void testPropertySourcesExampleLoad() throws IOException {
        PropertySourcesExample.load();
    }
}
