@TypeDefs({
        @TypeDef(defaultForType = YearModelId.class, typeClass = YearModelIdType.class),
        @TypeDef(
                name = "json",
                typeClass = JsonType.class
        )
})
package org.ddd.fundamental.workorder.infra.hibernate;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import org.ddd.fundamental.workorder.domain.value.YearModelId;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;