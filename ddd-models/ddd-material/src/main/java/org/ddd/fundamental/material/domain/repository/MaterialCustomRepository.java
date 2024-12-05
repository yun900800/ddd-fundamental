package org.ddd.fundamental.material.domain.repository;

import org.ddd.fundamental.material.domain.model.Material;

import java.util.List;

public interface MaterialCustomRepository {

    void batchInsert(List<Material> materialList);

    void batchUpdate(List<Material> materialList);


}
