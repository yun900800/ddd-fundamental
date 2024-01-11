package org.ddd.fundamental.share.domain.bus.command;

import org.junit.Assert;
import org.junit.Test;

public abstract class CommandInterfaceTest {

    private Command command;

    protected abstract Command createCommand();

    @Test
    public void testCommand() {
        command = createCommand();
        Assert.assertEquals(Command.class, command.getClass().getInterfaces()[0]);
    }
}
