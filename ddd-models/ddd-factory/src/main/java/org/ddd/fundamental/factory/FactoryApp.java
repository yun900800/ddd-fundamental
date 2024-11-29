package org.ddd.fundamental.factory;

import org.ddd.fundamental.creator.CreatorDataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(
        {
                "org.ddd.fundamental.factory",
                "org.ddd.fundamental.infra.hibernate"
        }
)
@ComponentScan(basePackages = {
        "org.ddd.fundamental.creator",
        "org.ddd.fundamental.factory",
        "org.ddd.fundamental.redis"
})
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
