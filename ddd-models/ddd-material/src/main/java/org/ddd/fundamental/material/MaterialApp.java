package org.ddd.fundamental.material;

import org.ddd.fundamental.creator.CreatorDataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EntityScan(
        {
                "org.ddd.fundamental.material",
                "org.ddd.fundamental.infra.hibernate"
        }
)
@ComponentScan(basePackages = {
        "org.ddd.fundamental.creator",
        "org.ddd.fundamental.material",
        "org.ddd.fundamental.redis",
})
@EnableJpaRepositories(
        basePackages = {
                "org.ddd.fundamental.core.repository",
                "org.ddd.fundamental.material.domain.repository"
        }
)
@EnableScheduling
public class MaterialApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(MaterialApp.class,args);
    }

    @Autowired
    private CreatorDataManager manager;
    @Override
    public void run(String... args) throws Exception {
        manager.execute();
    }
}
