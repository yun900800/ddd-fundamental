package org.ddd.fundamental.share.infrastructure.hibernate.persistent;

import org.ddd.fundamental.share.infrastructure.bus.event.mysql.CustomMySqlContainer;
import org.ddd.fundamental.share.infrastructure.config.ParameterNotExist;
import org.ddd.fundamental.share.infrastructure.hibernate.HibernateConfigurationFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.testcontainers.containers.MySQLContainer;

import javax.sql.DataSource;
import java.io.IOException;

@TestConfiguration
public class MySqlEventBusTestConfiguration {

//    public CustomMySqlContainer mySqlContainer = CustomMySqlContainer.getInstance();
//    {
//        mySqlContainer.start();
//    }

    static final MySQLContainer MY_SQL_CONTAINER;

    static {
        MY_SQL_CONTAINER = new MySQLContainer("mysql:latest");
        MY_SQL_CONTAINER.start();
    }
    private final String                        CONTEXT_NAME = "fundamental";

    @Autowired
    private final HibernateConfigurationFactory factory;

    MySqlEventBusTestConfiguration(HibernateConfigurationFactory factory) {
        this.factory = factory;
    }

    @Bean("fundamental-transaction_manager")
    public PlatformTransactionManager hibernateTransactionManager() throws IOException, ParameterNotExist {
        return factory.hibernateTransactionManager(sessionFactory());
    }

    @Bean("fundamental-session_factory")
    public LocalSessionFactoryBean sessionFactory() throws IOException, ParameterNotExist {

        DataSource newDataSource = factory.proxyDataSource(dataSource());
        return factory.sessionFactory(CONTEXT_NAME, newDataSource);
    }

    @Bean("fundamental-data_source")
    public DataSource dataSource() throws IOException, ParameterNotExist {
        System.out.println("init dataSource mysql-container in test Environment");
        return factory.dataSource(
                MY_SQL_CONTAINER.getHost(),
                MY_SQL_CONTAINER.getFirstMappedPort(),
                MY_SQL_CONTAINER.getDatabaseName(),
                MY_SQL_CONTAINER.getUsername(),
                MY_SQL_CONTAINER.getPassword()
        );
    }
}
