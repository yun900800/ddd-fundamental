package org.ddd.fundamental.memoize;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class MemoizerAspect {
    @Autowired
    private RequestScopeCache requestScopeCache;

    @Around("@annotation(org.ddd.fundamental.memoize.Memoize)")
    public Object memoize(ProceedingJoinPoint pjp) throws Throwable {
        InvocationContext invocationContext = new InvocationContext(
                pjp.getSignature().getDeclaringType(),
                pjp.getSignature().getName(),
                pjp.getArgs()
        );
        Object result = requestScopeCache.get(invocationContext);
        if (RequestScopeCache.NONE == result) {
            result = pjp.proceed();
            log.info("Memoizing result {}, for method invocation: {}", result, invocationContext);
            requestScopeCache.put(invocationContext, result);
        } else {
            log.info("Using memoized result: {}, for method invocation: {}", result, invocationContext);
        }
        return result;
    }
}
