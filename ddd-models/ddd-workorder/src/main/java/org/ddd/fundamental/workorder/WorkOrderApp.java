package org.ddd.fundamental.workorder;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.workorder.client.EquipmentClient;
import org.ddd.fundamental.workorder.client.MaterialClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@Slf4j
public class WorkOrderApp implements CommandLineRunner {

    @Autowired
    private MaterialClient materialClient;

    @Autowired
    private EquipmentClient equipmentClient;
    public static void main(String[] args) {
        SpringApplication.run(WorkOrderApp.class,args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("materials is {}", materialClient.materials());
        log.info("equipments is {}", equipmentClient.equipments());
        log.info("toolingList is {}", equipmentClient.toolingList());
    }
}
