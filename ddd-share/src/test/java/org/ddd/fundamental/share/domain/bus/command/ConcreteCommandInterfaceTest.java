package org.ddd.fundamental.share.domain.bus.command;

public class ConcreteCommandInterfaceTest extends CommandInterfaceTest{
    @Override
    protected Command createCommand() {
        return new Command() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        };
    }



}
