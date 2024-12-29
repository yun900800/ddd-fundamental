package org.ddd.fundamental.schedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Hello world!
 */
@SpringBootApplication
@EntityScan(
        {
                "org.ddd.fundamental.schedule",
                "org.ddd.fundamental.infra.hibernate"
        }
)
@ComponentScan(basePackages = {
        "org.ddd.fundamental.equipment",
        "org.ddd.fundamental.creator",
        "org.ddd.fundamental.common",
        "org.ddd.fundamental.schedule",
        "org.ddd.fundamental.redis",
})
@EnableJpaRepositories(
        basePackages = {
                "org.ddd.fundamental.core.repository",
                "org.ddd.fundamental.schedule.domain.repository"
        }
)
@EnableScheduling
@EnableFeignClients(
        basePackages = {
                "org.ddd.fundamental.equipment",
        }
)
public class ScheduleApp {
    public static void main(String[] args) {
        SpringApplication.run(ScheduleApp.class, args);
    }
}
