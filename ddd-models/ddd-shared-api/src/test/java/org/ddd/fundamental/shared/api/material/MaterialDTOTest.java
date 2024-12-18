package org.ddd.fundamental.shared.api.material;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.ddd.fundamental.material.MaterialMaster;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.material.value.MaterialType;
import org.junit.jupiter.api.Test;

public class MaterialDTOTest {

    @Test
    public void testMaterialDTOToJson() throws JsonProcessingException {
        MaterialDTO materialDTO = new MaterialDTO(
                new MaterialMaster("testCode","name","spec","单位"),
                MaterialId.randomId(MaterialId.class),
                MaterialType.PRODUCTION
        );
        System.out.println(materialDTO.toJson());
    }
}
