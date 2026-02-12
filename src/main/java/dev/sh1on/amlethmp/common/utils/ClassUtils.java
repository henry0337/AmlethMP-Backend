package dev.sh1on.amlethmp.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ClassUtils {
    public static String[] extractAllPropertiesNameFromClass(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> !Modifier.isStatic(f.getModifiers()))
                .map(Field::getName)
                .toArray(String[]::new);
    }
}
