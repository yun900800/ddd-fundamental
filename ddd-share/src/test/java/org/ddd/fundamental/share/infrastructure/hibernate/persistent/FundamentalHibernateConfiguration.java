package org.ddd.fundamental.share.infrastructure.hibernate.persistent;

import net.ttddyy.dsproxy.listener.ChainListener;
import net.ttddyy.dsproxy.listener.DataSourceQueryCountListener;
import net.ttddyy.dsproxy.listener.logging.SLF4JQueryLoggingListener;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.ddd.fundamental.share.infrastructure.config.Parameter;
import org.ddd.fundamental.share.infrastructure.config.ParameterNotExist;
import org.ddd.fundamental.share.infrastructure.hibernate.HibernateConfigurationFactory;
import org.ddd.fundamental.share.infrastructure.hibernate.logger.InlineQueryLogEntryCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;

import static net.ttddyy.dsproxy.listener.logging.CommonsLogLevel.INFO;

@Configuration
@EnableTransactionManagement
public class FundamentalHibernateConfiguration {
    private final HibernateConfigurationFactory factory;
    private final Parameter config;
    private final String                        CONTEXT_NAME = "fundamental";

    @Autowired
    private DataSource dataSource;

    public FundamentalHibernateConfiguration(HibernateConfigurationFactory factory, Parameter config) {
        this.factory = factory;
        this.config  = config;
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
        return factory.dataSource(
                config.get("FUNDAMENTAL_HOST"),
                config.getInt("FUNDAMENTAL_DATABASE_PORT"),
                config.get("FUNDAMENTAL_DATABASE_NAME"),
                config.get("FUNDAMENTAL_DATABASE_USER"),
                config.get("FUNDAMENTAL_DATABASE_PASSWORD")
        );
    }
}
