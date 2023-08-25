package org.ddd.fundamental.share.domain.bus.event;

import java.io.Serializable;
import java.util.HashMap;

public class UserDomainEvent extends DomainEvent{

    private String userName;

    private Integer age;

    public UserDomainEvent(){}

    @Override
    public String eventName() {
        return "UserDomainEvent";
    }

    @Override
    public HashMap<String, Serializable> toPrimitives() {
        HashMap<String, Serializable> map = new HashMap<>();
        map.put("userName", this.userName);
        map.put("age", this.age);
        map.put("aggregateId", aggregateId());
        map.put("eventId", eventId());
        map.put("occurredOn", occurredOn());
        return map;
    }

    @Override
    public DomainEvent fromPrimitives(String aggregateId, HashMap<String, Serializable> body, String eventId, String occurredOn) {
        String userName = (String)body.get("userName");
        Integer age = (Integer) body.get("age");
        UserDomainEvent userDomainEvent = new UserDomainEvent(userName, age,
                aggregateId,eventId,occurredOn);
        return userDomainEvent;
    }

    public UserDomainEvent(String userName, Integer age,String aggregateId,
                            String eventId, String createOn) {
        super(aggregateId,eventId,createOn);
        this.userName = userName;
        this.age = age;
    }

    public UserDomainEvent(String userName, Integer age,String aggregateId) {
        super(aggregateId);
        this.userName = userName;
        this.age = age;
    }
}
