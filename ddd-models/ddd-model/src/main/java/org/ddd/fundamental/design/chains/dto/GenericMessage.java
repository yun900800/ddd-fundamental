package org.ddd.fundamental.design.chains.dto;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static java.util.Optional.ofNullable;

public class GenericMessage {
    private final Map<String, String> content;

    public GenericMessage(Map<String, String> content) {
        this.content = content;
    }

    public Optional<String> getValue(String field) {
        return ofNullable(content.get(field));
    }

    public GenericMessage with(String field, String value) {
        final var contentCopy = new HashMap<>(content);
        contentCopy.put(field, value);
        return new GenericMessage(contentCopy);
    }

    @Override
    public String toString() {
        return "GenericMessage{" +
                "content=" + content +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GenericMessage message = (GenericMessage) o;
        return content.equals(message.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}
