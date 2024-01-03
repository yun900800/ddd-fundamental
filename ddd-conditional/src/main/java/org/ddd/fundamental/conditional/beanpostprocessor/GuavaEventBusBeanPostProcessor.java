package org.ddd.fundamental.conditional.beanpostprocessor;

import com.google.common.eventbus.EventBus;
import org.ddd.fundamental.conditional.beanpostprocessor.annotation.Subscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionException;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.function.BiConsumer;

public class GuavaEventBusBeanPostProcessor implements DestructionAwareBeanPostProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(GuavaEventBusBeanPostProcessor.class);

    SpelExpressionParser expressionParser = new SpelExpressionParser();

    @Override
    public boolean requiresDestruction(Object bean) {
        return true;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        this.process(bean,EventBus::register,"initialization" );
        return bean;
    }

    @Override
    public void postProcessBeforeDestruction(Object bean, String s) throws BeansException {
        this.process(bean,EventBus::unregister,"destruction" );
    }

    private void process(Object bean, BiConsumer<EventBus, Object> consumer, String action) {
        Object proxy = this.getTargetObject(bean);
        Subscriber annotation = AnnotationUtils.getAnnotation(proxy.getClass(),Subscriber.class);
        if (annotation == null)
            return;
        this.LOGGER.info("{}: processing bean of type {} during {}",
                this.getClass().getSimpleName(), proxy.getClass().getName(), action);
        String annotationValue = annotation.value();
        try {
            Expression expression = this.expressionParser.parseExpression(annotationValue);
            Object value = expression.getValue();
            if (!(value instanceof EventBus)) {
                this.LOGGER.error(
                        "{}: expression {} did not evaluate to an instance of EventBus for bean of type {}",
                        this.getClass().getSimpleName(), annotationValue, proxy.getClass().getSimpleName());
                return;
            }
            EventBus eventBus = (EventBus)value;
            consumer.accept(eventBus, proxy);
        } catch (ExpressionException e){
            this.LOGGER.error("{}: unable to parse/evaluate expression {} for bean of type {}",
                    this.getClass().getSimpleName(), annotationValue, proxy.getClass().getName());
        }
    }

    private Object getTargetObject(Object proxy) {
        if (AopUtils.isJdkDynamicProxy(proxy)) {
            try {
                return ((Advised)proxy).getTargetSource().getTarget();
            } catch (Exception e) {
                throw new FatalBeanException("Error getting target of JDK proxy", e);
            }
        }
        return proxy;
    }
}
