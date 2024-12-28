package org.ddd.fundamental.infra.hibernate.function;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.spi.MetadataBuilderContributor;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

@Slf4j
public class SqlFunctionsMetadataBuilderContributor
        implements MetadataBuilderContributor {
    @Override
    public void contribute(MetadataBuilder metadataBuilder) {
        metadataBuilder.applySqlFunction(
                "group_concat",
                new StandardSQLFunction(
                        "group_concat",
                        StandardBasicTypes.STRING
                )
        );
        log.info("test add function");
        metadataBuilder.applySqlFunction(
                "json_extract",
                new SQLFunctionTemplate(
                        StandardBasicTypes.TEXT,
                        "json_extract(?1,?2)"
                )
        );
    }
}
