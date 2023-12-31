package org.ddd.fundamental.share.infrastructure.bus.event.mysql;

import org.ddd.fundamental.share.domain.Utils;
import org.ddd.fundamental.share.domain.bus.event.DomainEvent;
import org.ddd.fundamental.share.infrastructure.bus.event.DomainEventsInformation;
import org.ddd.fundamental.share.infrastructure.bus.event.spring.SpringApplicationEventBus;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MySqlDomainEventsConsumer {
    private final SessionFactory            sessionFactory;
    private final DomainEventsInformation domainEventsInformation;
    private final SpringApplicationEventBus bus;
    private final static Integer                   CHUNKS     = 200;
    private       Boolean                   shouldStop = false;

    public MySqlDomainEventsConsumer(
            @Qualifier("mooc-session_factory") SessionFactory sessionFactory,
            DomainEventsInformation domainEventsInformation,
            SpringApplicationEventBus bus
    ) {
        this.sessionFactory          = sessionFactory;
        this.domainEventsInformation = domainEventsInformation;
        this.bus                     = bus;
    }

    @Transactional
    public void consume() {
        while (!shouldStop) {
            NativeQuery query = sessionFactory.getCurrentSession().createSQLQuery(
                    "SELECT * FROM domain_events ORDER BY occurred_on ASC LIMIT :chunk"
            );

            query.setParameter("chunk", CHUNKS);

            List<Object[]> events = query.list();

            try {
                for (Object[] event : events) {
                    executeSubscribers(
                            (String) event[0],
                            (String) event[1],
                            (String) event[2],
                            (String) event[3],
                            (String) event[4]
                    );
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
            }

            sessionFactory.getCurrentSession().clear();
        }
    }

    public void stop() {
        shouldStop = true;
    }

    private void executeSubscribers(
            String id, String aggregateId, String eventName, String body, String occurredOn
    ) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        System.out.println("9999999999999999999");
        Class<? extends DomainEvent> domainEventClass = domainEventsInformation.forName(eventName);

        DomainEvent nullInstance = domainEventClass.getConstructor().newInstance();

        Method fromPrimitivesMethod = domainEventClass.getMethod(
                "fromPrimitives",
                String.class,
                HashMap.class,
                String.class,
                String.class
        );

        Object domainEvent = fromPrimitivesMethod.invoke(
                nullInstance,
                aggregateId,
                Utils.jsonDecode(body),
                id,
                occurredOn
        );

        bus.publish(Collections.singletonList((DomainEvent) domainEvent));
    }
}
