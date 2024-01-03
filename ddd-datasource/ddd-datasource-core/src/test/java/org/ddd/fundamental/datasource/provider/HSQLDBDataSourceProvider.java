package org.ddd.fundamental.datasource.provider;

import org.ddd.fundamental.datasource.core.DataSourceProvider;
import org.ddd.fundamental.datasource.core.Queries;
import org.ddd.fundamental.datasource.core.enums.Database;
import org.ddd.fundamental.datasource.provider.query.HSQLDBServerQueries;
import org.hibernate.dialect.HSQLDialect;
import org.hsqldb.jdbc.JDBCDataSource;

import javax.sql.DataSource;
import java.util.Properties;

public class HSQLDBDataSourceProvider implements DataSourceProvider {
    @Override
    public String hibernateDialect() {
        return HSQLDialect.class.getName();
    }

    @Override
    public DataSource dataSource() {
        JDBCDataSource dataSource = new JDBCDataSource();
        dataSource.setUrl(url());
        dataSource.setUser(username());
        dataSource.setPassword(password());
        return dataSource;
    }

    @Override
    public Class<? extends DataSource> dataSourceClassName() {
        return JDBCDataSource.class;
    }

    @Override
    public Properties dataSourceProperties() {
        Properties properties = new Properties();
        properties.setProperty("url", url());
        properties.setProperty("user", username());
        properties.setProperty("password", password());
        return properties;
    }

    @Override
    public String url() {
        return "jdbc:hsqldb:mem:test";
    }

    @Override
    public String username() {
        return "sa";
    }

    @Override
    public String password() {
        return "";
    }

    @Override
    public Database database() {
        return Database.HSQLDB;
    }

    @Override
    public Queries queries() {
        return HSQLDBServerQueries.INSTANCE;
    }
}
