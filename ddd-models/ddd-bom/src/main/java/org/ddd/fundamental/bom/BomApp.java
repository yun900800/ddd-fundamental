package org.ddd.fundamental.bom;


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

/**
 *
 */
@ComponentScan(basePackages = {
        "org.ddd.fundamental.creator",
        "org.ddd.fundamental.bom",
        "org.ddd.fundamental.redis",
        "org.ddd.fundamental.material",
})

@SpringBootApplication
@EntityScan(
        {
                "org.ddd.fundamental.bom",
                "org.ddd.fundamental.infra.hibernate"
        }
)
@EnableJpaRepositories(
        basePackages = {
                "org.ddd.fundamental.core.repository",
                "org.ddd.fundamental.bom.domain.repository"
        }
)
@EnableFeignClients(
        basePackages = {
                "org.ddd.fundamental.material",
        }
)
@EnableScheduling
public class BomApp implements CommandLineRunner {

    @Autowired
    private CreatorDataManager manager;
    public static void main(String[] args) {
        SpringApplication.run(BomApp.class,args);
    }

    @Override
    public void run(String... args) throws Exception {
        manager.execute();
    }
}
