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
        @TypeDef(defaultForType = MaterialRecordId.class, typeClass = MaterialRecordIdType.class),
        @TypeDef(defaultForType = ToolingEquipmentId.class, typeClass = ToolingEquipmentIdType.class),
        @TypeDef(defaultForType = RPAccountId.class, typeClass = RPAccountIdType.class),
        @TypeDef(defaultForType = WorkProcessTemplateId.class, typeClass = WorkProcessTemplateIdType.class),
        @TypeDef(defaultForType = WorkOrderId.class, typeClass = WorkOrderIdType.class),
        @TypeDef(defaultForType = CraftsmanShipId.class, typeClass = CraftsmanShipIdType.class),
        @TypeDef(defaultForType = ProductStructureDataId.class, typeClass = ProductStructureDataIdType.class),
        @TypeDef(defaultForType = EquipmentResourceId.class, typeClass = EquipmentResourceIdType.class),
        @TypeDef(defaultForType = TenantId.class, typeClass = TenantIdType.class),
        @TypeDef(defaultForType = FactoryId.class, typeClass = FactoryIdType.class)
})


package org.ddd.fundamental.infra.hibernate;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import org.ddd.fundamental.bom.ProductStructureDataId;
import org.ddd.fundamental.core.tenant.FactoryId;
import org.ddd.fundamental.core.tenant.TenantId;
import org.ddd.fundamental.equipment.value.EquipmentResourceId;
import org.ddd.fundamental.equipment.value.RPAccountId;
import org.ddd.fundamental.factory.*;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.material.value.MaterialRecordId;
import org.ddd.fundamental.workorder.value.WorkOrderId;
import org.ddd.fundamental.workprocess.value.CraftsmanShipId;
import org.ddd.fundamental.workprocess.value.WorkProcessTemplateId;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;