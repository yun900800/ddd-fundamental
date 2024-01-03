package org.ddd.fundamental.datasource.core.enums;

import org.ddd.fundamental.datasource.core.DataSourceProvider;
import org.ddd.fundamental.datasource.provider.HSQLDBDataSourceProvider;
import org.ddd.fundamental.datasource.provider.PostgreSQLDataSourceProvider;
import org.ddd.fundamental.datasource.utils.ReflectionUtils;
import org.hibernate.dialect.Dialect;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Collections;

public enum Database {

    HSQLDB {
        @Override
        public Class<? extends DataSourceProvider> dataSourceProviderClass() {
            return HSQLDBDataSourceProvider.class;
        }
    },
    POSTGRESQL {
        @Override
        public Class<? extends DataSourceProvider> dataSourceProviderClass() {
            return PostgreSQLDataSourceProvider.class;
        }

        @Override
        protected JdbcDatabaseContainer newJdbcDatabaseContainer() {
            return new PostgreSQLContainer("postgres:15.3");
        }
    }
    ;

    private JdbcDatabaseContainer container;

    public JdbcDatabaseContainer getContainer() {
        return container;
    }

    /**
     * 返回对应数据库的DataSourceProvider实现类
     * @return
     */
    public DataSourceProvider dataSourceProvider() {
        return ReflectionUtils.newInstance(dataSourceProviderClass().getName());
    }

    /**
     * 所有的子类实现必须实现这个抽象方法
     * @return Class<? extends DataSourceProvider>
     */
    public abstract Class<? extends DataSourceProvider> dataSourceProviderClass();

    /**
     * 创建一个测试容器(注意这个类必须在test目录下)
     * @param username
     * @param password
     */
    public void initContainer(String username, String password) {
        container = (JdbcDatabaseContainer) newJdbcDatabaseContainer()
                .withReuse(true)
                .withEnv(Collections.singletonMap("ACCEPT_EULA", "Y"))
                .withTmpFs(Collections.singletonMap("/testtmpfs", "rw"));
        if(supportsDatabaseName()) {
            container.withDatabaseName(databaseName());
        }
        if(supportsCredentials()) {
            container.withUsername(username).withPassword(password);
        }
        container.start();
    }

    /**
     * 有条件的子类必须实现一个返回JdbcDatabaseContainer的方法
     * @return
     */
    protected JdbcDatabaseContainer newJdbcDatabaseContainer() {
        throw new UnsupportedOperationException(
                String.format(
                        "The [%s] database was not configured to use Testcontainers!",
                        name()
                )
        );
    }

    /**
     * 子类可以覆盖此方法
     * @return
     */
    protected boolean supportsDatabaseName() {
        return true;
    }

    /**
     * 可以覆盖实现对应的数据库名称
     * @return
     */
    protected String databaseName() {
        return "high-performance-java-persistence";
    }

    /**
     * 覆盖是否支持数据库认证
     * @return
     */
    protected boolean supportsCredentials() {
        return true;
    }

    /**
     * 根据Dialect返回对应的Database
     * @param dialect
     * @return
     */
    public static Database of(Dialect dialect) {
        Class<? extends Dialect> dialectClass = dialect.getClass();
        for(Database database : values()) {
            if(database.dataSourceProvider().hibernateDialectClass().isAssignableFrom(dialectClass)) {
                return database;
            }
        }
        throw new UnsupportedOperationException(
                String.format(
                        "The provided Dialect [%s] is not supported!",
                        dialectClass
                )
        );
    }
}
