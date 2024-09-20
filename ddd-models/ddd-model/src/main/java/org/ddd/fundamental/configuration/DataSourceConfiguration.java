package org.ddd.fundamental.configuration;

import com.mysql.cj.jdbc.MysqlXADataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "DataSourceConfiguration",
        transactionManagerRef = "transactionManager",
        basePackages = {"org.ddd.fundamental.visa","org.ddd.fundamental.tamagotchi.repository"}
)
public class DataSourceConfiguration {

    @Value("${db.url}")
    String dbUrl;

    @Value("${db.username}")
    String username;

    @Value("${db.passport}")
    String passport;

    public Map<String, String> jpaProperties() {
        Map<String, String> jpaProperties = new HashMap<>();
        jpaProperties.put("hibernate.hbm2ddl.auto", "none");
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        jpaProperties.put("hibernate.show_sql", "true");
        jpaProperties.put("hibernate.temp.use_jdbc_metadata_defaults", "false");
        jpaProperties.put("javax.persistence.transactionType", "JTA");
        return jpaProperties;
    }

    @Bean(name = "entityManagerFactoryBuilder")
    public EntityManagerFactoryBuilder entityManagerFactoryBuilder() {
        return new EntityManagerFactoryBuilder(
                new HibernateJpaVendorAdapter(), jpaProperties(), null
        );
    }


    @Bean(name = "DataSourceConfiguration")
    public LocalContainerEntityManagerFactoryBean getPostgresEntityManager(
            @Qualifier("entityManagerFactoryBuilder") EntityManagerFactoryBuilder entityManagerFactoryBuilder,
            @Qualifier("dataSource") DataSource postgresDataSource
    ) {
        String[] packages = {"org.ddd.fundamental.visa","org.ddd.fundamental.tamagotchi.domain"};
        return entityManagerFactoryBuilder
                .dataSource(postgresDataSource)
                .packages(packages)
                .persistenceUnit("mysql")
                .properties(jpaProperties())
                .jta(true)
                .build();
    }
    // 这个bean的配置对应的是
    //spring:
    //  datasource:
    //    url: ...
    //    username: ...
    //    password: ...
    //    driverClassname: ...
//    @Bean("dataSourceProperties")
//    @Primary
//    public DataSourceProperties dataSourceProperties() {
//        return new DataSourceProperties();
//    }

    //因此这里的配置可以去掉,通过装饰模式设置的AtomikosDataSourceBean剧透分布式事务
    @Bean("dataSource")
    @Primary
    public DataSource dataSource() {
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setUrl(dbUrl);
        mysqlXaDataSource.setUser(username);
        mysqlXaDataSource.setPassword(passport);

        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXaDataSource);
        xaDataSource.setUniqueResourceName("xa_visa");
        xaDataSource.setMaxPoolSize(30);
        return xaDataSource;
    }

}
