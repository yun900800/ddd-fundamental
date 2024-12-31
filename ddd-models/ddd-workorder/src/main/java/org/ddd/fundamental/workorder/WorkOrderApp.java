package org.ddd.fundamental.workorder;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.creator.CreatorDataManager;
import org.ddd.fundamental.equipment.client.EquipmentClient;
import org.ddd.fundamental.material.client.MaterialClient;
import org.ddd.fundamental.workprocess.client.WorkProcessClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
        "org.ddd.fundamental.equipment",
        "org.ddd.fundamental.material",
        "org.ddd.fundamental.workprocess",
        "org.ddd.fundamental.creator",
        "org.ddd.fundamental.common",
        "org.ddd.fundamental.workorder",
        "org.ddd.fundamental.redis",
})
@EntityScan(
        {
                "org.ddd.fundamental.workorder",
                "org.ddd.fundamental.infra.hibernate"
        }
)
@EnableJpaRepositories(
        basePackages = {
                "org.ddd.fundamental.core.repository",
                "org.ddd.fundamental.workorder.domain.repository"
        }
)
@EnableFeignClients(
        basePackages = {
                "org.ddd.fundamental.equipment",
                "org.ddd.fundamental.material",
                "org.ddd.fundamental.workprocess"
        }
)
@Slf4j
public class WorkOrderApp implements CommandLineRunner {

    @Autowired(required = false)
    private CreatorDataManager manager;

    public static void main(String[] args) {
        SpringApplication.run(WorkOrderApp.class,args);
    }

    @Override
    public void run(String... args) throws Exception {
        manager.execute();
//        List<MaterialDTO> materialDTOList = materialClient.materials();
//        log.info("materials is {}", materialDTOList);
//        log.info("equipments is {}", equipmentClient.equipments());
//        log.info("toolingList is {}", equipmentClient.toolingList());
//        List<String> ids = Arrays.asList(materialDTOList.get(0).id().toUUID(),
//                materialDTOList.get(2).id().toUUID(),materialDTOList.get(5).id().toUUID());
//        materialDTOList = materialClient.materialsByIds(ids);
//        log.info("materials is {} in ids:{}", materialDTOList,ids);

//        List<CraftsmanShipTemplateDTO> craftsmanShipTemplateDTOS = workProcessClient.craftsmanShipTemplates();
//        log.info("CraftsmanShipTemplates is {}",craftsmanShipTemplateDTOS);

    }
}
