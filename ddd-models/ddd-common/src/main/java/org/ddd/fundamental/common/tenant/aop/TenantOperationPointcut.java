package org.ddd.fundamental.common.tenant.aop;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.StaticMethodMatcher;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.NonNull;

import java.lang.reflect.Method;

class TenantOperationPointcut extends StaticMethodMatcher implements Pointcut {
    public static final TenantOperationPointcut INSTANCE = new TenantOperationPointcut();

    @Override
    public @NonNull ClassFilter getClassFilter() {
        return ClassFilter.TRUE;
    }

    @Override
    public @NonNull MethodMatcher getMethodMatcher() {
        return this;
    }

    @Override
    public boolean matches(@NonNull Method method, @NonNull Class<?> type) {
        return AnnotatedElementUtils.isAnnotated(method, TenantOperation.class)
                || AnnotatedElementUtils.isAnnotated(type, TenantOperation.class);
    }
}
