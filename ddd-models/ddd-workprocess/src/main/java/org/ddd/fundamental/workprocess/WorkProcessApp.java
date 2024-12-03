package org.ddd.fundamental.workprocess;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.creator.CreatorDataManager;
import org.ddd.fundamental.shared.api.equipment.EquipmentDTO;
import org.ddd.fundamental.shared.api.material.MaterialDTO;
import org.ddd.fundamental.material.value.MaterialType;
import org.ddd.fundamental.workprocess.client.EquipmentClient;
import org.ddd.fundamental.workprocess.client.MaterialClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.util.StopWatch;

import java.util.List;

@SpringBootApplication
@EntityScan(
        {
                "org.ddd.fundamental.workprocess",
                "org.ddd.fundamental.infra.hibernate"
        }
)
@ComponentScan(basePackages = {
        "org.ddd.fundamental.creator",
        "org.ddd.fundamental.workprocess",
        "org.ddd.fundamental.redis",
})
@EnableJpaRepositories(
        basePackages = {
                "org.ddd.fundamental.core.repository",
                "org.ddd.fundamental.workprocess.domain.repository"
        }
)
@EnableFeignClients
@Slf4j
public class WorkProcessApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(WorkProcessApp.class);
    }

    @Autowired
    private CreatorDataManager manager;

    @Override
    public void run(String... args) throws Exception {
        manager.execute();
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
//        List<MaterialDTO> materialDTOList = client.materialsByMaterialType(MaterialType.PRODUCTION);
//        log.info("products is {}",materialDTOList);
//        List<EquipmentDTO> equipmentDTOS = equipmentClient.equipments();
//        log.info("equipmentDTOS is {}",equipmentDTOS);
//        stopWatch.stop();
//        // 统计执行时间（秒）
//        System.out.printf("执行时长：%f 秒.%n", stopWatch.getTotalTimeSeconds()); // %n 为换行
//        // 统计执行时间（毫秒）
//        System.out.printf("执行时长：%d 毫秒.%n", stopWatch.getTotalTimeMillis());
//        // 统计执行时间（纳秒）
//        System.out.printf("执行时长：%d 纳秒.%n", stopWatch.getTotalTimeNanos());
    }
}
