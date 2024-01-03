package org.ddd.fundamental.share.infrastructure.utils;

import org.hibernate.Session;

import java.util.function.Consumer;

@FunctionalInterface
public interface HibernateTransactionConsumer extends Consumer<Session> {
    default void beforeTransactionCompletion() {

    }

    default void afterTransactionCompletion() {

    }
}
