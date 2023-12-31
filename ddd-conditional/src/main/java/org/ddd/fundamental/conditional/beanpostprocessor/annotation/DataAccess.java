package org.ddd.fundamental.conditional.beanpostprocessor.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
@Documented
public @interface DataAccess {

    Class<?> entity();
}
