@TypeDefs({
        @TypeDef(
                name = "json",
                typeClass = JsonType.class
        )
})
package org.ddd.fundamental.factory.infra.hibernate;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;