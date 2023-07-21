package org.ddd.fundamental.share.infrastructure.hibernate.persistent;

import net.ttddyy.dsproxy.listener.ChainListener;
import net.ttddyy.dsproxy.listener.DataSourceQueryCountListener;
import net.ttddyy.dsproxy.listener.logging.SLF4JQueryLoggingListener;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.apache.commons.dbcp2.BasicDataSource;
import org.ddd.fundamental.share.infrastructure.config.ParameterNotExist;
import org.ddd.fundamental.share.infrastructure.hibernate.HibernateConfigurationFactory;
import org.ddd.fundamental.share.infrastructure.hibernate.logger.InlineQueryLogEntryCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


@TestConfiguration
public class TestHibernateConfiguration {

    private final String HSQL_DRIVER = org.hsqldb.jdbcDriver.class.getName();

    private final String HSQL_URL = "jdbc:hsqldb:mem:devnote";

    @Autowired
    private HibernateConfigurationFactory factory;

    private final String CONTEXT_NAME = "fundamental";

    @Bean("fundamental-transaction_manager")
    public PlatformTransactionManager hibernateTransactionManager() throws IOException, ParameterNotExist {
        System.out.println("init hibernateTransactionManager in test Environment");
        return factory.hibernateTransactionManager(sessionFactory());
    }

    @Bean("fundamental-session_factory")
    public LocalSessionFactoryBean sessionFactory() throws IOException, ParameterNotExist {
        System.out.println("init sessionFactory in test Environment");
        DataSource newDataSource = factory.proxyDataSource(dataSource());
        return factory.sessionFactory(CONTEXT_NAME, newDataSource);
    }

    @Bean("fundamental-data_source")
    public DataSource dataSource() throws IOException {
        System.out.println("init dataSource in test Environment");
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(HSQL_DRIVER);
        dataSource.setUrl(HSQL_URL);
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        Resource mysqlResource = factory.getResourceResolver().getResource(String.format(
                "classpath:database/%s.sql",
                "devnote"
        ));
        String mysqlSentences = new Scanner(mysqlResource.getInputStream(), "UTF-8").useDelimiter("\\A").next();

        dataSource.setConnectionInitSqls(new ArrayList<>(Arrays.asList(mysqlSentences.split(";"))));
        return dataSource;
    }
}
