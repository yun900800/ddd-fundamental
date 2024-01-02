package org.ddd.fundamental.datasource.provider;

import org.ddd.fundamental.datasource.core.DataSourceProvider;
import org.ddd.fundamental.datasource.core.enums.Database;
import org.testcontainers.containers.JdbcDatabaseContainer;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 实现数据源提供者的一个抽象类
 */
public abstract class AbstractContainerDataSourceProvider implements DataSourceProvider {
    @Override
    public DataSource dataSource() {
        DataSource dataSource = newDataSource();
        try(Connection connection = dataSource.getConnection()) {
            //如果连接成功,则直接返回数据源
            return dataSource;
        } catch (SQLException e) {
            Database database = database();
            //否则的话创建一个测试的数据库容器
            if(database.getContainer() == null) {
                database.initContainer(username(), password());
            }
            return newDataSource();
        }
    }

    @Override
    public String url() {
        JdbcDatabaseContainer container = database().getContainer();
        return container != null ?
                container.getJdbcUrl() :
                defaultJdbcUrl();
    }

    /**
     * 创建一个默认的url
     * @return
     */
    protected abstract String defaultJdbcUrl();

    /**
     * 创建一个数据源
     * @return
     */
    protected abstract DataSource newDataSource();
}
