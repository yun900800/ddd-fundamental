package org.ddd.fundamental.app;

import org.ddd.fundamental.share.domain.Service;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(
        value = {"org.ddd.fundamental.app.importtest","org.ddd.fundamental.app.lock"}
)
public class AppApplicationTest {

    public static void main(String[] args) {
        SpringApplication.run(AppApplicationTest.class, args);
    }
}
