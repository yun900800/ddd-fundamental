package org.ddd.fundamental.core.utils;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class CommonUtils {
    public static String nullIfBlank(String string) {
        if (isBlank(string)) {
            return null;
        }

        return string;
    }

    public static String requireNonBlank(String str, String message) {
        if (isBlank(str)) {
            throw new IllegalArgumentException(message);
        }
        return str;
    }
}
