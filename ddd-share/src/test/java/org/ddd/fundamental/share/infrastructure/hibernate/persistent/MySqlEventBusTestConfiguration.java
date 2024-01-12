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

import javax.sql.DataSource;
import java.io.IOException;

@TestConfiguration
public class MySqlEventBusTestConfiguration {

    public static CustomMySqlContainer mySqlContainer = CustomMySqlContainer.getInstance();
    private final String                        CONTEXT_NAME = "fundamental";

    @Autowired
    private final HibernateConfigurationFactory factory;

    MySqlEventBusTestConfiguration(HibernateConfigurationFactory factory) {
        this.factory = factory;
    }

    @Bean("fundamental-transaction_manager")
    @ConditionalOnMissingBean
    public PlatformTransactionManager hibernateTransactionManager() throws IOException, ParameterNotExist {
        return factory.hibernateTransactionManager(sessionFactory());
    }

    @Bean("fundamental-session_factory")
    @ConditionalOnMissingBean
    public LocalSessionFactoryBean sessionFactory() throws IOException, ParameterNotExist {

        DataSource newDataSource = factory.proxyDataSource(dataSource());
        return factory.sessionFactory(CONTEXT_NAME, newDataSource);
    }

    @Bean("fundamental-data_source")
    @ConditionalOnMissingBean
    public DataSource dataSource() throws IOException, ParameterNotExist {
        return factory.dataSource(
                mySqlContainer.getHost(),
                3306,
                mySqlContainer.getDatabaseName(),
                mySqlContainer.getUsername(),
                mySqlContainer.getPassword()
        );
    }
}
