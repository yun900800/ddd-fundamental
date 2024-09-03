package org.ddd.fundamental.common;

import org.springframework.context.ApplicationEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class AggregateRoot<T> {

    private T id;

    private List<ApplicationEvent> domainEvents = new ArrayList<>();

    protected AggregateRoot(T id){
        this.id = id;
    }

    protected void registerEvent(ApplicationEvent e){
        domainEvents.add(e);
    }

    public void clearDomainEvents(){
        domainEvents.clear();
    }

    public T getId(){
        return id;
    }

    public List<ApplicationEvent> getDomainEvents() {
        return domainEvents;
    }

}
