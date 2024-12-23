package org.ddd.fundamental.common.tenant.aop;

import cn.hutool.core.util.ArrayUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.ddd.fundamental.thread.RequestUserContext;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.NonNull;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.util.StringValueResolver;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public abstract class AbstractTenantOperationAdvisor
        implements PointcutAdvisor, MethodInterceptor, EmbeddedValueResolverAware {

    private static final String INTERCEPT_REQUEST_ENTRY = "tenant";
    private static final TenantOpsInfo NULL = new TenantOpsInfo(null, true);
    private final Map<Method, TenantOpsInfo> tenantInfoCaches = new ConcurrentReferenceHashMap<>();
    private final TenantOpsInfo opsByDefault;
    @Setter
    private StringValueResolver embeddedValueResolver;

    /**
     * Create a new {@link AbstractTenantOperationAdvisor} instance.
     *
     * @param tableWithColumns 要拦截的表-租户字段
     * @author huangchengxing
     */
    protected AbstractTenantOperationAdvisor(Map<String, String> tableWithColumns) {
        this.opsByDefault = new TenantOpsInfo(tableWithColumns, false);
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        // 从上下文获取租户ID
        String tenantId = Optional.ofNullable(RequestUserContext.getUser())
                .map(RequestUserContext.User::getUserId)
                .orElse(null);
        // 若没有上下文信息，或者不是从用户端发起的请求，则直接放行
        if (Objects.isNull(tenantId)) {
            return methodInvocation.proceed();
        }
        // 解析配置信息
        TenantOpsInfo info = resolveMethod(methodInvocation.getMethod());
        if (info == NULL) {
            return methodInvocation.proceed();
        }
        return doInvoke(methodInvocation, info, tenantId);
    }

    /**
     * 执行被拦截的方法
     *
     * @param methodInvocation 方法调用
     * @param info            租户操作信息
     * @param tenantId        租户ID
     * @return 方法执行结果
     * @throws Throwable 调用异常
     */
    protected abstract Object doInvoke(MethodInvocation methodInvocation, TenantOpsInfo info, String tenantId) throws Throwable;

    private TenantOpsInfo resolveMethod(Method method) {
        return tenantInfoCaches.computeIfAbsent(method, m -> {
            // 从方法上或类上获取注解
            TenantOperation annotation = Optional.ofNullable(AnnotatedElementUtils.findMergedAnnotation(method, TenantOperation.class))
                    .orElse(AnnotatedElementUtils.findMergedAnnotation(method.getDeclaringClass(), TenantOperation.class));
            if (Objects.isNull(annotation)) {
                return NULL;
            }
            // 若注解未指定column和tables，则使用默认值
            TenantOperation.Tables[] tables = annotation.value();
            if (ArrayUtil.isEmpty(tables) && (annotation.ignore() == opsByDefault.isIgnore())) {
                return opsByDefault;
            }
            // 若指定了column和tables，则使用指定值
            Map<String, String> tableWithColumns = new HashMap<>(tables.length);
            for (TenantOperation.Tables table : tables) {
                String column = embeddedValueResolver.resolveStringValue(table.column());
                for (String tableName : table.tables()) {
                    tableName = embeddedValueResolver.resolveStringValue(tableName);
                    tableWithColumns.put(tableName, column);
                }
            }
            return new TenantOpsInfo(tableWithColumns, annotation.ignore());
        });
    }

    @Override
    public @NonNull Pointcut getPointcut() {
        return TenantOperationPointcut.INSTANCE;
    }

    @Override
    public @NonNull Advice getAdvice() {
        return this;
    }

    @Override
    public boolean isPerInstance() {
        return false;
    }

    /**
     * 租户操作配置，包含了需要添加租户条件的表名与对应的租户字段
     *
     * @author huangchengxing
     */
    @Getter
    @RequiredArgsConstructor
    protected static class TenantOpsInfo {
        private final Map<String, String> tablesWithTenantColumn;
        private final boolean ignore;
    }
}
