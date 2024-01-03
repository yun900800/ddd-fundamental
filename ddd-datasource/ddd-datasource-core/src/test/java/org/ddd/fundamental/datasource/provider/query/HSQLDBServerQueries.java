package org.ddd.fundamental.datasource.provider.query;

import org.ddd.fundamental.datasource.core.Queries;

public class HSQLDBServerQueries implements Queries {

    public static final Queries INSTANCE = new HSQLDBServerQueries();

    @Override
    public String transactionId() {
        return "VALUES (TRANSACTION_ID())";
    }
}
