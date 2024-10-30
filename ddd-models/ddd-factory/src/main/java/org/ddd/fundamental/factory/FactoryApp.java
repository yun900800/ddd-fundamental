package org.ddd.fundamental.factory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(
        {
                "org.ddd.fundamental.factory",
                "org.ddd.fundamental.infra.hibernate"
        }
)
public class FactoryApp {
    public static void main(String[] args) {
        SpringApplication.run(FactoryApp.class,args);
    }
}
