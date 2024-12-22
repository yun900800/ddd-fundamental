package org.ddd.fundamental.common.tenant.hibernate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.common.tenant.sql.LineLevelTenantSqlHandler;
import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.springframework.stereotype.Component;

import javax.persistence.Column;

/**
 * SQL拦截器，用于为SQL语句添加租户条件。
 * 每次执行SQL时，将会检查当前线程中是否存在租户信息，如果存在，则会为查询语句添加租户条件，否则直接略过。
 *
 *
 */
@Slf4j
@RequiredArgsConstructor
public class HibernateLineLevelTenantStatementInspector
        extends LineLevelTenantSqlHandler implements StatementInspector {

    @Override
    public String inspect(String sql) {
        return handle(sql);
    }
}
