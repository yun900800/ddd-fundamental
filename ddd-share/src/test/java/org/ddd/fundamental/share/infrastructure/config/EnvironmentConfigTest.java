package org.ddd.fundamental.share.infrastructure.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.ddd.fundamental.share.DddShareTestApp;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = DddShareTestApp.class)
@RunWith(SpringRunner.class)
public class EnvironmentConfigTest {

    @Autowired
    private EnvironmentConfig  environmentConfig;

    @Autowired
    private Dotenv dotenv;

    @Test
    public void testEnvironmentConfig() {
        Assert.assertNotNull(environmentConfig);
    }

    @Test
    public void testDotenv() {
        Assert.assertEquals(dotenv.get("RABBITMQ_HOST"),"codelytv-java_ddd_skeleton-rabbitmq");
    }
}
