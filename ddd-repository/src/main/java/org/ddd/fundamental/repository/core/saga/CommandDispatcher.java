package org.ddd.fundamental.repository.core.saga;


import com.fasterxml.jackson.core.JsonProcessingException;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class CommandDispatcher {
    //命令处理器对象列表
    private Map<String, CommandHandlerUnite> commandHandlers = new HashMap<>();

    /**
     * 命令分发器实例
     */
    public final static CommandDispatcher INSTANCE = new CommandDispatcher();

    /**
     * 分发消息方法
     */
    public void dispatch(Command command) throws JsonProcessingException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        CommandHandler commandHandler = (CommandHandler) this.commandHandlers.get(command.getName());
        if (commandHandler != null) {
            commandHandler.process(command);
        }
    }

    /**
     * 注册处理分发器
     *
     */
    public void register(String commandName, CommandHandlerUnite commandHandler) {
        this.commandHandlers.put(commandName, commandHandler);
    }
}
