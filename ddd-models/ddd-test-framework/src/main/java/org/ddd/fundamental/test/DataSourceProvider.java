package org.ddd.fundamental.test;

import org.ddd.fundamental.enums.Database;

import javax.sql.DataSource;

public interface DataSourceProvider {
    Database database();

    String hibernateDialect();

    DataSource dataSource();

    String url();

    String username();

    String password();
}
