package org.ddd.fundamental.conditional.beanpostprocessor.metadatareader;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {
    String value() default "";
}
