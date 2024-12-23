package org.ddd.fundamental.infra.hibernate;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.hypersistence.utils.hibernate.type.util.ObjectMapperSupplier;
import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.jackson.LocalDateDeserializer;
import org.ddd.fundamental.jackson.LocalDateSerializer;

import java.time.LocalDate;
import java.util.TimeZone;

@Slf4j
public class CustomObjectMapperSupplier implements ObjectMapperSupplier {

    @Override
    public ObjectMapper get() {
        log.info("customize ObjectMapper");
        ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

        objectMapper.setTimeZone(
                TimeZone.getTimeZone("GMT")
        );
        SimpleModule simpleModule = new SimpleModule(
                "SimpleModule",
                new Version(1, 0, 0, null, null, null)
        );
        simpleModule.addSerializer(new LocalDateSerializer());
        simpleModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        objectMapper.registerModule(simpleModule);

        return objectMapper;
    }
}
