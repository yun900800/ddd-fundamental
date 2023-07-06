package org.ddd.fundamental.validation.external;

import org.ddd.fundamental.validation.exception.ValidationException;

public interface Validationable {

    void validate(final ValidationContext validationContext) throws ValidationException;
}
