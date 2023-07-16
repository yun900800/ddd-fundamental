package org.ddd.fundamental.share.infrastructure.bus.event;

import org.ddd.fundamental.share.domain.Service;
import org.ddd.fundamental.share.domain.bus.event.DomainEvent;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Service
public final class DomainEventsInformation {
    HashMap<String, Class<? extends DomainEvent>> indexedDomainEvents;

    private static final String DEFAULT_DOMAIN_EVENT_PATH = "org.ddd.fundamental";

    public DomainEventsInformation() {
        Reflections                       reflections = new Reflections(DEFAULT_DOMAIN_EVENT_PATH);
        Set<Class<? extends DomainEvent>> classes     = reflections.getSubTypesOf(DomainEvent.class);
        try {
            indexedDomainEvents = formatEvents(classes);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public DomainEventsInformation appendScanDomainEventPah(String path) {
        Reflections reflections = new Reflections(path);
        Set<Class<? extends DomainEvent>> classes     = reflections.getSubTypesOf(DomainEvent.class);
        try {
            HashMap<String, Class<? extends DomainEvent>> addedScanDomainEvents = formatEvents(classes);
            for (Map.Entry<String,Class<? extends DomainEvent>> entry:addedScanDomainEvents.entrySet()) {
                indexedDomainEvents.put(entry.getKey(),entry.getValue());
            }
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return this;
    }

    public Class<? extends DomainEvent> forName(String name) {
        return indexedDomainEvents.get(name);
    }

    public String forClass(Class<? extends DomainEvent> domainEventClass) {
        return indexedDomainEvents.entrySet()
                .stream()
                .filter(entry -> Objects.equals(entry.getValue(), domainEventClass))
                .map(Map.Entry::getKey)
                .findFirst().orElse("");
    }

    public Integer eventSize() {
        return indexedDomainEvents.size();
    }

    private HashMap<String, Class<? extends DomainEvent>> formatEvents(
            Set<Class<? extends DomainEvent>> domainEvents
    ) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        HashMap<String, Class<? extends DomainEvent>> events = new HashMap<>();

        for (Class<? extends DomainEvent> domainEvent : domainEvents) {
            DomainEvent nullInstance;
            try {
                 nullInstance = domainEvent.getConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            events.put((String) domainEvent.getMethod("eventName").invoke(nullInstance), domainEvent);
        }

        return events;
    }
}
