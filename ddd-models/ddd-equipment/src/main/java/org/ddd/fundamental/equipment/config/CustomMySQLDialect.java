package org.ddd.fundamental.equipment.config;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.dialect.MySQLDialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

@Slf4j
public class CustomMySQLDialect extends MySQLDialect {
    public CustomMySQLDialect(){
        super();
        log.info("custom CustomMySQLDialect");
        registerFunction(
                "json_extract",
                new StandardSQLFunction(
                        "json_extract",
                        StandardBasicTypes.STRING
                )
        );

        registerFunction(
                "function",
                new StandardSQLFunction(
                        "function",
                        StandardBasicTypes.STRING
                )
        );
    }
}
