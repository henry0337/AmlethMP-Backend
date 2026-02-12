package dev.sh1on.amlethmp.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringUtils {
    public static String asCamelCase(String str) {
        return str.replace("_", " ").toLowerCase();
    }
}
