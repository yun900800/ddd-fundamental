package org.ddd.fundamental.spring.condition;

import org.ddd.fundamental.spring.ConditionExample;
import org.junit.Test;

import java.io.IOException;

public class ConditionExampleTest {

    @Test
    public void testConditionLoad() throws IOException {
        ConditionExample.load();
    }
}
