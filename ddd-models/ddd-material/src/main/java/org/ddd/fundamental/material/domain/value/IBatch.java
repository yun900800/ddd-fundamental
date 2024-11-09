package org.ddd.fundamental.material.domain.value;

import org.ddd.fundamental.core.ValueObject;
import org.ddd.fundamental.material.domain.enums.BatchClassifyType;
import org.ddd.fundamental.material.value.MaterialId;

import java.util.Map;

/**
 * 批次是指具备相同特性/属性的一定数量的物料
 */
public interface IBatch extends ValueObject {

    /**
     * 批次对应的物料
     * @return
     */
    MaterialId materialId();

    /**
     * 批次号
     * @return
     */
    String batchNo();

    /**
     * 共同的属性
     * @return
     */
    Map<String,String> commonProps();

    /**
     * 特殊的属性
     * @return
     */
    Map<String,String> specialProps();

    /**
     * 批次数量
     * @return
     */
    int batchNumber();

    /**
     * 是否可以拆分
     * @return
     */
    boolean canSplit();

    /**
     * 是否可以合并
     * @return
     */
    boolean canMerge();

    /**
     * 批次类型
     * @return
     */
    BatchClassifyType batchClassifyType();

}
