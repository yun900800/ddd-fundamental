package org.ddd.fundamental.bom;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 *
 */
@SpringBootApplication
@EntityScan(
        {
                "org.ddd.fundamental.bom",
                "org.ddd.fundamental.infra.hibernate"
        }
)
public class BomApp {
    public static void main(String[] args) {
        SpringApplication.run(BomApp.class,args);
    }
}
