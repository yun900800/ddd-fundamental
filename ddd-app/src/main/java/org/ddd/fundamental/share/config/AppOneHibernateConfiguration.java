package org.ddd.fundamental.share.config;

import org.ddd.fundamental.share.infrastructure.config.Parameter;
import org.ddd.fundamental.share.infrastructure.config.ParameterNotExist;
import org.ddd.fundamental.share.infrastructure.hibernate.HibernateConfigurationFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages={"org.ddd.fundamental.app.note.domain","org.ddd.fundamental.app.note.repository"},
        entityManagerFactoryRef = "note-entityManagerFactory",
        transactionManagerRef = "note-transactionManager"
)
public class AppOneHibernateConfiguration {
    private final HibernateConfigurationFactory factory;
    private final Parameter config;
    private final static String    CONTEXT_NAME = "app-note";

    public AppOneHibernateConfiguration(HibernateConfigurationFactory factory, Parameter config) {
        this.factory = factory;
        this.config = config;
    }

    @Bean(name = "note-transactionManager")
    public PlatformTransactionManager hibernateTransactionManager() throws IOException, ParameterNotExist {
        return factory.hibernateTransactionManager(sessionFactory());
    }

    @Bean(name = "note-entityManagerFactory")
    public LocalSessionFactoryBean sessionFactory() throws IOException, ParameterNotExist {

        DataSource newDataSource = factory.proxyDataSource(dataSourceOne());
        return factory.sessionFactory(CONTEXT_NAME, newDataSource);
    }

    @Bean("note_data_source")
    public DataSource dataSourceOne() throws IOException, ParameterNotExist {
        return factory.dataSource(
                config.get("APP_NOTE_DATABASE_HOST"),
                config.getInt("APP_NOTE_DATABASE_PORT"),
                config.get("APP_NOTE_DATABASE_NAME"),
                config.get("APP_NOTE_DATABASE_USER"),
                config.get("APP_NOTE_DATABASE_PASSWORD")
        );
    }
}
