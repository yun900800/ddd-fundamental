package org.ddd.fundamental.equipment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(
        {
                "org.ddd.fundamental.equipment",
                "org.ddd.fundamental.infra.hibernate"
        }
)
@SpringBootApplication
public class EquipmentApp {
    public static void main(String[] args) {
        SpringApplication.run(EquipmentApp.class,args);
    }
}
