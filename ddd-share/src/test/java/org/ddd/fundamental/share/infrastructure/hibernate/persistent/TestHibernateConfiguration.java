package org.ddd.fundamental.share.infrastructure.hibernate.persistent;


import org.ddd.fundamental.share.infrastructure.config.ParameterNotExist;
import org.ddd.fundamental.share.infrastructure.hibernate.HibernateConfigurationFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;


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
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(HSQL_DRIVER)
                .url(HSQL_URL).username("sa").password("");


//        Resource mysqlResource = autowirefactory.getResourceResolver().getResource(String.format(
//                "classpath:database/%s.sql",
//                "devnote"
//        ));
//        String mysqlSentences = new Scanner(mysqlResource.getInputStream(), "UTF-8").useDelimiter("\\A").next();
//
//        dataSource.setConnectionInitSqls(new ArrayList<>(Arrays.asList(mysqlSentences.split(";"))));
        return dataSourceBuilder.build();
    }
}
