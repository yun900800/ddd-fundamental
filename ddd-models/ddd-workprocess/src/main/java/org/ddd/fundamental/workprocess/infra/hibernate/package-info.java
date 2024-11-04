@TypeDefs({
        @TypeDef(defaultForType = WorkProcessId.class, typeClass = WorkProcessIdType.class),
        @TypeDef(
                name = "json",
                typeClass = JsonType.class
        ),
        @TypeDef(defaultForType = CraftsmanShipId.class, typeClass = CraftsmanShipIdType.class),
})
package org.ddd.fundamental.workprocess.infra.hibernate;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import org.ddd.fundamental.workprocess.domain.model.CraftsmanShipId;
import org.ddd.fundamental.workprocess.domain.model.WorkProcessId;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;