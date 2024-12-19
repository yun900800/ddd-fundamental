package org.ddd.fundamental.common.tenant.sql;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.FromItem;
import org.springframework.lang.Nullable;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class LineLevelTenantSqlHandler extends AbstractSqlHandler{
    private static final ThreadLocal<TenantInfo> TENANT_INFO_CONTEXT = new ThreadLocal<>();

    /**
     * 获取当前租户信息
     *
     * @return 租户信息
     */
    public static TenantInfo getTenantInfo() {
        return TENANT_INFO_CONTEXT.get();
    }

    /**
     * 设置租户信息
     *
     * @param tenantInfo 租户信息
     */
    public static void setTenantInfo(TenantInfo tenantInfo) {
        TENANT_INFO_CONTEXT.set(tenantInfo);
    }

    /**
     * 清除租户信息
     */
    public static void clearTenantInfo() {
        TENANT_INFO_CONTEXT.remove();
    }

    @Override
    public String handle(String sql) {
        // 如果未设置租户信息，则直接返回原始SQL
        TenantInfo tenantInfo = TENANT_INFO_CONTEXT.get();
        if (Objects.isNull(tenantInfo)) {
            return sql;
        }
        log.debug("租户拦截器拦截原始 SQL: {}", sql);
        String handledSql = super.handle(sql);
        log.info("租户拦截器拦截后 SQL: {}", handledSql);
        return Objects.isNull(handledSql) ? sql : handledSql;
    }

    @Override
    @Nullable
    protected Expression handleCondition(@Nullable Expression expression, FromItem table) {
        TenantInfo tenantInfo = TENANT_INFO_CONTEXT.get();
        // 如果是一个标准表名，且改表名在租户表列表中，则为查询条件添加租户条件
        if (!(table instanceof Table)) {
            return null;
        }
        String tenantColumn = tenantInfo.tablesWithTenantColumn.get(((Table) table).getName());
        if (Objects.nonNull(tenantColumn)) {
            return appendTenantCondition(expression, table, tenantInfo.tenantId, tenantColumn);
        }
        return null;
    }

    private static Expression appendTenantCondition(
            @Nullable Expression original, FromItem table, String tenantId, String tenantColumn) {
        EqualsTo equalsTo = new EqualsTo();
        equalsTo.setLeftExpression(getColumnWithTableAlias(table, tenantColumn));
        equalsTo.setRightExpression(new StringValue(tenantId));
        if (Objects.isNull(original)) {
            return equalsTo;
        }
        return original instanceof OrExpression ?
                new AndExpression(equalsTo, new Parenthesis(original)) :
                new AndExpression(original, equalsTo);
    }

    private static Column getColumnWithTableAlias(FromItem table, String column) {
        // 如果表存在别名，则字段应该变“表别名.字段名”的格式
        return Optional.ofNullable(table)
                .map(FromItem::getAlias)
                .map(alias -> alias.getName() + "." + column)
                .map(Column::new)
                .orElse(new Column(column));
    }

    /**
     * 租户信息
     */
    @RequiredArgsConstructor
    public static class TenantInfo {
        /**
         * 租户ID
         */
        private final String tenantId;
        /**
         * 要添加租户条件的表名称与对应的租户字段
         */
        private final Map<String, String> tablesWithTenantColumn;
    }
}
