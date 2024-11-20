package org.ddd.fundamental.equipment.value;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.day.range.DateRange;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.workprocess.enums.ProductResourceType;
import org.ddd.fundamental.workprocess.value.resources.ProductResource;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@MappedSuperclass
public class EquipmentResource extends ProductResource<EquipmentId> {

    /**
     * 设备资源或者工装的计划时间段
     */
    private List<DateRange> planRanges = new ArrayList<>();

    /**
     * 设备资源或者工装的使用
     */
    private DateRange useRang;

    /**
     * 设备是否正在使用
     */
    private boolean used;

    private EquipmentResource() {
        super(null, null,null);
    }

    private EquipmentResource(EquipmentId id, ProductResourceType resourceType,
                              ChangeableInfo info){
        super(id,resourceType,info);
    }



}
