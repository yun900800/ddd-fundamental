@TypeDefs({
        @TypeDef(
                name = "json",
                typeClass = JsonType.class
        ),
        @TypeDef(defaultForType = BatchId.class, typeClass = BatchIdType.class)
})


package org.ddd.fundamental.material.infra.hibernate;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import org.ddd.fundamental.material.domain.value.BatchId;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;