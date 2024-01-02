package org.ddd.fundamental.datasource.core;

import org.ddd.fundamental.datasource.core.enums.Database;
import org.ddd.fundamental.datasource.utils.ReflectionUtils;
import org.hibernate.dialect.Dialect;

import javax.sql.DataSource;
import java.util.Properties;

public interface DataSourceProvider {

    /**
     * hibernateDialect 属性
     * @return String
     */
    String hibernateDialect();

    /**
     * 数据源
     * @return DataSource
     */
    DataSource dataSource();

    /**
     * 数据源的类型名称
     * @return Class<? extends DataSource>
     */
    Class<? extends DataSource> dataSourceClassName();

    /**
     * 数据源的Properties属性
     * @return Properties
     */
    Properties dataSourceProperties();

    /**
     * 数据源的url
     * @return
     */
    String url();

    /**
     * 数据源的用户名
     * @return
     */
    String username();

    /**
     * 数据源的密码
     * @return
     */
    String password();

    /**
     * 数据库类型
     * @return Database
     */
    Database database();

    Queries queries();


    /**
     * 默认返回hibernateDialect的类型名称
     * @return Class<? extends Dialect>
     */
    default Class<? extends Dialect> hibernateDialectClass() {
        return ReflectionUtils.getClass(hibernateDialect());
    }
}
