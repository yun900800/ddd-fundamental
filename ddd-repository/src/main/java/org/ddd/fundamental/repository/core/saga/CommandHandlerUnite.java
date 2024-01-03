package org.ddd.fundamental.repository.core.saga;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class CommandHandlerUnite {

    private static final String COMMAND_HANDLER_METHOD_NAME = "process";

    public void process(Command command) throws InvocationTargetException, IllegalAccessException, JsonProcessingException, ClassNotFoundException, NoSuchMethodException {
        if (command == null) {
            return;
        }
        Class clazz = Command.from(command.getName());
        Method handler = this.getClass().getMethod(COMMAND_HANDLER_METHOD_NAME, new Class[]{clazz});
        handler.invoke(this, command);
    }
}
