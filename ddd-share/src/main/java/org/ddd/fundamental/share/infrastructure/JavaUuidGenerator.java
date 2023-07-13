package org.ddd.fundamental.share.infrastructure;

import org.ddd.fundamental.share.domain.Service;
import org.ddd.fundamental.share.domain.UuidGenerator;

import java.util.UUID;

@Service
public final class JavaUuidGenerator implements UuidGenerator {
    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
