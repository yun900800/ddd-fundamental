package org.ddd.fundamental.equipment.domain.repository;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.equipment.application.EquipmentConverter;
import org.ddd.fundamental.equipment.domain.model.EquipmentResource;
import org.ddd.fundamental.material.value.MaterialId;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Component
@Slf4j
public class EquipmentResourceRepositoryImpl implements CustomEquipmentResourceRepository{

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<EquipmentResource> queryResourcesByInputAndOutput(MaterialId inputId, MaterialId outputId) {
        Query query = entityManager.createNativeQuery("""
                        select * from equipment_resource er where
                        1 = 1 AND  
                        :inputId MEMBER OF (
                            JSON_EXTRACT(er.equipment_inputs,'$[*]') 
                        )  AND :outputId MEMBER OF (
                            JSON_EXTRACT(er.equipment_outputs,'$[*]') 
                        )
                        """
        ,EquipmentResource.class)
                .setParameter("inputId",inputId.toUUID())
                .setParameter("outputId", outputId.toUUID());
        return query.getResultList();
    }

    @Override
    public List<String> queryResourcesByInputAndOutputByJPQL(MaterialId inputId, MaterialId outputId) {
//        Query query1 = entityManager.createQuery("""
//                        select er from EquipmentResource er where
//                        :inputId MEMBER OF function('json_extract',er.equipmentResourceValue.inputs,:jsonPath)
//                         AND :outputId MEMBER OF function('json_extract',er.equipmentResourceValue.outputs,:jsonPath)
//                        """)
//                .setParameter("inputId",inputId.toUUID())
//                .setParameter("jsonPath","$[*]")
//                .setParameter("outputId", outputId.toUUID());
//        List<EquipmentResource> equipmentResources = query1.getResultList();
//        log.info("equipmentResources is ");


        Query query1 = entityManager.createQuery("""
                        select er  from EquipmentResource er 
                        where :inputId in (
                            json_extract(er.equipmentResourceValue.inputs,'$[*]')
                        )
                        """)
                .setParameter("inputId","76fadfb9-f2d7-4991-a45f-320916c15fb8");
        List<EquipmentResource> equipmentResources = query1.getResultList();
        log.info("equipmentResources is {}", EquipmentConverter.entityToResourceDTO(equipmentResources));


        Query query = entityManager.createQuery("""
                        select group_concat(er.equipmentResourceValue.resource.name) from EquipmentResource er where 
                        1 = 1 
                        group by er.positionType                                   
                        """,String.class);
        return query.getResultList();
    }

    private static String buildHeader(){
        return """
                select * from equipment_resource er where
                1 = 1
                """;
    }

    private static String buildWithInputId(int index){
        return """
                AND :inputId%s MEMBER OF (
                            JSON_EXTRACT(er.equipment_inputs,'$[*]') 
                        ) 
                """.formatted(index);
    }

    private static Query buildWithInputParam(int index,MaterialId inputId,Query query){
        query.setParameter("inputId"+index,inputId.toUUID());
        return query;
    }

    private static Query buildWithOutputParam(int index,MaterialId outputId,Query query){
        query.setParameter("outputId"+index,outputId.toUUID());
        return query;
    }

    private static String buildWithOutputId(int index){
        return """
               AND :outputId%s MEMBER OF (
                            JSON_EXTRACT(er.equipment_outputs,'$[*]') 
                        ) 
                """.formatted(index);
    }

    private static String buildSQLString(List<MaterialId> inputIds, List<MaterialId> outputIds){
        String result = buildHeader();
        for (int i = 0 ; i< inputIds.size(); i++) {
            result += buildWithInputId(i);
        }
        for (int i = 0 ; i< outputIds.size(); i++) {
            result += buildWithOutputId(i);
        }
        return result;
    }

    private static Query buildSQLStringParams(List<MaterialId> inputIds, List<MaterialId> outputIds,Query query){
        for (int i = 0 ; i< inputIds.size(); i++) {
            query = buildWithInputParam(i, inputIds.get(i),query);
        }
        for (int i = 0 ; i< inputIds.size(); i++) {
            query = buildWithOutputParam(i, outputIds.get(i),query);
        }
        return query;
    }

    @Override
    public List<EquipmentResource> queryResourcesByInputAndOutputIds(List<MaterialId> inputIds, List<MaterialId> outputIds) {
        Query query = entityManager.createNativeQuery(buildSQLString(inputIds,outputIds)
                ,EquipmentResource.class);
        query = buildSQLStringParams(inputIds, outputIds,query);
        return query.getResultList();
    }
}
