package org.ddd.fundamental.workprocess.domain.repository;

import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.utils.CollectionUtils;
import org.ddd.fundamental.workprocess.WorkProcessAppTest;
import org.ddd.fundamental.workprocess.domain.model.*;
import org.ddd.fundamental.workprocess.value.WorkProcessTemplateId;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;

public class CraftsmanShipRepositoryTest extends WorkProcessAppTest {

    @Autowired
    private CraftsmanShipRepository craftsmanShipRepository;

    @Autowired
    private WorkProcessTemplateRepository workProcessNewRepository;

    @Test
    public void createCraftsmanShip(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<WorkProcessTemplate> workProcessNewList = workProcessNewRepository.findAll();
        List<WorkProcessTemplateId> workProcessIds = new ArrayList<>();
        for (int i = 0 ; i< 5;i++) {
            workProcessIds.add(CollectionUtils.random(workProcessNewList).id());
        }

        CraftsmanShipTemplate craftsmanShip = new CraftsmanShipTemplate(workProcessIds,workProcessNewRepository,
                MaterialId.randomId(MaterialId.class));
        System.out.println(craftsmanShip);
        craftsmanShipRepository.save(craftsmanShip);
        stopWatch.stop();
        // 统计执行时间（秒）
        System.out.printf("执行时长：%f 秒.%n", stopWatch.getTotalTimeSeconds()); // %n 为换行
        // 统计执行时间（毫秒）
        System.out.printf("执行时长：%d 毫秒.%n", stopWatch.getTotalTimeMillis());
        // 统计执行时间（纳秒）
        System.out.printf("执行时长：%d 纳秒.%n", stopWatch.getTotalTimeNanos());
    }
}
