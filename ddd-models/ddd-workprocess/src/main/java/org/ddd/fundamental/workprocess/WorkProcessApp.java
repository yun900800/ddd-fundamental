package org.ddd.fundamental.workprocess;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(
        {
                "org.ddd.fundamental.workprocess",
                "org.ddd.fundamental.infra.hibernate"
        }
)
public class WorkProcessApp {
    public static void main(String[] args) {
        SpringApplication.run(WorkProcessApp.class);
    }
}
