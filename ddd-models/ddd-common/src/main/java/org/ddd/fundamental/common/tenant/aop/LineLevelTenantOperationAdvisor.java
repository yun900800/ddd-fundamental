package org.ddd.fundamental.common.tenant.aop;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;
import org.ddd.fundamental.common.tenant.sql.LineLevelTenantSqlHandler;

import java.util.Map;
import java.util.Objects;

/**
 * 方法拦截器，用于拦截带有{@link TenantOperation}注解的方法，为涉及的查询语句添加租户过滤条件
 *
 *
 * @see LineLevelTenantSqlHandler
 */
@Slf4j
public class LineLevelTenantOperationAdvisor extends AbstractTenantOperationAdvisor {

    /**
     * Create a new {@link LineLevelTenantOperationAdvisor} instance.
     *
     * @param tableWithColumns 要拦截的表-租户字段
     * @author huangchengxing
     */
    public LineLevelTenantOperationAdvisor(Map<String, String> tableWithColumns) {
        super(tableWithColumns);
    }

    /**
     * 执行被拦截的方法
     *
     * @param methodInvocation 方法调用
     * @param info             租户操作信息
     * @param tenantId         租户ID
     * @return 方法执行结果
     * @throws Throwable 调用异常
     */
    @Override
    protected Object doInvoke(MethodInvocation methodInvocation, TenantOpsInfo info, String tenantId) throws Throwable {
        // 暂时挂起上一层级方法设置的租户信息
        LineLevelTenantSqlHandler.TenantInfo previous = LineLevelTenantSqlHandler.getTenantInfo();
        // 若当前方法设置了忽略租户信息，则清空上下文，否则设置当前租户信息
        if (info.isIgnore()) {
            LineLevelTenantSqlHandler.clearTenantInfo();
        } else {
            LineLevelTenantSqlHandler.TenantInfo current = new LineLevelTenantSqlHandler.TenantInfo(tenantId, info.getTablesWithTenantColumn());
            LineLevelTenantSqlHandler.setTenantInfo(current);
        }
        try {
            return methodInvocation.proceed();
        } finally {
            // 恢复挂起的租户信息
            if (Objects.nonNull(previous)) {
                LineLevelTenantSqlHandler.setTenantInfo(previous);
            }
            // 若之前没有租户信息，则清空上下文
            else {
                LineLevelTenantSqlHandler.clearTenantInfo();
            }
        }
    }
}
