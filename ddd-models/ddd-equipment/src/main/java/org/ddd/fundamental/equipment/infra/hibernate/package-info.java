@TypeDefs({
        @TypeDef(defaultForType = EquipmentId.class, typeClass = EquipmentIdType.class),
        @TypeDef(
                name = "json",
                typeClass = JsonType.class
        )
})
package org.ddd.fundamental.equipment.infra.hibernate;

        import io.hypersistence.utils.hibernate.type.json.JsonType;

        import org.ddd.fundamental.equipment.domain.model.EquipmentId;
        import org.hibernate.annotations.TypeDef;
        import org.hibernate.annotations.TypeDefs;