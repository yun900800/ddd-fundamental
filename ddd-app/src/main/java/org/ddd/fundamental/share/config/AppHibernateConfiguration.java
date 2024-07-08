package org.ddd.fundamental.share.config;

import org.ddd.fundamental.share.infrastructure.config.Parameter;
import org.ddd.fundamental.share.infrastructure.config.ParameterNotExist;
import org.ddd.fundamental.share.infrastructure.hibernate.HibernateConfigurationFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages={"org.ddd.fundamental.app.domain","org.ddd.fundamental.app.repository"},
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager"
)
public class AppHibernateConfiguration {
    private final HibernateConfigurationFactory factory;
    private final Parameter config;
    private final static String    CONTEXT_NAME = "app";

    @Autowired
    private DataSource dataSource;

    public AppHibernateConfiguration(HibernateConfigurationFactory factory, Parameter config) {
        this.factory = factory;

        this.config  = config;
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager hibernateTransactionManager() throws IOException, ParameterNotExist {
        return factory.hibernateTransactionManager(sessionFactory());
    }

    @Bean(name = "entityManagerFactory")
    public LocalSessionFactoryBean sessionFactory() throws IOException, ParameterNotExist {

        DataSource newDataSource = factory.proxyDataSource(dataSource());
        return factory.sessionFactory(CONTEXT_NAME, newDataSource);
    }

    @Bean("app-data_source")
    public DataSource dataSource() throws IOException, ParameterNotExist {
        return factory.dataSource(
                config.get("APP_DATABASE_HOST"),
                config.getInt("APP_DATABASE_PORT"),
                config.get("APP_DATABASE_NAME"),
                config.get("APP_DATABASE_USER"),
                config.get("APP_DATABASE_PASSWORD")
        );
    }
}
