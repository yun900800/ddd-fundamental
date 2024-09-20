package org.ddd.fundamental.tamagotchi.domain.value;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import lombok.Value;
import org.ddd.fundamental.tamagotchi.domain.exception.PhoneNumberParsingException;

import static com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat.E164;

@Value
public class PhoneNumber {

    private static final PhoneNumberUtil PHONE_NUMBER_UTIL = PhoneNumberUtil.getInstance();
    String value;
    public PhoneNumber(String value){
        this.value = validateAndNormalizePhoneNumber(value);
    }

    private static void validatePhoneNumber(String value) {
        try {
            if (Long.parseLong(value) <= 0) {
                throw new PhoneNumberParsingException("The phone number must be positive: " + value);
            }
            PHONE_NUMBER_UTIL.parse(String.valueOf(value), "CN");
        } catch (NumberParseException | NumberFormatException e) {
            throw new PhoneNumberParsingException("The phone number isn't valid: " + value, e);
        }
    }

    private static String validateAndNormalizePhoneNumber(String value) {
        try {
            if (Long.parseLong(value) <= 0) {
                throw new PhoneNumberParsingException("The phone number cannot be negative: " + value);
            }
            final var phoneNumber = PHONE_NUMBER_UTIL.parse(value, "CN");
            final String formattedPhoneNumber = PHONE_NUMBER_UTIL.format(phoneNumber, E164);
            // E164 format returns phone number with + character
            return formattedPhoneNumber.substring(1);
        } catch (NumberParseException | NumberFormatException e) {
            throw new PhoneNumberParsingException("The phone number isn't valid: " + value, e);
        }
    }
}
