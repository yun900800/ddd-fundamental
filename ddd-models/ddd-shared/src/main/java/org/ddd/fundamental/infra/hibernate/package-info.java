@TypeDefs({
        @TypeDef(
                name = "json",
                typeClass = JsonType.class
        ),
        @TypeDef(defaultForType = EquipmentId.class, typeClass = EquipmentIdType.class),
        @TypeDef(defaultForType = MachineShopId.class, typeClass = MachineShopIdType.class),
        @TypeDef(defaultForType = ProductionLineId.class, typeClass = ProductionLineIdType.class),
        @TypeDef(defaultForType = WorkStationId.class, typeClass = WorkStationIdType.class),
        @TypeDef(defaultForType = MaterialId.class, typeClass = MaterialIdType.class),
        @TypeDef(defaultForType = MaterialRecordId.class, typeClass = MaterialRecordIdType.class)
})


package org.ddd.fundamental.infra.hibernate;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.factory.MachineShopId;
import org.ddd.fundamental.factory.ProductionLineId;
import org.ddd.fundamental.factory.WorkStationId;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.material.value.MaterialRecordId;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;