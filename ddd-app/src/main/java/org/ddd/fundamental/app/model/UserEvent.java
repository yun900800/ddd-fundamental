package org.ddd.fundamental.app.model;

import org.ddd.fundamental.share.domain.bus.event.DomainEvent;

import java.io.Serializable;
import java.util.HashMap;

public class UserEvent extends DomainEvent {
    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    private String userName;

    private String password;

    public UserEvent(){

    }

    public UserEvent(String userName,String password,String aggregateId,
                     String eventId, String createOn) {
        super(aggregateId,eventId,createOn);
        this.userName =userName;
        this.password = password;

    }

    public UserEvent(String userName,String password,String aggregateId
                     ) {
        super(aggregateId);
        this.userName =userName;
        this.password = password;

    }

    @Override
    public String eventName() {
        return "UserEvent";
    }

    @Override
    public HashMap<String, Serializable> toPrimitives() {
        HashMap<String, Serializable> map = new HashMap<>();
        map.put("userName", this.userName);
        map.put("password", this.password);
        map.put("aggregateId", aggregateId());
        map.put("eventId", eventId());
        map.put("occurredOn", occurredOn());
        return map;
    }

    @Override
    public DomainEvent fromPrimitives(String aggregateId, HashMap<String, Serializable> body, String eventId, String occurredOn) {
        String userName = (String)body.get("userName");
        String password = (String)body.get("password");
        UserEvent emptyDomainEvent = new UserEvent(userName,password,
                aggregateId,eventId,occurredOn);
        return emptyDomainEvent;
    }

    @Override
    public String toString() {
        return "UserEvent{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
