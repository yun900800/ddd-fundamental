package org.ddd.fundamental.datasource;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.ddd.fundamental.enums.Database;
import org.ddd.fundamental.testframework.AbstractContainerDataSourceProvider;

import javax.sql.DataSource;

public class MySQLDataSourceProvider extends AbstractContainerDataSourceProvider {

    @Override
    public String hibernateDialect() {
        return "org.hibernate.dialect.MySQL8Dialect";
    }

    @Override
    protected String defaultJdbcUrl() {
        return "jdbc:mysql://localhost/high_performance_java_persistence?useSSL=false";
    }

    protected DataSource newDataSource() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL(url());
        dataSource.setUser(username());
        dataSource.setPassword(password());

        return dataSource;
    }

    @Override
    public String username() {
        return "mysql";
    }

    @Override
    public String password() {
        return "admin";
    }

    @Override
    public Database database() {
        return Database.MYSQL;
    }
}
