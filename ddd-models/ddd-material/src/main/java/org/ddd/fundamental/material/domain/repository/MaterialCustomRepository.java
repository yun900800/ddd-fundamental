package org.ddd.fundamental.material.domain.repository;

import com.blazebit.persistence.PagedList;
import org.ddd.fundamental.material.domain.model.Material;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface MaterialCustomRepository {

    void batchInsert(List<Material> materialList);

    void batchUpdate(List<Material> materialList);

    PagedList<Material> findTopN(
            Sort sortBy,
            int pageSize
    );

    PagedList<Material> findNextN(
            Sort orderBy,
            PagedList<Material> previousPage
    );


}
