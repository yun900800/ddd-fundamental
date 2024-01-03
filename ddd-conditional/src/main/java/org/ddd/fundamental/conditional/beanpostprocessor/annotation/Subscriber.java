package org.ddd.fundamental.conditional.beanpostprocessor.annotation;

import org.ddd.fundamental.conditional.beanpostprocessor.eventbus.GlobalEventBus;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface Subscriber {

    String value() default GlobalEventBus.GLOBAL_EVENT_BUS_EXPRESSION;
}
