package org.ddd.fundamental.share.infrastructure.hibernate;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.logging.LoggingMeterRegistry;
import io.micrometer.core.instrument.logging.LoggingRegistryConfig;
import net.ttddyy.dsproxy.listener.ChainListener;
import net.ttddyy.dsproxy.listener.DataSourceQueryCountListener;
import net.ttddyy.dsproxy.listener.logging.SLF4JQueryLoggingListener;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.ddd.fundamental.share.domain.Service;
import org.ddd.fundamental.share.infrastructure.hibernate.logger.InlineQueryLogEntryCreator;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

import static net.ttddyy.dsproxy.listener.logging.CommonsLogLevel.INFO;

@Service
public final class HibernateConfigurationFactory {
    private final ResourcePatternResolver resourceResolver;

    private final static String HSQL_DRIVER = org.hsqldb.jdbcDriver.class.getName();

    private final static String HSQL_URL = "jdbc:hsqldb:mem:devnote";

    public ResourcePatternResolver getResourceResolver() {
        return resourceResolver;
    }

    public HibernateConfigurationFactory(ResourcePatternResolver resourceResolver) {
        this.resourceResolver = resourceResolver;
    }

    public PlatformTransactionManager hibernateTransactionManager(LocalSessionFactoryBean sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory.getObject());

        return transactionManager;
    }

    public LocalSessionFactoryBean sessionFactory(String contextName, DataSource dataSource) throws IOException {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setHibernateProperties(hibernateProperties());
        List<Resource> mappingFiles = searchMappingFiles(contextName);
        sessionFactory.setImplicitNamingStrategy(new SpringImplicitNamingStrategy());
        sessionFactory.setPhysicalNamingStrategy(new SpringPhysicalNamingStrategy());
        sessionFactory.setMappingLocations(mappingFiles.toArray(new Resource[mappingFiles.size()]));
        sessionFactory.setPackagesToScan("org.ddd.fundamental");
        return sessionFactory;
    }

    public DataSource proxyDataSource(DataSource dataSource) {
        ChainListener listener = new ChainListener();
        SLF4JQueryLoggingListener loggingListener = new SLF4JQueryLoggingListener();
        loggingListener.setQueryLogEntryCreator(new InlineQueryLogEntryCreator());
        listener.addListener(loggingListener);
        listener.addListener(new DataSourceQueryCountListener());
        return ProxyDataSourceBuilder
                .create(dataSource)
                .logQueryByCommons(INFO)
                .name("Fundamental")
                .listener(listener)
                .build();
    }

    private HikariDataSource createHikariDataSource(String url,String driverName,
                                                    String userName,String password) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(driverName);
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(userName);
        hikariConfig.setPassword(password);
        hikariConfig.setMaximumPoolSize(10);
        hikariConfig.setMinimumIdle(3);
        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
        LoggingMeterRegistry loggingMeterRegistry = new LoggingMeterRegistry(new LoggingRegistryConfig() {
            @Override
            public String get(String s) {
                return null;
            }

            public Duration step(){
                return Duration.ofSeconds(10);
            }
        }, Clock.SYSTEM);
        hikariDataSource.setMetricRegistry(loggingMeterRegistry);
        return hikariDataSource;
    }

    /**
     *
     * @param url
     * @param driverClassName
     * @param username
     * @param password
     * @return
     */
    private DataSource createCommonDataSource(String url,
                                              String driverClassName,
                                              String username,
                                              String password) {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driverClassName)
                .url(url).username(username).password(password);
        DataSource dataSource = dataSourceBuilder.build();
        return dataSource;
    }

    public DataSource dataSource(
            String host,
            Integer port,
            String databaseName,
            String username,
            String password
    ) {
        String url = String.format(
                "jdbc:mysql://%s:%s/%s?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                host,
                port,
                databaseName
        );
        String driverClassName = "com.mysql.cj.jdbc.Driver";
        return createHikariDataSource(url,driverClassName,username,password);
    }

    private List<Resource> searchMappingFiles(String contextName) {
        List<String> modules   = subdirectoriesFor(contextName);
        List<String> goodPaths = new ArrayList<>();

        for (String module : modules) {
            String[] files = mappingFilesIn(module + "/infrastructure/persistence/hibernate/");

            for (String file : files) {
                goodPaths.add(module + "/infrastructure/persistence/hibernate/" + file);
            }
        }

        return goodPaths.stream().map(FileSystemResource::new).collect(Collectors.toList());
    }

    private List<String> subdirectoriesFor(String contextName) {
        String path = "./src/" + contextName + "/main/java/org/ddd/" + contextName + "/";

        String[] files = new File(path).list((current, name) -> new File(current, name).isDirectory());

        if (null == files) {
            path  = "src/main/java/org/ddd/" + contextName + "/";
            files = new File(path).list((current, name) -> new File(current, name).isDirectory());
        }

        if (null == files) {
            return Collections.emptyList();
        }

        String finalPath = path;

        return Arrays.stream(files).map(file -> finalPath + file).collect(Collectors.toList());
    }

    private String[] mappingFilesIn(String path) {
        String[] files = new File(path).list((current, name) -> new File(current, name).getName().contains(".hbm.xml"));

        if (null == files) {
            return new String[0];
        }

        return files;
    }

    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.put(AvailableSettings.HBM2DDL_AUTO, "none");

        hibernateProperties.put(AvailableSettings.SHOW_SQL, "false");
        hibernateProperties.put(AvailableSettings.DIALECT, "org.hibernate.dialect.MySQLDialect");
        hibernateProperties.put("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName());
        hibernateProperties.put("hibernate.physical_naming_strategy", SpringPhysicalNamingStrategy.class.getName());
//        hibernateProperties.put(AvailableSettings.DIALECT, "org.hibernate.dialect.HSQLDialect");


        return hibernateProperties;
    }
}
