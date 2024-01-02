package org.ddd.fundamental.datasource.provider.query;

import org.ddd.fundamental.datasource.core.Queries;

public class PostgresSQLQueries implements Queries {

    public static final Queries INSTANCE = new PostgresSQLQueries();

    @Override
    public String transactionId() {
        return "SELECT CAST(pg_current_xact_id_if_assigned() AS text)";
    }
}
