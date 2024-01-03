package org.ddd.fundamental.repository.core.saga;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventDispatcher {
    //事件处理器对象列表
    private Map<String, List<EventHandlerUnite>> eventHandlers = new HashMap<>();


    /**
     * 分发消息方法
     */
    public void dispatch(Event event) throws JsonProcessingException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        List<EventHandlerUnite> eventHandlers = this.eventHandlers.get(event.getName());
        for (EventHandlerUnite eventHandler : eventHandlers) {
            if (eventHandler != null) {
                eventHandler.process(event);
            }
        }
    }

    /**
     * 注册处理分发器
     */
    public void register(String eventName, EventHandlerUnite eventHandler) {
        List<EventHandlerUnite> eventHandlers = this.eventHandlers.get(eventName);
        if (eventHandlers == null) {
            eventHandlers = new ArrayList<>();
        }
        eventHandlers.add(eventHandler);
        this.eventHandlers.put(eventName, eventHandlers);
    }
}
