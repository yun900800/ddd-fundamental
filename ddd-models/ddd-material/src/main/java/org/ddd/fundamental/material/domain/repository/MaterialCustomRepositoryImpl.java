package org.ddd.fundamental.material.domain.repository;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.material.domain.model.Material;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
@Slf4j
public class MaterialCustomRepositoryImpl implements MaterialCustomRepository{
    @PersistenceContext
    private EntityManager entityManager;

    private static final int BATCH_SIZE = 5;

    @Override
    @Transactional
    public void batchUpdate(List<Material> materialList) {
        TypedQuery<Material> schoolQuery =
                entityManager.createQuery("SELECT s from Material s", Material.class);
        List<Material> materials = schoolQuery.getResultList();
        for (Material material: materials) {
            material.changeName("555");

        }
    }

    @Transactional
    @Override
    public void batchInsert(List<Material> materialList) {
        int size = materialList.size();
        for (int i = 0; i < size; i++) {
            //注释掉了显示刷新
//            if (i > 0 && i % BATCH_SIZE == 0) {
//                entityManager.flush();
//                entityManager.clear();
//            }
            Material material = materialList.get(i);
            entityManager.persist(material);
        }
    }
}
