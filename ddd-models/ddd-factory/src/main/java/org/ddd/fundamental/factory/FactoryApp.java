package org.ddd.fundamental.factory;

import org.ddd.fundamental.creator.CreatorDataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EntityScan(
        {
                "org.ddd.fundamental.factory",
                "org.ddd.fundamental.infra.hibernate"
        }
)
@ComponentScan(basePackages = {
        "org.ddd.fundamental.equipment",
        "org.ddd.fundamental.creator",
        "org.ddd.fundamental.common",
        "org.ddd.fundamental.factory",
        "org.ddd.fundamental.redis",
})
@EnableJpaRepositories(
        basePackages = {
                "org.ddd.fundamental.core.repository",
                "org.ddd.fundamental.factory.domain.repository"
        }
)
@EnableScheduling
@EnableFeignClients(
        basePackages = {
                "org.ddd.fundamental.equipment",
        }
)
public class FactoryApp implements CommandLineRunner {

    @Autowired
    private CreatorDataManager manager;
    public static void main(String[] args) {
        SpringApplication.run(FactoryApp.class,args);
    }

    @Override
    public void run(String... args) throws Exception {
        manager.execute();
    }
}
