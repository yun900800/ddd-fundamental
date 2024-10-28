package org.ddd.fundamental.material;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(
        {
                "org.ddd.fundamental.material",
                "org.ddd.fundamental.infra.hibernate"
        }
)
public class MaterialApp {
    public static void main(String[] args) {
        SpringApplication.run(MaterialApp.class,args);
    }
}
