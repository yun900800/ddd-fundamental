package org.ddd.fundamental.share.infrastructure.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.Mockito.when;

public class ParameterTest {

    @Test
    public void testParameter() throws ParameterNotExist {
        Dotenv dotenv = Dotenv.load();
        Parameter parameter = new Parameter(dotenv);
        Assert.assertEquals(parameter.get("MY_ENV_VAR1"),"some_value");
        Assert.assertEquals(parameter.get("MY_EVV_VAR2"),"some_value #some value comment");
    }

    @Test
    public void testParameterByMock() throws ParameterNotExist {
        Dotenv dotenv = Mockito.mock(Dotenv.class);
        when(dotenv.get("MY_ENV_VAR1")).thenReturn("some_value");
        when(dotenv.get("MY_EVV_VAR2")).thenReturn("some_value #some value comment");
        when(dotenv.get("MY_EVV_VAR3")).thenReturn("5");
        Parameter parameter = new Parameter(dotenv);
        Assert.assertEquals(parameter.get("MY_ENV_VAR1"),"some_value");
        Assert.assertEquals(parameter.get("MY_EVV_VAR2"),"some_value #some value comment");
        Assert.assertEquals(Optional.of(parameter.getInt("MY_EVV_VAR3")),Optional.of(5));
    }
}
