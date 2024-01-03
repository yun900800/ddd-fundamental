package org.ddd.fundamental.share.infrastructure.persistence.hibernate;

import org.ddd.fundamental.share.domain.Identifier;

public class StringIdentifier extends Identifier {

    public StringIdentifier() {
    }

    public StringIdentifier(String value) {
        super(value);
    }

    public String id() {
        return super.value;
    }


}
