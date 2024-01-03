package org.ddd.fundamental.validation.exception.external;

import org.ddd.fundamental.validation.exception.ValidationException;

public interface Validationable {

    void validate(final ValidationContext validationContext) throws ValidationException;
}
