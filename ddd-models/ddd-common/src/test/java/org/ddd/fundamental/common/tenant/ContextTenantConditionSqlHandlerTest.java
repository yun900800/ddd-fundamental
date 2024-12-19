package org.ddd.fundamental.common.tenant;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ContextTenantConditionSqlHandlerTest {
    private ContextTenantConditionSqlHandler inspector;

    @Before
    public void init() {
        inspector = new ContextTenantConditionSqlHandler();
        Map<String, String> tableWithTenantColumns = new HashMap<>();
        tableWithTenantColumns.put("user", "tenant_id");
        tableWithTenantColumns.put("order", "tenant_id");
        ContextTenantConditionSqlHandler.setTenantInfo(new ContextTenantConditionSqlHandler.TenantInfo(
                "1", tableWithTenantColumns
        ));
    }

    @After
    public void destroy() {
        inspector.clearTenantInfo();
    }

    @Test
    public void testSimpleSql() {
        // 无表别名
        String sql = "select * from user where id = 1";
        String result = inspector.handle(sql);
        Assert.assertEquals(
                "SELECT * FROM user WHERE id = 1 AND tenant_id = '1'", result
        );

        // 指定表别名
        sql = "select * from user u where u.id = 1";
        result = inspector.handle(sql);
        Assert.assertEquals(
                "SELECT * FROM user u WHERE u.id = 1 AND u.tenant_id = '1'", result
        );

        // 多重 and 条件
        sql = "select * from user u where u.id = 1 and u.name = 'test'";
        result = inspector.handle(sql);
        Assert.assertEquals(
                "SELECT * FROM user u WHERE u.id = 1 AND u.name = 'test' AND u.tenant_id = '1'", result
        );

        // 多重 or 条件
        sql = "select * from user u where u.id = 1 or u.name = 'test'";
        result = inspector.handle(sql);
        Assert.assertEquals(
                "SELECT * FROM user u WHERE u.tenant_id = '1' AND (u.id = 1 OR u.name = 'test')", result
        );
    }

    @Test
    public void testNestedInSelect() {
        // 当内外两张表同时存在租户字段时，应当都为其添加租户字段的条件
        String sql = "select * " +
                "from user usr " +
                "where usr.id in (select u.id from user u)";
        String result = inspector.handle(sql);
        Assert.assertEquals(
                "SELECT * FROM user usr WHERE usr.id IN (SELECT u.id FROM user u WHERE u.tenant_id = '1') AND usr.tenant_id = '1'", result
        );

        // 当仅外层表存在租户字段时，仅为外层表添加租户字段的条件
        sql = "select * " +
                "from user usr " +
                "where usr.id in (select u.id from user2 u)";
        result = inspector.handle(sql);
        Assert.assertEquals(
                "SELECT * FROM user usr WHERE usr.id IN (SELECT u.id FROM user2 u) AND usr.tenant_id = '1'", result
        );

        // 当仅内层表存在租户字段时，仅为内层表添加租户字段的条件
        sql = "select * " +
                "from user2 usr " +
                "where usr.id in (select u.id from user u)";
        result = inspector.handle(sql);
        Assert.assertEquals(
                "SELECT * FROM user2 usr WHERE usr.id IN (SELECT u.id FROM user u WHERE u.tenant_id = '1')", result
        );

        // 当从临时表查询时，应当为临时表添加租户字段的条件
        sql = "select * " +
                "from (select u.id from user u1) usr " +
                "where usr.id in (select u.id from user u2)";
        result = inspector.handle(sql);
        Assert.assertEquals(
                "SELECT * FROM (SELECT u.id FROM user u1 WHERE u1.tenant_id = '1') usr WHERE usr.id IN (SELECT u.id FROM user u2 WHERE u2.tenant_id = '1')", result
        );
    }

    @Test
    public void testFromTmpTableSql() {
        // 无表别名
        String sql = "select * " +
                "from (select * from user) u " +
                "where u.id = 1";
        String result = inspector.handle(sql);
        Assert.assertEquals(
                "SELECT * FROM (SELECT * FROM user WHERE tenant_id = '1') u WHERE u.id = 1", result
        );

        // 指定内部查询的表别名
        sql = "select * " +
                "from (select * from user usr) u " +
                "where u.id = 1";
        result = inspector.handle(sql);
        Assert.assertEquals(
                "SELECT * FROM (SELECT * FROM user usr WHERE usr.tenant_id = '1') u WHERE u.id = 1", result
        );

        // 多重 and 条件
        sql = "select * " +
                "from (select * from user usr where user.name = 'test') u " +
                "where u.id = 1";
        result = inspector.handle(sql);
        Assert.assertEquals(
                "SELECT * FROM (SELECT * FROM user usr WHERE user.name = 'test' AND usr.tenant_id = '1') u WHERE u.id = 1", result
        );

        // 多重 or 条件
        sql = "select * " +
                "from (select * from user usr where user.name = 'test' or user.name = 'test2') u " +
                "where u.id = 1";
        result = inspector.handle(sql);
        Assert.assertEquals(
                "SELECT * FROM (SELECT * FROM user usr WHERE usr.tenant_id = '1' AND (user.name = 'test' OR user.name = 'test2')) u WHERE u.id = 1", result
        );
    }

    @Test
    public void testConditionQuery() {
        // 当查询条件中存在子查询时，应当为子查询添加租户字段的条件
        String sql = "select * from user u " +
                "where u.id = (select o.id from order o limit 1)";
        String result = inspector.handle(sql);
        Assert.assertEquals(
                "SELECT * FROM user u WHERE u.id = (SELECT o.id FROM order o WHERE o.tenant_id = '1' LIMIT 1) AND u.tenant_id = '1'", result
        );

        // 当查询条件中存在重复的子查询时，应当为子查询添加租户字段的条件
        sql = "select * from user u " +
                "where u.id = (select o.id from order o limit 1) " +
                "and u.id = (select o.id from order o limit 1)";
        result = inspector.handle(sql);
        Assert.assertEquals(
                "SELECT * FROM user u WHERE u.id = (SELECT o.id FROM order o WHERE o.tenant_id = '1' LIMIT 1) AND u.id = (SELECT o.id FROM order o WHERE o.tenant_id = '1' LIMIT 1) AND u.tenant_id = '1'", result
        );

        // 当查询条件中存在多处子查询时，应当为子查询添加租户字段的条件
        sql = "select * from user u " +
                "where u.id = (select o.id from (select * from order) o limit 1)";
        result = inspector.handle(sql);
        Assert.assertEquals(
                "SELECT * FROM user u WHERE u.id = (SELECT o.id FROM (SELECT * FROM order WHERE tenant_id = '1') o LIMIT 1) AND u.tenant_id = '1'", result
        );

        // 当查询条件中，子查询反复嵌套时，应当为子查询添加租户字段的条件
        sql = "select * from user u " +
                "where u.id in (select o1.id from order o1 where o1.id = (select o2.id from order o2 where o2.id = (select o3.id from order o3)))";
        result = inspector.handle(sql);
        Assert.assertEquals(
                "SELECT * FROM user u WHERE u.id IN (SELECT o1.id FROM order o1 WHERE o1.id = (SELECT o2.id FROM order o2 WHERE o2.id = (SELECT o3.id FROM order o3 WHERE o3.tenant_id = '1') AND o2.tenant_id = '1') AND o1.tenant_id = '1') AND u.tenant_id = '1'", result
        );
    }

    @Test
    public void testSimpleJoin() {
        // 当主表与子表都存在租户字段时，应当都为其添加租户字段的条件，其中，主表的租户字段条件应当放在 Where，而子表的租户字段条件应当放在 On
        String sql = "select * from user u " +
                "join order o on u.id = o.user_id";
        String result = inspector.handle(sql);
        Assert.assertEquals(
                "SELECT * FROM user u JOIN order o ON u.id = o.user_id AND o.tenant_id = '1' WHERE u.tenant_id = '1'", result
        );

        // 当主表存在租户字段而子表不存在租户字段时，仅为主表添加租户字段的条件
        sql = "select * from user u " +
                "join order2 o on u.id = o.user_id";
        result = inspector.handle(sql);
        Assert.assertEquals(
                "SELECT * FROM user u JOIN order2 o ON u.id = o.user_id WHERE u.tenant_id = '1'", result
        );

        // 当子表存在租户字段而主表不存在租户字段时，仅为子表添加租户字段的条件
        sql = "select * from user2 u " +
                "join order o on u.id = o.user_id";
        result = inspector.handle(sql);
        Assert.assertEquals(
                "SELECT * FROM user2 u JOIN order o ON u.id = o.user_id AND o.tenant_id = '1'", result
        );

        // 当同时关联多张表时，仅为存在租户字段的表添加租户字段的条件
        sql = "select * from user u " +
                "join order o on u.id = o.user_id " +
                "join order2 o2 on u.id = o2.user_id";
        result = inspector.handle(sql);
        Assert.assertEquals(
                "SELECT * FROM user u JOIN order o ON u.id = o.user_id AND o.tenant_id = '1' JOIN order2 o2 ON u.id = o2.user_id WHERE u.tenant_id = '1'", result
        );
    }

    @Test
    public void testNestedInJoin() {
        // 当内外两张表同时存在租户字段时，应当都为其添加租户字段的条件
        String sql = "select * " +
                "from user usr " +
                "join (select u.id from user u) o on usr.id = o.id";
        String result = inspector.handle(sql);
        Assert.assertEquals(
                "SELECT * FROM user usr JOIN (SELECT u.id FROM user u WHERE u.tenant_id = '1') o ON usr.id = o.id WHERE usr.tenant_id = '1'", result
        );

        // 当仅外层表存在租户字段时，仅为外层表添加租户字段的条件
        sql = "select * " +
                "from user usr " +
                "join (select u.id from user2 u) o on usr.id = o.id";
        result = inspector.handle(sql);
        Assert.assertEquals(
                "SELECT * FROM user usr JOIN (SELECT u.id FROM user2 u) o ON usr.id = o.id WHERE usr.tenant_id = '1'", result
        );

        // 当仅内层表存在租户字段时，仅为内层表添加租户字段的条件
        sql = "select * " +
                "from user2 usr " +
                "join (select u.id from user u) o on usr.id = o.id";
        result = inspector.handle(sql);
        Assert.assertEquals(
                "SELECT * FROM user2 usr JOIN (SELECT u.id FROM user u WHERE u.tenant_id = '1') o ON usr.id = o.id", result
        );

        // 当从临时表查询时，应当为临时表添加租户字段的条件
        sql = "select * " +
                "from (select u.id from user u1) usr " +
                "join (select u.id from user u2) o on usr.id = o.id";
        result = inspector.handle(sql);
        Assert.assertEquals(
                "SELECT * FROM (SELECT u.id FROM user u1 WHERE u1.tenant_id = '1') usr JOIN (SELECT u.id FROM user u2 WHERE u2.tenant_id = '1') o ON usr.id = o.id", result
        );
    }

    @Test
    public void testFunction() {
        // 当查询条件中存在函数时，应当为函数的参数添加租户字段的条件
        String sql = "select * from user u " +
                "where u.id = (select max(o.id) from order o)";
        String result = inspector.handle(sql);
        Assert.assertEquals(
                "SELECT * FROM user u WHERE u.id = (SELECT max(o.id) FROM order o WHERE o.tenant_id = '1') AND u.tenant_id = '1'", result
        );

        // 当查询条件中存在函数时，应当为函数的参数添加租户字段的条件
        sql = "select * from user u " +
                "where u.id = (select max(o.id) from order o where o.id = 1)";
        result = inspector.handle(sql);
        Assert.assertEquals(
                "SELECT * FROM user u WHERE u.id = (SELECT max(o.id) FROM order o WHERE o.id = 1 AND o.tenant_id = '1') AND u.tenant_id = '1'", result
        );

        // 特殊函数 if
        sql = "select * from user u " +
                "where u.id = if((select o.id from order o where o.id = 1), 1, 0)";
        result = inspector.handle(sql);
        Assert.assertEquals(
                "SELECT * FROM user u WHERE u.id = if((SELECT o.id FROM order o WHERE o.id = 1 AND o.tenant_id = '1'), 1, 0) AND u.tenant_id = '1'", result
        );

        // 特殊函数 JSON_EXTRACT
        sql = "select * from user u " +
                "where u.id = JSON_EXTRACT((select o.id from order o where o.id = 1), '$.id')";
        result = inspector.handle(sql);
        Assert.assertEquals(
                "SELECT * FROM user u WHERE u.id = JSON_EXTRACT((SELECT o.id FROM order o WHERE o.id = 1 AND o.tenant_id = '1'), '$.id') AND u.tenant_id = '1'", result
        );
    }

    @Test
    public void testCaseWhenSql() {
        // 当查询条件中存在 case when 时，应当为 case when 的条件添加租户字段的条件
        String sql = "select * from user u " +
                "where u.id = case when (select o.id from order o where o.id = 1) then 1 else 0 end";
        String result = inspector.handle(sql);
        Assert.assertEquals(
                "SELECT * FROM user u WHERE u.id = CASE WHEN (SELECT o.id FROM order o WHERE o.id = 1 AND o.tenant_id = '1') THEN 1 ELSE 0 END AND u.tenant_id = '1'", result
        );

        // 当查询条件中存在 case when 时，应当为 case when 的条件添加租户字段的条件
        sql = "select * from user u " +
                "where u.id = case when (select o.id from order o where o.id = 1) then 1 when (select o.id from order o where o.id = 2) then 2 else 0 end";
        result = inspector.handle(sql);
        Assert.assertEquals(
                "SELECT * FROM user u WHERE u.id = CASE WHEN (SELECT o.id FROM order o WHERE o.id = 1 AND o.tenant_id = '1') THEN 1 WHEN (SELECT o.id FROM order o WHERE o.id = 2 AND o.tenant_id = '1') THEN 2 ELSE 0 END AND u.tenant_id = '1'", result
        );
    }
}
