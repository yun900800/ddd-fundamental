package org.ddd.fundamental.base.loader;

import org.junit.Assert;
import org.junit.Test;

public class SpiFactoryTest {

    @Test
    public void testGet() {
        IUser user = SpiFactory.get(IUser.class);
        Assert.assertEquals("UserOne", user.name());
    }

    @Test
    public void testGetReturnDefault() {
        IUser user = SpiFactory.get(IUser.class, () -> "Default");
        Assert.assertEquals("Default", user.name());
    }
}
