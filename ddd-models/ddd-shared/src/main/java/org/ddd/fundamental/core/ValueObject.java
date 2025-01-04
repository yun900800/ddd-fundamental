package org.ddd.fundamental.core;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public interface ValueObject extends DomainObject{

    default String objToString(){
        return StringEscapeUtils.unescapeJson(
                ReflectionToStringBuilder.reflectionToString(this,
                        ToStringStyle.JSON_STYLE)
        );
    }

}
