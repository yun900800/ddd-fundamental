package org.ddd.fundamental.tamagotchi.domain;

import org.ddd.fundamental.tamagotchi.domain.exception.PhoneNumberParsingException;
import org.ddd.fundamental.tamagotchi.domain.value.PhoneNumber;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class PhoneNumberTest {
    @ParameterizedTest
    @CsvSource({
            "78005553535,78005553535",
            "88005553535,78005553535",
    })
    void shouldParsePhoneNumbersSuccessfully(String input, String expectedOutput) {
        final var phoneNumber = assertDoesNotThrow(
                () -> new PhoneNumber(input)
        );
        assertEquals(expectedOutput, phoneNumber.getValue());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "0", "-1", "-56"
    })
    void shouldThrowExceptionIfPhoneNumberIsNotValid(String input) {
        assertThrows(
                PhoneNumberParsingException.class,
                () -> new PhoneNumber(input)
        );
    }
}
