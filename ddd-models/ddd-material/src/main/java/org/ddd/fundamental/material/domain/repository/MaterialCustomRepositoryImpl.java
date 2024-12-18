package org.ddd.fundamental.material.domain.repository;

import com.blazebit.persistence.CriteriaBuilder;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.PagedList;
import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.material.domain.model.Material;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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


    @Autowired
    private CriteriaBuilderFactory criteriaBuilderFactory;

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

    @Override
    public PagedList<Material> findTopN(Sort sortBy, int pageSize) {
        return sortedCriteriaBuilder(sortBy)
                .page(0, pageSize)
                .withKeysetExtraction(true)
                .getResultList();
    }

    @Override
    public PagedList<Material> findNextN(Sort orderBy, PagedList<Material> previousPage) {
        return sortedCriteriaBuilder(orderBy)
                .page(
                        previousPage.getKeysetPage(),
                        previousPage.getPage() * previousPage.getMaxResults(),
                        previousPage.getMaxResults()
                )
                .getResultList();
    }

    private CriteriaBuilder<Material> sortedCriteriaBuilder(
            Sort sortBy) {
        CriteriaBuilder<Material> criteriaBuilder = criteriaBuilderFactory
                .create(entityManager, Material.class);
        sortBy.forEach(order -> {
            criteriaBuilder.orderBy(
                    order.getProperty(),
                    order.isAscending()
            );
        });

        return criteriaBuilder;
    }
}
