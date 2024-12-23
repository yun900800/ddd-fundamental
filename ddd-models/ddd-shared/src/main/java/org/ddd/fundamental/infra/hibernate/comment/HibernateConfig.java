package org.ddd.fundamental.infra.hibernate.comment;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.day.DayOff;
import org.ddd.fundamental.infra.hibernate.CustomObjectMapperSupplier;
import org.hibernate.jpa.boot.spi.IntegratorProvider;
import org.hibernate.jpa.boot.spi.TypeContributorList;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Component
@Slf4j
public class HibernateConfig implements HibernatePropertiesCustomizer {
    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        log.info("custom HibernateConfig");
        hibernateProperties.put("hibernate.use_sql_comments", true);
        hibernateProperties.put("hibernate.integrator_provider",
                (IntegratorProvider) () -> Collections.singletonList(CommentIntegrator.INSTANCE));

        JsonType jsonType = new JsonType(
                new CustomObjectMapperSupplier().get(),
                DayOff.class
        );

        hibernateProperties.put("hibernate.type_contributors",
                (TypeContributorList) () -> Collections.singletonList(
                        (typeContributions, serviceRegistry) ->
                                typeContributions.contributeType(
                                        jsonType, "dayOff"
                                )
                )
        );
    }
}
