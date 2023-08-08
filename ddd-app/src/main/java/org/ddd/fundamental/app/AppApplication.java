package org.ddd.fundamental.app;

import org.ddd.fundamental.share.domain.Service;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;


/**
 * App项目启动工程
 */
@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class )
@ComponentScan(
        includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Service.class),
        value = {"org.ddd.fundamental"}
)
public class AppApplication {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }
}
