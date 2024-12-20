package org.ddd.fundamental.workorder;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.shared.api.material.MaterialDTO;
import org.ddd.fundamental.shared.api.optemplate.CraftsmanShipTemplateDTO;
import org.ddd.fundamental.workorder.client.EquipmentClient;
import org.ddd.fundamental.workorder.client.MaterialClient;
import org.ddd.fundamental.workorder.client.WorkProcessClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableFeignClients
@EntityScan(
        {
                "org.ddd.fundamental.workorder",
                "org.ddd.fundamental.infra.hibernate"
        }
)
@Slf4j
public class WorkOrderApp implements CommandLineRunner {

    @Autowired
    private MaterialClient materialClient;

    @Autowired
    private EquipmentClient equipmentClient;

    @Autowired
    private WorkProcessClient workProcessClient;
    public static void main(String[] args) {
        SpringApplication.run(WorkOrderApp.class,args);
    }

    @Override
    public void run(String... args) throws Exception {
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
