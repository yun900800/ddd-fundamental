package org.ddd.fundamental.equipment.domain.repository;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.equipment.EquipmentAppTest;
import org.ddd.fundamental.equipment.domain.model.EquipmentResource;
import org.ddd.fundamental.material.value.MaterialId;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class EquipmentResourceRepositoryTest extends EquipmentAppTest {

    @Autowired
    private EquipmentResourceRepository resourceRepository;

    @Test
    public void testQueryResourcesByInputAndOutput(){
        List<EquipmentResource> resourceList = resourceRepository.queryResourcesByInputAndOutput(
                new MaterialId("76fadfb9-f2d7-4991-a45f-320916c15fb8"),new MaterialId("")
        );
        log.info("resourceList is {}",resourceList);
    }

}
